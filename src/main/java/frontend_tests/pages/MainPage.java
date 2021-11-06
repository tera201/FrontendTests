package frontend_tests.pages;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class MainPage {
    public WebDriver driver;
    private final Locators locator = new Locators();

    public MainPage(WebDriver driver1) {
        this.driver = driver1;
    }

    @Step("Close yandex page and switch to market page")
    private void switchToNewPageAndCloseOld() {
        driver.close();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    @Step("Click yandex market button")
    public void clickMarketBtn() {
        driver.findElement(locator.marketBtn).click();
    }

    @Step("Open yandex market page")
    public void openYandexMarket(){
        clickMarketBtn();
        switchToNewPageAndCloseOld();
    }

}

