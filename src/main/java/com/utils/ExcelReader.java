package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Utility class to read Excel (.xlsx) and CSV (.csv) files
 * into List<Map<String, String>> format.
 */
public class ExcelReader {

    // ------------------- Excel Read -------------------
	public static List<Map<String, String>> readExcelFile(String filePath) throws Exception {
	    List<Map<String, String>> data = new ArrayList<>();

	    try (FileInputStream fis = new FileInputStream(filePath);
	         Workbook workbook = new XSSFWorkbook(fis)) {

	        Sheet sheet = workbook.getSheetAt(0);
	        Iterator<Row> iterator = sheet.iterator();
	        if (!iterator.hasNext()) return data;

	        // Read header
	        Row headerRow = iterator.next();
	        List<String> headers = new ArrayList<>();
	        for (Cell cell : headerRow) {
	            headers.add(cell.getStringCellValue().trim());
	        }

	        // Use DataFormatter for reading all types (numeric, string, date, etc.)
	        DataFormatter formatter = new DataFormatter();

	        // Read data rows
	        while (iterator.hasNext()) {
	            Row row = iterator.next();
	            Map<String, String> rowData = new LinkedHashMap<>();

	            for (int i = 0; i < headers.size(); i++) {
	                Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	                String cellValue = formatter.formatCellValue(cell).trim();
	                rowData.put(headers.get(i), cellValue);
	            }

	            data.add(rowData);
	        }
	    }

	    return data;
	}

    // ------------------- CSV Read -------------------
    public static List<Map<String, String>> readCsvFile(String filePath) throws Exception {
        List<Map<String, String>> data = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            if (!scanner.hasNextLine()) return data; // empty file

            String[] headers = scanner.nextLine().split(",");
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");
                Map<String, String> row = new LinkedHashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    row.put(headers[i].trim(), i < values.length ? values[i].trim() : "");
                }
                data.add(row);
            }
        }

        return data;
    }
}
