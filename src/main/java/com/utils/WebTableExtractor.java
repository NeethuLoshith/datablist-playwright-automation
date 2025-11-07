package com.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.*;

/**
 * Utility class for extracting structured data from web tables in Datablist.
 */
public class WebTableExtractor {

    /**
     * Extracts only the first N visible rows from the current web table.
     */
    public static List<Map<String, String>> extractVisibleRows(Page page, int limit) {
        List<String> rawHeaders = page.locator("div#columns .column-label").allInnerTexts();

        // Remove duplicate headers
        Set<String> seen = new HashSet<>();
        List<String> headers = new ArrayList<>();
        for (String h : rawHeaders) {
            if (seen.add(h)) headers.add(h.trim());
        }

        Locator rows = page.locator("div.row_virtual");
        int rowCount = Math.min(rows.count(), limit);
        List<Map<String, String>> tableData = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            Locator row = rows.nth(i);
            List<String> cells = row.locator("div.cell .cell-inner span").allInnerTexts();

            Map<String, String> rowData = new LinkedHashMap<>();
            for (int j = 0; j < Math.min(headers.size(), cells.size()); j++) {
                String value = cells.get(j).trim();
                if (headers.get(j).equalsIgnoreCase("CustomerId") && value.length() < 6) {
                    while (value.length() < 6) value = "0" + value;
                }
                rowData.put(headers.get(j), value);
            }
            tableData.add(rowData);
        }

        return tableData;
    }

    /**
     * Extracts all rows by repeatedly scrolling the web table until all rows are loaded.
     */
    public static List<Map<String, String>> extractAllRows(Page page) {
        System.out.println("⏳ Waiting for initial table to load...");
        page.waitForTimeout(4000);

        List<String> rawHeaders = page.locator("div#columns .column-label").allInnerTexts();
        Set<String> seen = new HashSet<>();
        List<String> headers = new ArrayList<>();
        for (String h : rawHeaders) {
            if (seen.add(h)) headers.add(h.trim());
        }

        List<Map<String, String>> tableData = new ArrayList<>();
        int previousCount = -1;
        int scrollAttempts = 0;

        while (true) {
            Locator rows = page.locator("div.row_virtual");
            int currentCount = rows.count();

            for (int i = 0; i < currentCount; i++) {
                Locator row = rows.nth(i);
                List<String> cells = row.locator("div.cell .cell-inner span").allInnerTexts();

                Map<String, String> rowData = new LinkedHashMap<>();
                for (int j = 0; j < Math.min(headers.size(), cells.size()); j++) {
                    String value = cells.get(j).trim();
                    if (headers.get(j).equalsIgnoreCase("CustomerId") && value.length() < 6) {
                        while (value.length() < 6) value = "0" + value;
                    }
                    rowData.put(headers.get(j), value);
                }

                if (!tableData.contains(rowData)) {
                    tableData.add(rowData);
                }
            }

            System.out.println("📊 Captured " + tableData.size() + " total rows so far...");
            page.waitForTimeout(1500);

            System.out.println("🔽 Scrolling down (attempt " + (++scrollAttempts) + ")...");
            page.mouse().wheel(0, 2000);
            page.waitForTimeout(3000);

            if (tableData.size() == previousCount || scrollAttempts >= 10) {
                System.out.println("✅ Scrolling complete. Final row count: " + tableData.size());
                break;
            }

            previousCount = tableData.size();
        }

        page.waitForTimeout(2000);
        return tableData;
    }
}
