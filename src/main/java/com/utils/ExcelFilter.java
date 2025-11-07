package com.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Applies filters to Excel data:
 *  - Status = "Active"
 *  - Amount >= 1000
 *  - CreatedDate starts with "2023"
 */
public class ExcelFilter {

    public static List<Map<String, String>> applyFilters(List<Map<String, String>> excelData) {
        List<Map<String, String>> filtered = new ArrayList<>();

        for (Map<String, String> row : excelData) {
            try {
                String status = row.getOrDefault("Status", "").trim();
                String amountStr = row.getOrDefault("Amount", "0").replaceAll("[^0-9.]", "");
                String createdDate = row.getOrDefault("CreatedDate", "").trim();

                double amount = Double.parseDouble(amountStr.isEmpty() ? "0" : amountStr);

                if (status.equalsIgnoreCase("Active") && amount >= 1000 && createdDate.startsWith("2023")) {
                    filtered.add(row);
                }
            } catch (Exception ignored) {
            }
        }
        return filtered;
    }
}
