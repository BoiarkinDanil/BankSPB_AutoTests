package org.example.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.Set;

public class MyStepDefinitions {

private WebDriver driver;
private WebDriverWait wait;
private JavascriptExecutor js;
private static final String BSPB_URL = "https://www.bspb.ru";

@Before
public void setup() {
WebDriverManager.chromedriver().setup();
// Если хотите запустить в Headless-режиме (без видимого окна браузера)
// раскомментируйте следующие 3 строки:
// ChromeOptions options = new ChromeOptions();
// options.addArguments("--headless");
// driver = new ChromeDriver(options);
driver = new ChromeDriver(); // Обычный режим
driver.manage().window().maximize();
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
wait = new WebDriverWait(driver, Duration.ofSeconds(15));
js = (JavascriptExecutor) driver;
}

@After
public void tearDown() {
if (driver != null) {
driver.quit();
driver = null;
}
}

private String switchToNewTab(Set<String> oldWindowHandles) {
wait.until(ExpectedConditions.numberOfWindowsToBe(oldWindowHandles.size() + 1));
Set<String> newWindowHandles = driver.getWindowHandles();
String newTabHandle = null;
for (String handle : newWindowHandles) {
if (!oldWindowHandles.contains(handle)) {
newTabHandle = handle;
break;
}
}
if (newTabHandle != null) {
driver.switchTo().window(newTabHandle);
return newTabHandle;
} else {
throw new AssertionError("Ошибка: Новая вкладка не была найдена после переключения.");
}
}

@Дано("я открыл главную страницу БСПБ")
public void я_открыл_главную_страницу_бспб() {
driver.get(BSPB_URL);
}

@Когда("я выбираю регион {string}")
public void я_выбираю_регион(String regionName) {
WebElement regionMenuButton = wait.until(ExpectedConditions.elementToBeClickable(
By.xpath("//button[./span/p[text()='Вне региона']]")));
regionMenuButton.click();

WebElement regionOption = wait.until(ExpectedConditions.elementToBeClickable(
By.xpath("//button[starts-with(@id, 'menu-list-') and text()='" + regionName + "']")));
regionOption.click();
}

@Тогда("регион должен измениться на {string}")
public void регион_должен_измениться_на(String expectedRegion) {
WebElement regionText = wait.until(ExpectedConditions.visibilityOfElementLocated(
By.xpath("//button[./span/p[text()='" + expectedRegion + "']]")));
Assert.assertEquals("Регион должен измениться на " + expectedRegion, expectedRegion, regionText.getText());
}

@Когда("я перехожу в раздел {string}")
public void я_перехожу_в_раздел(String sectionName) {
WebElement link = wait.until(ExpectedConditions.elementToBeClickable(
By.partialLinkText(sectionName)));
link.click();
if (sectionName.equals("Офисы и банкоматы")) {
wait.until(ExpectedConditions.urlContains("/map"));
} else if (sectionName.equals("Инвесторам")) {
wait.until(ExpectedConditions.urlContains("/investors"));
} else if (sectionName.equals("Финансовые рынки")) {
wait.until(ExpectedConditions.urlContains("/finance"));
} else if (sectionName.equals("ВЭД")) {
wait.until(ExpectedConditions.urlContains("/foreign-trade"));
} else if (sectionName.equals("Бизнесу")) {
wait.until(ExpectedConditions.urlContains("/business"));
}
}

@Когда("я выбираю отображение {string} списком")
public void я_выбираю_отображение_списком(String displayType) {
WebElement listModeButton = wait.until(ExpectedConditions.elementToBeClickable(
By.xpath("//span[contains(@class, 'radio__label') and contains(text(), 'Списком')]")));
listModeButton.click();
}

@Когда("я ищу офис по адресу {string}")
public void я_ищу_офис_по_адресу(String address) {
WebElement searchAddress = wait.until(ExpectedConditions.elementToBeClickable(
By.cssSelector("input[placeholder*='Поиск по адресу']")));
searchAddress.sendKeys(address);
searchAddress.sendKeys(Keys.ENTER);
}

@Тогда("я должен найти офис {string}")
public void я_должен_найти_офис(String expectedOfficeName) {
// Ищем по полному тексту заголовка, это более надежно
WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
By.xpath("//h3[contains(text(), '" + expectedOfficeName + "')]")));
Assert.assertEquals("Заголовок найденного офиса должен быть '" + expectedOfficeName + "'", expectedOfficeName, header.getText());
}

