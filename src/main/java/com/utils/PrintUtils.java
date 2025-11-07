package com.utils;

import java.util.List;
import java.util.Map;

public class PrintUtils {
	public static void printRows(List<Map<String, String>> data, int start, int end, String title) {

		System.out.println("\n=== " + title + " ===");
		for (int i = start; i < Math.min(end, data.size()); i++) {
			System.out.println("Row " + (i + 1) + ": " + data.get(i));
		}
	}
}
