package org.example.steps;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.example.PojoModels.ExchangeOfficeModel;
import org.example.PojoModels.OfficeDataModel;
import org.example.data.Users;
import org.example.requests.GetExchangeOfficesRequest;
import org.example.utility.JsonParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import org.example.PojoModels.ExchangeOfficeModel;
import org.example.PojoModels.Rate;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

import org.example.pages.MainPage;
import org.example.pages.OfficesPage;
import org.example.pages.LoginPage;
import org.example.pages.SearchPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class MyStepDefinitions {
    private WebDriver driver;
    private MainPage mainPage;
    private OfficesPage officesPage;
    private LoginPage loginPage;
    private SearchPage searchPage;
    private WebDriverWait wait;

    @Before("@web")
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        mainPage = new MainPage(driver);
        officesPage = new OfficesPage(driver);
        loginPage = new LoginPage(driver);
        searchPage = new SearchPage(driver);
    }

    @After("@web")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Дано("я открыл главную страницу БСПБ")
    public void openMainPage() {
        mainPage.open();
    }

    @Когда("я выбираю регион {string}")
    public void selectRegion(String regionName) {
        mainPage.selectRegion(regionName);
    }

    @Тогда("регион должен измениться на {string}")
    public void verifyRegionChanged(String expectedRegion) {
        assertThat(mainPage.getSelectedRegion()).isEqualTo(expectedRegion);
    }

    @Когда("я перехожу в раздел {string}")
    public void я_перехожу_в_раздел(String sectionName) {
        mainPage.navigateToSection(sectionName);
    }

    @Когда("я выбираю отображение {string} списком")
    public void switchToListView(String displayType) {
        officesPage.switchToListView();
    }

    @Когда("я ищу офис по адресу {string}")
    public void searchOffice(String address) {
        officesPage.searchOffice(address);
    }

    @Когда("я кликаю по тексту {string}")
    public void clickOnText(String text) {
        mainPage.clickOnText(text);
    }

    @Тогда("я должен найти офис {string}")
    public void я_должен_найти_офис(String expectedOfficeName) {
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h3[contains(text(), '" + expectedOfficeName + "')]")));
        assertThat(header.getText()).isEqualTo(expectedOfficeName)
                .withFailMessage("Заголовок найденного офиса должен быть '" + expectedOfficeName + "'");
    }

    @Когда("я кликаю по ссылке {string}")
    public void clickLink(String linkText) {
        mainPage.clickLink(linkText);
    }

    @Когда("я кликаю на кнопку {string}")
    public void clickButton(String buttonText) {
        mainPage.clickButton(buttonText);
    }

    @Тогда("я должен быть на странице {string}")
    public void я_должен_быть_на_странице(String expectedUrl) {
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        assertThat(driver.getCurrentUrl())
                .isEqualTo(expectedUrl)
                .withFailMessage("URL должен соответствовать ожидаемой странице");
    }

    @Когда("я кликаю на стрелку для раскрытия меню")
    public void clickArrowButton() {
        mainPage.clickArrowButton();
    }

    @Когда("я ввожу {string} в поле ввода валюты")
    public void enterCurrencyValue(String value) {
        mainPage.enterCurrencyValue(value);
    }

    @Когда("я кликаю на кнопку входа")
    public void clickLoginButton() {
        mainPage.clickLoginButton();
    }

    @Когда("я выбираю Интернет банк ФЛ")
    public void selectInternetBankFL() {
        loginPage.selectInternetBankFL();
    }

    @Когда("я переключаюсь между вкладками")
    public void switchBetweenTabs() {
        mainPage.switchToNewTab();
    }

    @Тогда("я должен увидеть заголовок Вход в интернет банк")
    public void verifyLoginHeader() {
        assertThat(loginPage.getLoginHeaderText()).isEqualTo("Вход в интернет-банк");
    }

    @Когда("я кликаю по иконке поиска")
    public void clickSearchIcon() {
        mainPage.clickSearchIcon();
    }

    @Когда("я ввожу поисковый запрос {string}")
    public void enterSearchQuery(String query) {
        searchPage.enterSearchQuery(query);
    }

    @Тогда("поле поиска должно содержать текст {string}")
    public void verifySearchFieldText(String expectedText) {
        assertThat(searchPage.getSearchText()).isEqualTo(expectedText);
    }

    @Тогда("Данные пользователя не совпадают")
    public void verifyUserDataNotMatch() {
        Users firstUser = Users.builder().firstName("victor").lastName("daniel").email("vd@gmail.com")
                .password("qwerty123").age(30).gender(0).active(true).build();
        Users secondUser = Users.builder().firstName("victor").lastName("daniel").email("vd@gmail.com")
                .password("qwerty123").age(31).gender(0).active(true).build();
        assertThat(firstUser).usingRecursiveComparison().isEqualTo(secondUser);
    }

    @Тогда("Данные пользователя совпадают")
    public void verifyUserDataMatch() {
        Users firstUser = Users.builder().firstName("victor").lastName("daniel").email("vd@gmail.com")
                .password("qwerty123").age(30).gender(0).active(true).build();
        Users secondUser = Users.builder().firstName("victor").lastName("daniel").email("vd@gmail.com")
                .password("qwerty123").age(30).gender(0).active(true).build();
        assertThat(firstUser).usingRecursiveComparison().isEqualTo(secondUser);
    }

    @Тогда("Коллекция по запросу соответствует требованиям")
    public void ChekApi() {
        Response response = GetExchangeOfficesRequest.performGet();
        OfficeDataModel actualOfficeDataModel = response.body().as(OfficeDataModel.class);
        OfficeDataModel expectedOfficeDataModel = JsonParser.readJson("src/test/resources/Expected/Offices.json", OfficeDataModel.class);
        assertThat(actualOfficeDataModel)
                .as("Проверка полного соответствия данных об обменных пунктах")
                .usingRecursiveComparison()
                .withFailMessage("Данные об обменных пунктах не соответствуют ожидаемым. Проверьте актуальность тестовых данных.")
                .isEqualTo(expectedOfficeDataModel);
    }

    @Тогда("Курсы валют соответствуют требованиям")
    public void checkCurrencyRates() {
        Response response = GetExchangeOfficesRequest.performGet();
        OfficeDataModel actualData = response.body().as(OfficeDataModel.class);
        OfficeDataModel expectedData = JsonParser.readJson("src/test/resources/Expected/Offices.json", OfficeDataModel.class);

        assertThat(actualData.getItems())
                .usingRecursiveComparison()
                .ignoringFields("items.address", "items.id", "items.name")
                .ignoringFields("items.rates.cbRate", "items.rates.currencyCodeSecond",
                        "items.rates.lotSize", "items.rates.transactionVolume")
                .isEqualTo(expectedData.getItems());
    }

}