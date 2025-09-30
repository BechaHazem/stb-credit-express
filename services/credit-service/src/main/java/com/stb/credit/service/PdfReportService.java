package com.stb.credit.service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.stb.credit.models.LoanRequest;

@Service
public class PdfReportService {

    public byte[] generateLoanRequestPdf(LoanRequest loanRequest) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Title
            document.add(new Paragraph("Loan Request Report")
                    .setFontSize(18)
                    .setBold()
                    .setMarginBottom(20));

            // Customer Info
            document.add(new Paragraph("Customer Information").setBold());
            Table customerTable = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                    .useAllAvailableWidth();
            addCell(customerTable, "Full Name:");
            addCell(customerTable, loanRequest.getCustomer().getFullName());
            addCell(customerTable, "Email:");
            addCell(customerTable, loanRequest.getCustomer().getEmail());
            addCell(customerTable, "ID Type:");
            addCell(customerTable, loanRequest.getCustomer().getIdType());
            addCell(customerTable, "ID Number:");
            addCell(customerTable, loanRequest.getCustomer().getIdNumber());
            addCell(customerTable, "ID Issue Date:");
            addCell(customerTable, loanRequest.getCustomer().getIdIssueDate());
            addCell(customerTable, "Phone:");
            addCell(customerTable, loanRequest.getCustomer().getPhone());
            addCell(customerTable, "Address:");
            addCell(customerTable, loanRequest.getCustomer().getAddress() + ", " +
                    loanRequest.getCustomer().getCity() + " " + loanRequest.getCustomer().getPostalCode());
            addCell(customerTable, "Profession:");
            addCell(customerTable, loanRequest.getCustomer().getProfession());
            addCell(customerTable, "Employer:");
            addCell(customerTable, loanRequest.getCustomer().getEmployer());
            document.add(customerTable);

            document.add(new Paragraph("\nLoan Details").setBold());

            Table loanTable = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                    .useAllAvailableWidth();
            addCell(loanTable, "Credit Type:");
            addCell(loanTable, loanRequest.getCreditType());
            addCell(loanTable, "Account Number:");
            addCell(loanTable, loanRequest.getAccountNumber());
            addCell(loanTable, "Loan Purpose:");
            addCell(loanTable, loanRequest.getLoanPurpose());
            addCell(loanTable, "Loan Amount:");
            addCell(loanTable, loanRequest.getLoanAmount().toString());
            addCell(loanTable, "Duration (months):");
            addCell(loanTable, loanRequest.getLoanDuration().toString());
            addCell(loanTable, "Grace Period (months):");
            addCell(loanTable, loanRequest.getGracePeriod().toString());
//            addCell(loanTable, "Documents:");
//            addCell(loanTable, loanRequest.getDocuments() != null ? loanRequest.getDocuments() : "-");
            document.add(loanTable);

         // Add electronic signature section
            document.add(new Paragraph("\n\nElectronic Signature")
                    .setBold()
                    .setFontSize(14)
                    .setMarginTop(30));

            Table signatureTable = new Table(UnitValue.createPercentArray(new float[]{1}))
                    .useAllAvailableWidth();
            Cell signatureCell = new Cell()
                    .add(new Paragraph("\n\n\n\n")) // empty space for signature
                    .setBorder(new SolidBorder(1)) 
                    .setHeight(100) // height of the signature box
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            signatureTable.addCell(signatureCell);

            document.add(signatureTable);

            // Optionally, add note
            document.add(new Paragraph("Please sign inside the box above.").setItalic().setFontSize(10));

            
            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    private void addCell(Table table, String content) {
        table.addCell(content != null ? content : "-");
    }
    
    public byte[] generateLoanContract(LoanRequest loanRequest) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf);

            // --- Title / Cover ---
            doc.add(new Paragraph("Loan Contract")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            doc.add(new Paragraph("This Loan Contract is made between:")
                    .setFontSize(12)
                    .setMarginBottom(10));

            doc.add(new Paragraph("The Lender: STB\n"
                                + "and\n\n"
                                + "The Borrower: " + loanRequest.getCustomer().getFullName() + "\n"
                                + "Address: " + loanRequest.getCustomer().getAddress() + ", "
                                + loanRequest.getCustomer().getCity() + " " + loanRequest.getCustomer().getPostalCode())
                    .setFontSize(11)
                    .setTextAlignment(TextAlignment.JUSTIFIED)
                    .setMarginBottom(20));

            // --- Contract Clauses ---
            doc.add(new Paragraph("Article 1: Loan Details")
                    .setBold()
                    .setMarginTop(15));
            doc.add(new Paragraph("The Lender agrees to grant the Borrower a loan with the following terms: \n"
                    + "- Loan Amount: " + loanRequest.getLoanAmount() + " TND\n"
                    + "- Duration: " + loanRequest.getLoanDuration() + " months\n"
                    + "- Grace Period: " + loanRequest.getGracePeriod() + " months\n"
                    + "- Purpose: " + loanRequest.getLoanPurpose())
                    .setFontSize(11)
                    .setTextAlignment(TextAlignment.JUSTIFIED));

            doc.add(new Paragraph("Article 2: Interest and Repayment")
                    .setBold()
                    .setMarginTop(15));
            doc.add(new Paragraph("The Borrower agrees to repay the loan amount plus applicable interest in equal "
                    + "monthly installments over the agreed duration. Payments must be made to the account number "
                    + loanRequest.getAccountNumber() + ". Late payments may incur penalties as per bank policy.")
                    .setFontSize(11)
                    .setTextAlignment(TextAlignment.JUSTIFIED));

            doc.add(new Paragraph("Article 3: Borrower Obligations")
                    .setBold()
                    .setMarginTop(15));
            doc.add(new Paragraph("The Borrower shall provide truthful information and use the loan strictly for "
                    + "the declared purpose. Failure to comply will entitle the Lender to terminate this contract "
                    + "and demand immediate repayment.")
                    .setFontSize(11)
                    .setTextAlignment(TextAlignment.JUSTIFIED));

            doc.add(new Paragraph("Article 4: Guarantees and Collateral")
                    .setBold()
                    .setMarginTop(15));
            doc.add(new Paragraph("If applicable, the Borrower provides guarantees or collateral as agreed with the Lender. "
                    + "Details of collateral shall be recorded in a separate annex to this contract.")
                    .setFontSize(11)
                    .setTextAlignment(TextAlignment.JUSTIFIED));

            doc.add(new Paragraph("Article 5: Governing Law")
                    .setBold()
                    .setMarginTop(15));
            doc.add(new Paragraph("This contract is governed by the laws of [Your Country]. Any disputes shall be resolved "
                    + "before the competent courts of [City].")
                    .setFontSize(11)
                    .setTextAlignment(TextAlignment.JUSTIFIED));

            // --- Signature Section ---
            doc.add(new Paragraph("\n\n\nSignatures").setBold().setFontSize(14).setMarginTop(30));

            Table signatureTable = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                    .useAllAvailableWidth()
                    .setMarginTop(20);

            doc.add(signatureTable);

            doc.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating Loan Contract PDF", e);
        }
    }

}
