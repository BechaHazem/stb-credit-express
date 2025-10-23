package com.stb.credit.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Image;
import com.stb.client.NotificationClient;
import com.stb.client.UserClient;
import com.stb.credit.dto.EmailDTO;
import com.stb.credit.dto.LoanRequestDTO;
import com.stb.credit.dto.UserDTO;
import com.stb.credit.models.CreditSimulation;
import com.stb.credit.models.Customer;
import com.stb.credit.models.Document;
import com.stb.credit.models.LoanRequest;
import com.stb.credit.repository.CreditSimulationRepository;
import com.stb.credit.repository.CustomerRepository;
import com.stb.credit.repository.DocumentRepository;
import com.stb.credit.repository.LoanRequestRepository;
import com.stb.credit.service.CloudinaryService;
import com.stb.credit.service.LoanRequestService;
import com.stb.credit.service.PdfReportService;

import jakarta.transaction.Transactional;

@Service
public class LoanRequestServiceImpl implements LoanRequestService {

    @Autowired
    private LoanRequestRepository loanRequestRepository;
    

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private CreditSimulationRepository simulationRepository;
    
    @Autowired
    private NotificationClient notificationClient;

    @Autowired
    private ModelMapper modelMapper;
    

	@Autowired
    private PdfReportService pdfReportService;
	
	@Autowired
    private DocumentRepository documentRepository;
	
	@Autowired
    private CloudinaryService cloudinaryService;
	
	@Autowired
	private UserClient userClient; 
	
	@Value("${app.frontend.url}")
	private String frontendUrl;

