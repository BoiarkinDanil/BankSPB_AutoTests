package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class PrivateBankingPage extends BasePage {
    private final By arrowButton = By.className("t-cover__arrow-svg");
    private final By goldButton = By.xpath("//button[contains(text(), 'ЗОЛОТО')]");
    private final By investmentProductsLink = By.partialLinkText("Инвестиционные продукты");
    private final By currencyInput = By.xpath("//input[@type='text' and @inputmode='decimal']");
    public PrivateBankingPage(WebDriver driver) {
        super(driver);
    }

    public void clickArrowButton() {
        click(arrowButton);
    }

    public void clickGoldButton() {
        click(goldButton);
    }

    public void navigateToInvestmentProducts() {
        click(investmentProductsLink);
    }

    public void enterCurrencyValue(String value) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(currencyInput));
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        input.sendKeys(value);
    }

    public boolean isGoldPageDisplayed() {
        return Objects.requireNonNull(driver.getCurrentUrl()).contains("/gold");
    }
}