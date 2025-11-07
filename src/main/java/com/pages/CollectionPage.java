package com.pages;

import java.util.List;
import java.util.Map;

import com.microsoft.playwright.Page;
import com.utils.*;

public class CollectionPage {
    private final Page page;

    public CollectionPage(Page page) {
        this.page = page;
    }

    public List<Map<String, String>> extractVisibleRows(int limit) {
        return WebTableExtractor.extractVisibleRows(page, limit);
    }

    public List<Map<String, String>> extractAllRows() {
        return WebTableExtractor.extractAllRows(page);
    }

    public void applyFilters() {
        FilterApplier.applyFilters(page);
        page.waitForTimeout(5000);
    }
}
