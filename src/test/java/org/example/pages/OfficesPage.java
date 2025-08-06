package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Keys;




public class OfficesPage extends BasePage {
    private final By listViewButton = By.xpath("//span[contains(text(), 'Списком')]");


    public OfficesPage(WebDriver driver) {
        super(driver);
    }

    public void switchToListView() {
        click(listViewButton);
    }

    public void searchOffice(String address) {
        WebElement searchAddress = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[placeholder*='Поиск по адресу']")));
        searchAddress.sendKeys(address);
        searchAddress.sendKeys(Keys.ENTER);
    }

}