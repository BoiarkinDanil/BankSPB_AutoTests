package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchPage extends BasePage {
    private final By searchInput = By.cssSelector("input[placeholder='Введите поисковый запрос']");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void enterSearchQuery(String query) {
        type(searchInput, query);
    }

    public String getSearchText() {
        return driver.findElement(searchInput).getAttribute("value");
    }
}