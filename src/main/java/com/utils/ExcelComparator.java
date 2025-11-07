package com.utils;

import java.util.List;
import java.util.Map;

public class ExcelComparator {

    /**
     * Compares two datasets (Excel vs Web). Prints mismatches and returns true if all match.
     */
    public static void compare(List<Map<String, String>> excelData, List<Map<String, String>> webData) {

        int mismatchCount = 0;

        for (int i = 0; i < Math.min(excelData.size(), webData.size()); i++) {
            Map<String, String> e = excelData.get(i);
            Map<String, String> w = webData.get(i);

            for (String key : e.keySet()) {
                String eVal = e.getOrDefault(key, "").trim();
                String wVal = w.getOrDefault(key, "").trim();

                if (key.equalsIgnoreCase("CustomerId")) {
                    while (eVal.length() < 6)
                        eVal = "0" + eVal;
                    while (wVal.length() < 6)
                        wVal = "0" + wVal;
                }

                if (key.equalsIgnoreCase("Amount")) {
                    try {
                        double eNum = Double.parseDouble(eVal.replaceAll("[^0-9.]", ""));
                        double wNum = Double.parseDouble(wVal.replaceAll("[^0-9.]", ""));
                        eVal = String.format("%.2f", eNum);
                        wVal = String.format("%.2f", wNum);
                    } catch (Exception ignored) {}
                }

                if (key.equalsIgnoreCase("CreatedDate")) {
                    eVal = eVal.replaceAll("[^0-9]", "");
                    wVal = wVal.replaceAll("[^0-9]", "");
                }

                if (!eVal.equals(wVal)) {
                    mismatchCount++;
                    System.out.println("❌ Row " + (i + 1) + " | Col=" + key + " | Excel=" + eVal + " | Web=" + wVal);
                }
            }
        }

        System.out.println("\n✅ Comparison complete. Mismatches found: " + mismatchCount);
    }
}