    @Override
    public LoanRequestDTO createLoanRequest(LoanRequestDTO dto) {
        LoanRequest loanRequest = modelMapper.map(dto, LoanRequest.class);
        loanRequest.setAccountNumber(generateAccountNumber());


        if (dto.getCustomer() != null && dto.getCustomer().getId() != null) {
            Customer existingCustomer = customerRepository.findById(dto.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
            modelMapper.map(dto.getCustomer(), existingCustomer); 
            loanRequest.setCustomer(existingCustomer);
        }

        if (dto.getSimulationId() != null) {
            CreditSimulation simulation = simulationRepository.findById(dto.getSimulationId())
                    .orElseThrow(() -> new RuntimeException("Simulation not found"));

            if (simulation.getCreditType() == null) {        
                throw new IllegalStateException("Simulation has no credit type");
            }

            simulation.setEnabled(false);
            simulationRepository.save(simulation);
            loanRequest.setCreditType(simulation.getCreditType().getType());
        }

        LoanRequest saved = loanRequestRepository.save(loanRequest);
        LoanRequestDTO result = modelMapper.map(saved, LoanRequestDTO.class);

        sendMail(result);

        return result;
    }

    private String generateAccountNumber() {
        String prefix = "001-";
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomPart = String.format("%06d", new Random().nextInt(999999));
        return prefix + datePart + randomPart;
    }

    void generateLoanRequestPdf(LoanRequest saved){

    	byte[] pdfBytes = pdfReportService.generateLoanRequestPdf(saved);


    	String pdfUrl = cloudinaryService.uploadFile(pdfBytes, "loan_request_" + saved.getId());


    	Document document = new Document();
    	document.setUrl(pdfUrl);
    	document.setCustomerId(saved.getCustomer().getId());
    	document.setLoanRequestId(saved.getId());
    	document.setName("Loan Request PDF");

    	documentRepository.save(document);
    }

    void sendMail(LoanRequestDTO loan) {
        List<UserDTO> bankers = userClient.getUsersByAgence(loan.getAgence());

        Map<String, Object> model = new HashMap<>();
        model.put("customerName", loan.getCustomer() != null ? loan.getCustomer().getFullName() : "Unknown");
        model.put("customerEmail", loan.getCustomer() != null ? loan.getCustomer().getEmail() : "unknown");
        model.put("accountNumber", loan.getAccountNumber());
        model.put("loanAmount", loan.getLoanAmount()); 
        model.put("loanType", loan.getCreditType());
        model.put("submissionDate", LocalDate.now().toString());
        model.put("url", frontendUrl);

        bankers.stream()
        .map(UserDTO::getEmail)
        .filter(StringUtils::hasText)
        .forEach(emailAddress -> {
            EmailDTO email = new EmailDTO();
            email.setTo(emailAddress);
            email.setSubject("🔔 New Loan Request from " + model.get("customerName"));
            email.setTemplateName("new-loan-request");
            email.setTemplateModel(model);

            notificationClient.sendEmail(email);
        });
    }


    @Override
    public List<LoanRequestDTO> getAllLoanRequests() {
        List<LoanRequest> requests = loanRequestRepository.findAll();
        return requests.stream()
                       .map(request -> modelMapper.map(request, LoanRequestDTO.class))
                       .toList();
    }

    @Override
    public LoanRequestDTO getLoanRequestById(Long id) {
        LoanRequest loanRequest = loanRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LoanRequest not found with id: " + id));
        return modelMapper.map(loanRequest, LoanRequestDTO.class);
    }
    public List<LoanRequestDTO> getLoanRequestsByCustomerId(Long customerId) {
        List<LoanRequest> requests = loanRequestRepository.findByCustomerId(customerId);
        return requests.stream()
                       .map(loanRequest -> modelMapper.map(loanRequest, LoanRequestDTO.class))
                       .collect(Collectors.toList());
    }
    
    public List<LoanRequestDTO> getLoanRequestsByAgence(String agence) {
        List<LoanRequest> requests = loanRequestRepository.findByAgence(agence);
        return requests.stream()
                       .map(loanRequest -> modelMapper.map(loanRequest, LoanRequestDTO.class))
                       .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public LoanRequestDTO updateLoanRequest(LoanRequestDTO dto) {
        LoanRequest existing = loanRequestRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Loan request not found"));

        if (dto.getCreditType() == null) {
            dto.setCreditType(existing.getCreditType());
        }


        modelMapper.map(dto, existing);

        if (dto.getCustomer() != null && dto.getCustomer().getId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
            modelMapper.map(dto.getCustomer(), customer);
            existing.setCustomer(customer);
        }

        LoanRequest saved = loanRequestRepository.save(existing);
        LoanRequestDTO savedDTO = modelMapper.map(saved, LoanRequestDTO.class);
        if(saved.getStep() == 1) {
        generateLoanRequestPdf(saved);
        }
        if(saved.getStep() == 3) {
        	saveDocs(saved);
        }
        else if (saved.getStep() == 4) {
        	generateLoanContract(saved);
        }
        
        sendCustomerStatusUpdate(savedDTO);
        return savedDTO;
    }
    
    void generateLoanContract(LoanRequest saved) {
        byte[] pdfBytes = pdfReportService.generateLoanContract(saved);

        String pdfUrl = cloudinaryService.uploadFile(pdfBytes, "loan_contract_" + saved.getId());

        Document document = new Document();
        document.setUrl(pdfUrl);
        document.setCustomerId(saved.getCustomer().getId());
        document.setLoanRequestId(saved.getId());
        document.setName("Loan Contract");

        documentRepository.save(document);
    }
    
    private void saveDocs(LoanRequest saved) {
    	
    	List<Document> listDocs = new ArrayList<>();
    	
    	Document document = new Document();
    	document.setCustomerId(saved.getCustomer().getId());
    	document.setLoanRequestId(saved.getId());
    	document.setName("CIN");
    	listDocs.add(document);
    	
    	Document document2 = new Document();
    	document2.setCustomerId(saved.getCustomer().getId());
    	document2.setLoanRequestId(saved.getId());
    	document2.setName("domiciliation de salaire");
    	listDocs.add(document2);
    	
    	Document document3 = new Document();
    	document3.setCustomerId(saved.getCustomer().getId());
    	document3.setLoanRequestId(saved.getId());
    	document3.setName("Fiche de paie");
    	listDocs.add(document3);
    	
    	documentRepository.saveAll(listDocs);
    	
    }

    @Override
    @Transactional
    public void attachSignatureToLoanRequest(Long loanRequestId, String signatureUrl) throws IOException {
        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new RuntimeException("Loan request not found"));

        Document document = documentRepository.findByLoanRequestId(loanRequestId)
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Loan request PDF not found"));

        byte[] pdfBytes = cloudinaryService.downloadFile(document.getUrl());

        byte[] signatureBytes = cloudinaryService.downloadFile(signatureUrl);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(
                new PdfReader(new ByteArrayInputStream(pdfBytes)),
                new PdfWriter(baos)
        );
        com.itextpdf.layout.Document doc = new com.itextpdf.layout.Document(pdfDoc);

        ImageData imageData = ImageDataFactory.create(signatureBytes);
        Image signatureImage = new Image(imageData);

        float boxX = 100;      
        float boxY = 120;      
        float boxWidth = 400;
        float boxHeight = 100;

        signatureImage.scaleToFit(boxWidth - 40, boxHeight - 40);

        float marginLeft = 15;   
        float marginTop = 1;    

        signatureImage.setFixedPosition(
                boxX + marginLeft,
                boxY + boxHeight - marginTop - signatureImage.getImageScaledHeight()
        );

        doc.add(signatureImage);
        doc.close();

        String signedPdfUrl = cloudinaryService.uploadFile(
                baos.toByteArray(),
                "loan_request_signed_" + loanRequestId
        );

        document.setUrl(signedPdfUrl);
        document.setName("Signed Loan Request PDF");
        documentRepository.save(document);
    }

    void sendCustomerStatusUpdate(LoanRequestDTO loan) {
        String customerEmail = loan.getCustomer() != null ? loan.getCustomer().getEmail() : null;
        if (!StringUtils.hasText(customerEmail)) {
            return;   
        }

        String libelle = loan.getLibelle() == null ? "En cours" : loan.getLibelle();
        String subject;
        String bodyLine;

        if (loan.getStep() == -1 && "rejected".equalsIgnoreCase(libelle)) {
            subject = "Votre demande de crédit a été refusée";
            bodyLine = "Nous regrettons de vous informer que votre demande de crédit a été refusée après examen. "
                     + "Pour plus d'informations, veuillez contacter votre conseiller bancaire.";
        }
        else if (loan.getStep() == 1 && "sign-pre-contract".equalsIgnoreCase(libelle)) {
            subject = "Votre demande de crédit a été acceptée";
            bodyLine = "Félicitations ! Votre demande de crédit a été acceptée. "
                     + "Vous pouvez désormais signer le contrat préliminaire en ligne.";
        }
        else if (loan.getStep() == 5) {
            subject = "Votre crédit a été émis avec succès";
            bodyLine = "Félicitations ! Votre crédit a été émis avec succès. "
                     + "Vous pouvez vous rendre à l’agence "
                     + (loan.getAgence() != null ? loan.getAgence() : "bancaire")
                     + " pour retirer vos fonds.";        subject = "Votre crédit a été émis avec succès";
                     bodyLine = "Félicitations ! Votre crédit a été émis avec succès. "
                             + "Vous pouvez vous rendre à l’agence "
                             + (loan.getAgence() != null ? loan.getAgence() : "bancaire")
                             + " pour retirer vos fonds.";
        }
        else {
            subject = "Mise à jour de votre demande de crédit";
            bodyLine = "Nous vous informons que le statut de votre demande de crédit a été mis à jour : "
                     + libelle + ".";
        }

        Map<String, Object> model = new HashMap<>();
        model.put("customerName", loan.getCustomer().getFullName());
        model.put("accountNumber", loan.getAccountNumber());
        model.put("loanType", loan.getCreditType());
        model.put("loanAmount", loan.getLoanAmount());
        model.put("libelle", libelle);
        model.put("bodyLine", bodyLine);
        model.put("actionUrl", frontendUrl);
        
        EmailDTO email = new EmailDTO();
        email.setTo(customerEmail);
        email.setSubject(subject);
        email.setTemplateName("customer-status-update");
        email.setTemplateModel(model);
        notificationClient.sendEmail(email);
    }

    @Override
    @Transactional
    public void attachSignatureToFinalContract(Long loanRequestId, String signatureUrl) throws IOException {
        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new RuntimeException("Loan request not found"));


        Document document = documentRepository.findByLoanRequestId(loanRequestId)
                .stream()
                .filter(doc -> "Loan Contract".equalsIgnoreCase(doc.getName()))  // <- filtrer le bon
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Loan Contract PDF not found"));

        byte[] pdfBytes = cloudinaryService.downloadFile(document.getUrl());

        byte[] signatureBytes = cloudinaryService.downloadFile(signatureUrl);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(
                new PdfReader(new ByteArrayInputStream(pdfBytes)),
                new PdfWriter(baos)
        );
        com.itextpdf.layout.Document doc = new com.itextpdf.layout.Document(pdfDoc);

        ImageData imageData = ImageDataFactory.create(signatureBytes);
        Image signatureImage = new Image(imageData);

        float boxX = 320;  
        float boxY = 40;   
        float boxWidth = 200;
        float boxHeight = 60;

        signatureImage.scaleToFit(boxWidth - 20, boxHeight - 20);
        signatureImage.setFixedPosition(
                boxX + 10,
                boxY + (boxHeight - signatureImage.getImageScaledHeight()) / 2
        );

        doc.add(signatureImage);
        doc.close();

        String signedPdfUrl = cloudinaryService.uploadFile(
                baos.toByteArray(),
                "loan_contract_signed_" + loanRequestId
        );

        document.setUrl(signedPdfUrl);
        document.setName("Signed Loan Contract");
        documentRepository.save(document);
    }

}
