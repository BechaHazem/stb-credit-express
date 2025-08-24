package com.stb.credit.service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
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
                    .setMarginTop(20));

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
}
