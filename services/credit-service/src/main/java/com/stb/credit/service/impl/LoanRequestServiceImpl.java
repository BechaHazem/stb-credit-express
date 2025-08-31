package com.stb.credit.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public LoanRequestDTO createLoanRequest(LoanRequestDTO dto) {
        LoanRequest loanRequest = modelMapper.map(dto, LoanRequest.class);
        loanRequest.setAccountNumber(generateAccountNumber());
        // Update existing customer if provided
        if (dto.getCustomer() != null && dto.getCustomer().getId() != null) {
            Customer existingCustomer = customerRepository.findById(dto.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
            modelMapper.map(dto.getCustomer(), existingCustomer); // merge changes
            loanRequest.setCustomer(existingCustomer);
        }

        // Update simulation if simulationId is provided
        if (dto.getSimulationId() != null) {
            CreditSimulation simulation = simulationRepository.findById(dto.getSimulationId())
                .orElseThrow(() -> new RuntimeException("Simulation not found"));
            simulation.setEnabled(false); // disable it
            simulationRepository.save(simulation); // persist change
        }
        
        LoanRequest saved = loanRequestRepository.save(loanRequest);
        LoanRequestDTO result = modelMapper.map(saved, LoanRequestDTO.class);

//        generateLoanRequestPdf(saved);
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
        model.put("url", "http://localhost:4200/loan-requests/");


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

        // Map all incoming fields onto the existing entity
        modelMapper.map(dto, existing);

        // If customer data came in the DTO, merge it too
        if (dto.getCustomer() != null && dto.getCustomer().getId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
            modelMapper.map(dto.getCustomer(), customer);
            existing.setCustomer(customer);
        }

        LoanRequest saved = loanRequestRepository.save(existing);
        LoanRequestDTO savedDTO = modelMapper.map(saved, LoanRequestDTO.class);
        sendCustomerStatusUpdate(savedDTO);
        return savedDTO;
    }

    void sendCustomerStatusUpdate(LoanRequestDTO loan) {
        // 1. recipient
        String customerEmail = loan.getCustomer() != null ? loan.getCustomer().getEmail() : null;
        if (!StringUtils.hasText(customerEmail)) {
            return;   // nothing to do
        }

        // 2. derive wording
        String libelle = loan.getLibelle() == null ? "En cours" : loan.getLibelle();
        String subject;
        String bodyLine;

        if (loan.getStep() == 1 && "sign-pre-contract".equalsIgnoreCase(libelle)) {
            subject = "Votre demande de crédit a été acceptée";
            bodyLine = "Félicitations ! Votre demande de crédit a été acceptée. "
                     + "Vous pouvez désormais signer le contrat préliminaire en ligne.";
        } else {
            subject = "Mise à jour de votre demande de crédit";
            bodyLine = "Nous vous informons que le statut de votre demande de crédit a été mis à jour : "
                     + libelle + ".";
        }

        // 3. build model
        Map<String, Object> model = new HashMap<>();
        model.put("customerName", loan.getCustomer().getFullName());
        model.put("accountNumber", loan.getAccountNumber());
        model.put("loanType", loan.getCreditType());
        model.put("loanAmount", loan.getLoanAmount());
        model.put("libelle", libelle);
        model.put("bodyLine", bodyLine);
        model.put("actionUrl", "http://localhost:4200/loan-requests/" + loan.getAccountNumber());

        // 4. send
        EmailDTO email = new EmailDTO();
        email.setTo(customerEmail);
        email.setSubject(subject);
        email.setTemplateName("customer-status-update");
        email.setTemplateModel(model);
        notificationClient.sendEmail(email);
    }
}
