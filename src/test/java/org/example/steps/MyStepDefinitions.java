package org.example.steps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class MyStepDefinitions {

    private WebDriver driver;
    private WebDriverWait wait;


    private static final String BSPB_URL = "https://www.bspb.ru";



    private void initializeDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        }
    }

    @Дано("я открыл главную страницу БСПБ")
    public void я_открыл_главную_страницу_бспб() {
        initializeDriver();
        driver.get(BSPB_URL);
    }

    @Когда("я выбираю регион {string}")
    public void я_выбираю_регион(String regionName) {
        WebElement regionMenuButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.id("menu-button-:R2tad9jltmH1:")));
        regionMenuButton.click();

        WebElement kaliningradOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[starts-with(@id, 'menu-list-') and text()='Калининград']")));
        kaliningradOption.click();
    }

    @Тогда("регион должен измениться на {string}")
    public void регион_должен_измениться_на(String expectedRegion) {
        WebElement regionText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[./span/p[text()='Калининград']]")));
        assertEquals("Регион должен измениться на Калининград", "Калининград", regionText.getText());

    }
}