@Когда("я кликаю по ссылке {string}")
public void я_кликаю_по_ссылке(String linkText) {
WebElement link = wait.until(ExpectedConditions.elementToBeClickable(
By.partialLinkText(linkText)));
link.click();
}

@Тогда("я должен быть на странице {string}")
public void я_должен_быть_на_странице(String expectedUrl) {
wait.until(ExpectedConditions.urlToBe(expectedUrl));
Assert.assertEquals("URL должен соответствовать ожидаемому", expectedUrl, driver.getCurrentUrl());
}

@Когда("я кликаю на стрелку для раскрытия меню")
public void я_кликаю_на_стрелку_для_раскрытия_меню() {
WebElement arrowDiv = wait.until(ExpectedConditions.elementToBeClickable(By.className("t-cover__arrow_mobile")));
arrowDiv.click();
}

@Когда("я выбираю вкладку {string}")
public void я_выбираю_вкладку(String tabName) {
WebElement tabButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@id, 'tabs-') and contains(., '" + tabName + "')]")));
tabButton.click();
}

@Когда("я прокручиваю страницу вниз на {int} пикселей")
public void я_прокручиваю_страницу_вниз_на_пикселей(Integer pixels) {
js.executeScript("window.scrollBy(0, " + pixels + ");");
try {
// Если после прокрутки должен появиться конкретный элемент,
// лучше ожидать его появления, а не использовать Thread.sleep().
Thread.sleep(1000); // Оставлено по вашему исходному коду
} catch (InterruptedException e) {
Thread.currentThread().interrupt();
}
}

@Когда("я ввожу {string} в поле ввода валюты")
public void я_ввожу_в_поле_ввода_валюты(String value) {
WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='text' and @inputmode='decimal']")));
inputField.sendKeys(Keys.CONTROL + "a");
inputField.sendKeys(Keys.DELETE);
inputField.sendKeys(value);
}

@Тогда("поле ввода валюты должно содержать значение {string}")
public void поле_ввода_валюты_должно_содержать_значение(String expectedValue) {
WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='text' and @inputmode='decimal']")));
Assert.assertEquals("Значение в поле должно быть '" + expectedValue + "'", expectedValue, inputField.getAttribute("value"));
}

@Когда("я нажимаю на кнопку входа с id {string}")
public void я_нажимаю_на_кнопку_входа_с_id(String buttonId) {
WebElement LoginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(buttonId)));
LoginButton.click();
}

@Когда("я выбираю {string}")
public void я_выбираю(String optionText) {
Set<String> oldWindowHandles = driver.getWindowHandles();
WebElement optionSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(
By.xpath("//span[text()='" + optionText + "']")));
optionSpan.click();
switchToNewTab(oldWindowHandles);
}

@Тогда("я должен увидеть заголовок {string} в новой вкладке")
public void я_должен_увидеть_заголовок_в_новой_вкладке(String expectedHeader) {
WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(., '" + expectedHeader + "')]")));
Assert.assertEquals("Текст заголовка не совпадает", expectedHeader, header.getText());
}

@Когда("я кликаю по иконке поиска")
public void я_кликаю_по_иконке_поиска() {
WebElement searchIconLink = wait.until(ExpectedConditions.elementToBeClickable(
By.xpath("//div[@class='css-35z2bt']/a[@href='/search']")
));
searchIconLink.click();
}

@Когда("я ввожу поисковый запрос {string}")
public void я_ввожу_поисковый_запрос(String query) {
WebElement searchInputField = wait.until(ExpectedConditions.elementToBeClickable(
By.cssSelector("input[placeholder='Введите поисковый запрос']")));
searchInputField.sendKeys(query);
}

@Тогда("поле поиска должно содержать текст {string}")
public void поле_поиска_должно_содержать_текст(String expectedText) {
WebElement searchInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(
By.cssSelector("input[placeholder='Введите поисковый запрос']")));
Assert.assertEquals("Текст в поле ввода должен соответствовать введенному запросу", expectedText, searchInputField.getAttribute("value"));
}
}