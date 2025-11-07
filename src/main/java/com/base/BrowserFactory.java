package com.base;

import com.microsoft.playwright.*;

public class BrowserFactory {
    private static Playwright playwright;
    private static Browser browser;
    private static Page page;

    public static Page initBrowser(boolean headless) {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(headless));
        page = browser.newPage();
        return page;
    }

    public static void closeBrowser() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
