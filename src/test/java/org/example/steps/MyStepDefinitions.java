package org.example.steps;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.example.data.Users;
import org.example.utility.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import static org.assertj.core.api.Assertions.*;
import org.assertj.core.api.SoftAssertions;


public class MyStepDefinitions {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BSPB_URL = "https://www.bspb.ru";
    private JavascriptExecutor js;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
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
            fail("Ошибка: Новая вкладка не была найдена после переключения.");
            return null;
        }
    }

    @Дано("я открыл главную страницу БСПБ")
    public void я_открыл_главную_страницу_БСПБ() {
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
        assertThat(regionText.getText()).as("Регион должен измениться на Калининград").isEqualTo("Калининград");
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

    @Когда("я кликаю по тексту {string}")
    public void я_кликаю_по_тексту(String Text) {
        DriverUtils.moveToElement(driver, By.partialLinkText(Text));
        WebElement creditsDiv = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//p[text()='на любые цели для вашего бизнеса']")));
        creditsDiv.click();
    }

    @Тогда("я должен найти офис {string}")
    public void я_должен_найти_офис(String expectedOfficeName) {
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h3[contains(text(), '" + expectedOfficeName + "')]")));
        assertThat(header.getText()).isEqualTo(expectedOfficeName).withFailMessage("Заголовок найденного офиса должен быть '" + expectedOfficeName + "'");
    }

    @Когда("я кликаю по ссылке {string}")
    public void я_кликаю_по_ссылке(String linkText) {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(
                By.partialLinkText(linkText)));
        link.click();
    }

    @Когда("я кликаю на кнопку {string}")
    public void я_кликаю_на_кнопку(String buttonText) {
        DriverUtils.moveToElement(driver, By.linkText(buttonText));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText(buttonText)));
        button.click();
    }

    @Тогда("я должен быть на странице {string}")
    public void я_должен_быть_на_странице(String expectedUrl) {
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        assertThat(driver.getCurrentUrl())
                .isEqualTo(expectedUrl)
                .withFailMessage("URL должен соответствовать ожидаемой странице");
    }

    @Когда("я кликаю на стрелку для раскрытия меню")
    public void я_кликаю_на_стрелку_для_раскрытия_меню() {
        WebElement arrowDiv = wait.until(ExpectedConditions.elementToBeClickable(By.className("t-cover__arrow-svg")));
        arrowDiv.click();
    }

    @Когда("я ввожу {string} в поле ввода валюты")
    public void я_ввожу_в_поле_ввода_валюты(String value) {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='text' and @inputmode='decimal']")));
        inputField.sendKeys(Keys.CONTROL + "a");
        inputField.sendKeys(Keys.DELETE);
        inputField.sendKeys(value);
    }

    @Когда("я кликаю на кнопку входа")
    public void я_кликаю_на_кнопку_входа() {
        WebElement LoginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("popover-trigger-:R3adt9jltmH1:")));
        LoginButton.click();
    }

    @Когда("я выбираю Интернет банк ФЛ")
    public void я_выбираю_Интернет_банк_ФЛ() {
        WebElement internetBankSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='Интернет-банк ФЛ']")));
        internetBankSpan.click();
    }

    @Когда("я переключаюсь между вкладками")
    public void я_переключаюсь_между_вкладками() {
        String mainWindowHandle = driver.getWindowHandle();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(mainWindowHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    @Тогда("я должен увидеть заголовок Вход в интернет банк")
    public void я_должен_увидеть_заголовок_Вход_в_интернет_банк() {
        WebElement header = driver.findElement(By.xpath("//h2[contains(., 'Вход в интернет-банк')]"));
        assertThat(header.getText()).isEqualTo("Вход в интернет-банк")
                .withFailMessage("Текст заголовка не совпадает");
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
        assertThat(searchInputField.getAttribute("value")).isEqualTo(expectedText);
    }

    @Тогда("Данные пользователя не совпадают")
    public void данные_пользователя_не_совпадают() {
        Users firstUser = Users.builder()
                .firstName("victor")
                .lastName("daniel")
                .email("vd@gmail.com")
                .password("qwerty123")
                .age(30)
                .gender(0)
                .active(true)
                .build();

        Users secondUser = Users.builder()
                .firstName("victor")
                .lastName("daniel")
                .email("vd@gmail.com")
                .password("qwerty123")
                .age(31)
                .gender(0)
                .active(true)
                .build();

        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(secondUser.getFirstName()).as("Проверка имени").isEqualTo(firstUser.getFirstName());
        softly.assertThat(secondUser.getLastName()).as("Проверка фамилии").isEqualTo(firstUser.getLastName());
        softly.assertThat(secondUser.getEmail()).as("Проверка email").isEqualTo(firstUser.getEmail());
        softly.assertThat(secondUser.getPassword()).as("Проверка пароля").isEqualTo(firstUser.getPassword());
        softly.assertThat(secondUser.getAge()).as("Проверка возраста").isEqualTo(firstUser.getAge());
        softly.assertThat(secondUser.getGender()).as("Проверка пола").isEqualTo(firstUser.getGender());
        softly.assertThat(secondUser.getActive()).as("Проверка активности").isEqualTo(firstUser.getActive());
        softly.assertAll();
    }

    @Тогда("Данные пользователя совпадают")
    public void данные_пользователя_совпадают() {
        Users firstUser = Users.builder()
                .firstName("victor")
                .lastName("daniel")
                .email("vd@gmail.com")
                .password("qwerty123")
                .age(30)
                .gender(0)
                .active(true)
                .build();

        Users secondUser = Users.builder()
                .firstName("victor")
                .lastName("daniel")
                .email("vd@gmail.com")
                .password("qwerty123")
                .age(30)
                .gender(0)
                .active(true)
                .build();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(secondUser.getFirstName()).as("Проверка имени").isEqualTo(firstUser.getFirstName());
        softly.assertThat(secondUser.getLastName()).as("Проверка фамилии").isEqualTo(firstUser.getLastName());
        softly.assertThat(secondUser.getEmail()).as("Проверка email").isEqualTo(firstUser.getEmail());
        softly.assertThat(secondUser.getPassword()).as("Проверка пароля").isEqualTo(firstUser.getPassword());
        softly.assertThat(secondUser.getAge()).as("Проверка возраста").isEqualTo(firstUser.getAge());
        softly.assertThat(secondUser.getGender()).as("Проверка пола").isEqualTo(firstUser.getGender());
        softly.assertThat(secondUser.getActive()).as("Проверка активности").isEqualTo(firstUser.getActive());
        softly.assertAll();
    }

}