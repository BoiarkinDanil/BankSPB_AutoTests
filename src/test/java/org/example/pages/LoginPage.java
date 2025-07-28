package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By internetBankOption = By.xpath("//span[text()='Интернет-банк ФЛ']");
    private final By loginHeader = By.xpath("//h2[contains(., 'Вход в интернет-банк')]");
    private final By becomeClientButton = By.xpath("//a[contains(text(), 'Стать клиентом')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void selectInternetBankFL() {
        click(internetBankOption);
    }

    public String getLoginHeaderText() {
        return getText(loginHeader);
    }

    public void clickBecomeClient() {
        click(becomeClientButton);
    }
}