package com.pages;

import java.nio.file.Path;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class HomePage {
    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public void open() {
        page.navigate("https://app.datablist.com/home");
        page.waitForLoadState();
    }

    public void uploadFile(Path excelPath) {
        page.getByText("Start with a CSV/Excel fileImport data from a CSV or Excel file into a").click();
        FileChooser fc = page.waitForFileChooser(() -> page.locator(".inline-flex").click());
        fc.setFiles(excelPath);
    }

    public void completeImport() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue to properties")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Import 40 items")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Back to collection")).click();
        page.waitForTimeout(4000);
    }
}
