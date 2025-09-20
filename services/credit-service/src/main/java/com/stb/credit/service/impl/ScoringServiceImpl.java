package com.stb.credit.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stb.credit.dto.CustomerDTO;
import com.stb.credit.dto.LoanRequestDTO;
import com.stb.credit.service.CloudinaryService;
import com.stb.credit.service.CustomerService;
import com.stb.credit.service.ScoringService;
@Service
public class ScoringServiceImpl implements ScoringService {

	

    @Autowired
    private CustomerService customerService;


	@Autowired
    private CloudinaryService cloudinaryService;
	
	@Override
	public Map<String, Object> calculateScore(LoanRequestDTO loan) {
	    try {
	        // Replace with your actual Cloudinary file URL
	        String cloudinaryFileUrl = "https://res.cloudinary.com/dl0lojylt/raw/upload/v1757009255/CUSTOMERSCORE_jm0qeg.xlsx";

	        // Download file as byte array
	        byte[] fileBytes = cloudinaryService.downloadFile(cloudinaryFileUrl);

	        // Create workbook from byte array
	        try (InputStream inputStream = new ByteArrayInputStream(fileBytes);
	             XSSFWorkbook finWorkBook = new XSSFWorkbook(inputStream)) {

	            XSSFSheet outSheet = finWorkBook.getSheet("Output");
	            XSSFSheet inSheet = finWorkBook.getSheet("Input");

	            setInputsToExcelSheet(loan, inSheet);

	            // Recalculate all formulas
	            finWorkBook.setForceFormulaRecalculation(true);
	            XSSFFormulaEvaluator.evaluateAllFormulaCells(finWorkBook);

	            XSSFFormulaEvaluator evaluator = finWorkBook.getCreationHelper()
                        .createFormulaEvaluator();
	            evaluator.evaluateAll();  
	            Map<String, Object> mapResults = new HashMap<>();
	            for (int rowIndex = 1; rowIndex <= outSheet.getLastRowNum(); rowIndex++) {
	                Row row = outSheet.getRow(rowIndex);
	                if (row == null) continue;

	                Cell keyCell = row.getCell(0);
	                Cell valueCell = row.getCell(1);

	                if (keyCell != null && valueCell != null) {
	                    String cellKey = keyCell.getStringCellValue();
//	                    if (cellKey != null && !cellKey.isBlank()) {
//	                        mapResults.put(cellKey, readCellValue(valueCell));
//	                    }
	                	if (valueCell.getCellType() == org.apache.poi.ss.usermodel.CellType.FORMULA) {
	                	    evaluator.evaluateInCell(valueCell);
	                	}
	                    mapResults.put(cellKey, readCellValue(valueCell));
	                    
	                }
	            }
	            if (mapResults != null && mapResults.containsKey("score")) {
	                BigDecimal score = new BigDecimal(mapResults.get("score").toString());
	                customerService.updateScore(loan.getCustomer().getId(), score); 

	            }
	            return mapResults;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	private Object readCellValue(Cell cell) {
	    if (cell == null) return null;

	    switch (cell.getCellType()) {
	        case NUMERIC:
	            return new BigDecimal(cell.getNumericCellValue());
	        case STRING:
	            return cell.getStringCellValue();
	        case BOOLEAN:
	            return cell.getBooleanCellValue();
	        case BLANK:
	            return null;
	        default:
	            /* if somehow still error, return raw string */
	            return cell.toString();
	    }
	}
	
	private void setInputsToExcelSheet(LoanRequestDTO loan, XSSFSheet inputSheet) {
	    BiConsumer<String, Object> write = (k, v) -> {
	        for (Row r : inputSheet) {
	            Cell labelCell = r.getCell(0);
	            if (labelCell != null && k.equals(labelCell.getStringCellValue())) {
	                Cell valueCell = r.getCell(1);
	                if (valueCell == null) valueCell = r.createCell(1);
	                if (v instanceof Number) valueCell.setCellValue(((Number) v).doubleValue());
	                else valueCell.setCellValue(String.valueOf(v));
	                return;
	            }
	        }
	    };

	    /* ---------- Loan ---------- */
	    write.accept("loanAmount",      loan.getLoanAmount());
	    write.accept("loanDuration",    loan.getLoanDuration());
	    write.accept("gracePeriod",     loan.getGracePeriod());
	    write.accept("creditType",      loan.getCreditType());
	    write.accept("loanPurpose",     loan.getLoanPurpose());

	    /* ---------- Customer ---------- */
	    CustomerDTO c = loan.getCustomer();
	    write.accept("profession",      c.getProfession());
	    write.accept("employer",        c.getEmployer());
	    write.accept("city",            c.getCity());
	    write.accept("postalCode",      c.getPostalCode());
	    write.accept("age",             c.getAge());
	    write.accept("monthlyIncome",   c.getMonthlyIncome());
	    write.accept("monthlyExpenses", c.getMonthlyExpenses());
	}
	
}
