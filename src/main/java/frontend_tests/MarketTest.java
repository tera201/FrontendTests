package frontend_tests;
import frontend_tests.pages.MainPage;
import frontend_tests.pages.MarketPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.util.Objects;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.PageLoadStrategy;

@Feature("Frontend tests")
public class MarketTest {
    public static MainPage mainPage;
    public static MarketPage marketPage;
    public static WebDriver driver;

    @BeforeAll
    @Step("Setup")
    public static void setup() {
        //определение пути до драйвера и его настройка
        //String chromedriver = "/usr/bin/chromedriver";
        //System.setProperty("webdriver.chrome.driver", chromedriver);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
    }

    @BeforeEach
    @Step("BeforeTest")
    public void openYandexMarketCategories() {
        String yandexPage = "https://yandex.ru/";
        driver.get(yandexPage);
        mainPage = new MainPage(driver);
        marketPage = new MarketPage(driver);
        mainPage.openYandexMarket();
        marketPage.clickCategoriesBtn();
    }

    @Feature("FrontendTest")
    @Story("Check category {subCategory}")
    @DisplayName("market test")
    @ParameterizedTest(name="{index}: Find ''{0}'': min price {1} max price: {2} manufacturer: ''{3}''")
    @CsvSource({ "Ноутбуки, -, 30000, Lenovo", "Планшеты, 20000, 95000, Apple"})
    public void frontendTest(String subCategory, String minPrice, String maxPrice, String manufacturer) throws Exception {
        marketPage.chooseSubCategoriesBtn("Компьютеры", subCategory);
        marketPage.displayAsList();
        marketPage.chooseShownElement();
        if (!Objects.equals(minPrice, "-")) {marketPage.setMinPrice(minPrice);}
        marketPage.setMaxPrice(maxPrice);
        marketPage.chooseManufacturer(manufacturer);
        Assertions.assertEquals(12, marketPage.numberProductOnPage());
        String laptopName = marketPage.getProductNameByIndex(0);
        marketPage.searchInMarket(laptopName);
        marketPage.displayAsList();
        String foundedLaptopName = marketPage.getProductNameByIndex(0);
        Assertions.assertEquals(laptopName, foundedLaptopName);
    }

    @AfterAll
    @Step("Close")
    public static void tearDown() {
        driver.quit();
    }
}
