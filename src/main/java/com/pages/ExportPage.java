package com.pages;

import java.nio.file.*;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class ExportPage {
    private final Page page;
    private final Path downloadsDir;

    public ExportPage(Page page, Path downloadsDir) {
        this.page = page;
        this.downloadsDir = downloadsDir;
    }

    public Path exportFilteredData() throws Exception {
        if (!Files.exists(downloadsDir)) Files.createDirectories(downloadsDir);

        Download download = page.waitForDownload(() -> {
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Export")).click();
            page.getByText("Export filtered items").click();
            page.locator("#modal").getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Export")).click();
        });

        Path exportedFile = downloadsDir.resolve("exported_filtered.xlsx");
        download.saveAs(exportedFile);
        return exportedFile;
    }
}
