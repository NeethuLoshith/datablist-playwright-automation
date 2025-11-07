package com.utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class FilterApplier {
    public static void applyFilters(Page page) {
    
    		page.getByRole(AriaRole.BUTTON).nth(3).click();

    		page.getByLabel("Select a property").selectOption("status");
    		page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter a value *")).fill("Active");
    		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add filter").setExact(true)).click();

    		page.getByLabel("Select a property").nth(1).selectOption("amount");
    		page.locator("#amount-eq").selectOption("ge");
    		page.getByRole(AriaRole.SPINBUTTON, new Page.GetByRoleOptions().setName("Enter a value *")).fill("1000");
    		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add filter").setExact(true)).click();

    		page.getByLabel("Select a property").nth(2).selectOption("createddate");
    		page.locator("#createddate-eq").selectOption("startsWith");
    		page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter a value *")).nth(1).fill("2023");
    		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply")).click();
    	}
    }

