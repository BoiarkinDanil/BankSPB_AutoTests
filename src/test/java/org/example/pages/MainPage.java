package org.example.pages;

import org.example.utility.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Map;

public class MainPage extends BasePage {
    private static final String BSPB_URL = "https://www.bspb.ru";

    private final By regionMenuButton = By.id("menu-button-:R2tad9jltmH1:");
    private final By loginButton = By.id("popover-trigger-:R3adt9jltmH1:");
    private final By searchIcon = By.xpath("//div[@class='css-35z2bt']/a[@href='/search']");
    private final By arrowButton = By.className("t-cover__arrow-svg");

    private static final Map<String, By> SECTION_LOCATORS = Map.of(
            "Офисы и банкоматы", By.partialLinkText("Офисы и банкоматы"),
            "Инвесторам", By.partialLinkText("Инвесторам"),
            "Финансовые рынки", By.partialLinkText("Финансовые рынки"),
            "ВЭД", By.partialLinkText("ВЭД"),
            "Бизнесу", By.partialLinkText("Бизнесу"),
            "Private Banking", By.partialLinkText("Private Banking")
    );

    private static final Map<String, String> SECTION_URL_FRAGMENTS = Map.of(
            "Офисы и банкоматы", "/map",
            "Инвесторам", "/investors",
            "Финансовые рынки", "/finance",
            "ВЭД", "/foreign-trade",
            "Бизнесу", "/business"
    );

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(BSPB_URL);
    }

    public void selectRegion(String regionName) {
        click(regionMenuButton);
        By regionOption = By.xpath(String.format("//button[text()='%s']", regionName));
        click(regionOption);
    }

    public String getSelectedRegion() {
        By selectedRegionLocator = By.xpath("//button[./span/p[text()='Калининград']]");
        return getText(selectedRegionLocator);
    }

    public void navigateToSection(String sectionName) {
        By locator = SECTION_LOCATORS.get(sectionName);
        if (locator == null) {
            throw new IllegalArgumentException("Раздел '" + sectionName + "' не найден");
        }
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(locator));
        link.click();

        if (SECTION_URL_FRAGMENTS.containsKey(sectionName)) {
            wait.until(ExpectedConditions.urlContains(SECTION_URL_FRAGMENTS.get(sectionName)));
        }
    }

    public void clickOnText(String text) {
        By locator = By.partialLinkText(text);
        DriverUtils.moveToElement(driver, locator);
        click(locator);
    }

    public void clickLink(String linkText) {
        By locator = By.partialLinkText(linkText);
        click(locator);
    }

    public void clickButton(String buttonText) {
        By locator = By.linkText(buttonText);
        click(locator);
    }

    public void clickArrowButton() {
        click(arrowButton);
    }

    public void enterCurrencyValue(String value) {
        By currencyInput = By.xpath("//input[@type='text' and @inputmode='decimal']");
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(currencyInput));
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        input.sendKeys(value);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public void clickSearchIcon() {
        click(searchIcon);
    }

    public void waitForUrl(String expectedUrl) {
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
    }
}