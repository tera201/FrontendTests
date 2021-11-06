package frontend_tests.pages;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class MarketPage {

    public WebDriver driver;
    public WebDriverWait wait;
    private final Locators locator = new Locators();

    public  MarketPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Click categories button")
    public void clickCategoriesBtn(){
        wait.until(ExpectedConditions.presenceOfElementLocated(locator.categoriesBtn));
        driver.findElement(locator.categoriesBtn).click();

    }

    private WebElement findBtn(List<WebElement> field, String name){
        WebElement targetBtn = null;
        for (WebElement webElement : field) {
            if (Objects.equals(webElement.getText(), name)) {
                targetBtn = webElement;
                break;
            }
        }
        return targetBtn;
    }
    @Step("Find subcategory button with name: {name}")
    private WebElement findSubcategoriesBtn(String name) throws Exception {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator.subcategoriesField));
        List<WebElement> subcategories = driver.findElements(locator.subcategoriesField);
        WebElement subcategory = findBtn(subcategories, name);
        if (subcategory == null) throw new Exception("Subcategory with name '" + name + "' - not found!");
        return findBtn(subcategories, name);
    }

    @Step("Find button with name: {name}")
    private WebElement findCategoriesBtn(String name) throws Exception {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator.categoriesField));
        List<WebElement> categories = driver.findElements(locator.categoriesField);
        WebElement category = findBtn(categories, name);
        if (category == null) throw new Exception("Category with name '" + name + "' - not found!");
        return category;
    }

    @Step("Choose {subCategory} in {mainCategory}")
    public void chooseSubCategoriesBtn(String mainCategory, String subCategory) throws Exception {
        Actions builder = new Actions(driver);
        WebElement categoryBtn = findCategoriesBtn(mainCategory);
        builder.moveToElement(categoryBtn).build().perform();
        WebElement subCategoryBtn = findSubcategoriesBtn(subCategory);
        builder.moveToElement(subCategoryBtn).build().perform();
        subCategoryBtn.click();
    }

    @Step("Set max price: {price}")
    public void setMaxPrice(String price) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator.maxPriceField));
        driver.findElement(locator.maxPriceField).sendKeys(price);
        waitUpdate();
    }

    @Step("Set min price: {price}")
    public void setMinPrice(String price){
        wait.until(ExpectedConditions.presenceOfElementLocated(locator.minPriceField));
        driver.findElement(locator.minPriceField).sendKeys(price);
        waitUpdate();
    }

    @Step("Wait til data is update")
    private void waitUpdate(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator.spinerLoader));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator.spinerLoader));
    }

    @Step("Choose manufacturer: {name}")
    public void chooseManufacturer(String name) throws Exception {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator.manufacturerField));
        List<WebElement> spanWithNames = driver.findElement(locator.manufacturerField).findElements(locator.manufacturerName);
        WebElement targetManufacturedCheckBox = findBtn(spanWithNames, name);
        if (targetManufacturedCheckBox == null){
            driver.findElement(locator.showMoreManufacturerBtn).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(locator.searchManufacturer));
            driver.findElement(locator.searchManufacturer).sendKeys(name);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator.manufacturerFieldAfterSearchCheck));
            spanWithNames = driver.findElement(locator.manufacturerFieldAfterSearchCheck).findElements(locator.manufacturerMoreName);
            if (spanWithNames.size() > 0) {
                targetManufacturedCheckBox = spanWithNames.get(0);
                targetManufacturedCheckBox.click();
                waitUpdate();
            }
            else throw new Exception("Manufacturer with name ' " + name + "' - not found!");
        }
        else {
            targetManufacturedCheckBox.click();
            waitUpdate();
        }

    }

    @Step("Choose 12 elements on page")
    public void chooseShownElement(){
        String param = "Показывать по 12";
        List<WebElement> shownButtonList = driver.findElements(locator.showElementBtn);
        if (shownButtonList.size() > 0) {
            List<WebElement> buttons = shownButtonList.get(0).findElements(By.tagName("button"));
            if (!Objects.equals(buttons.get(0).findElement(By.tagName("span")).findElement(By.tagName("span")).getText(), param)) {
                buttons.get(0).click();
                wait.until(ExpectedConditions.attributeContains(buttons.get(0), "aria-expanded", "true"));
                WebElement button = findBtn(buttons, param);
                button.click();
                waitUpdate();
            }
        }
    }

    @Step("Numbers of elements on page")
    public int numberProductOnPage(){
        wait.until(ExpectedConditions.presenceOfElementLocated(locator.productInfoBox));
        List<WebElement> products = driver.findElements(locator.productInfoBox);
        return products.size();
    }

    @Step("Get product with index: {i}")
    private WebElement getProductByIndex(int i){
        wait.until(ExpectedConditions.presenceOfElementLocated(locator.productInfoBox));
        List<WebElement> products = driver.findElements(locator.productInfoBox);
        return products.get(i);
    }

    @Step("Type to search line: {str}")
    private void enterTextToSearchLine(String str){
        wait.until(ExpectedConditions.presenceOfElementLocated(locator.searchLine));
        driver.findElement(locator.searchLine).sendKeys(str);
    }

    @Step("Click on search button")
    private void clickSearchBtn(){
        wait.until(ExpectedConditions.presenceOfElementLocated(locator.searchLine));
        driver.findElement(locator.searchBtn).click();
    }

    @Step("Image page that list")
    public void displayAsList(){
        wait.until(ExpectedConditions.presenceOfElementLocated(locator.showListLable));
        WebElement lable = driver.findElement(locator.showListLable);
        // check that the value needs to be changed
        if (Objects.equals(lable.findElement(By.tagName("span")).getAttribute("class"), "_1E7pr _1P_tg")) {
            lable.click();
            waitUpdate();
        }
    }
    @Step("Search in market: {str}")
    public void searchInMarket(String str){
        enterTextToSearchLine(str);
        clickSearchBtn();
    }

    @Step("Get product name")
    private String getProductName(WebElement product){
        return product.findElement(By.tagName("a")).getAttribute("title");
    }

    @Attachment
    @Step("Get product name by index on page")
    public String getProductNameByIndex(int index){
        return getProductName(getProductByIndex(index));
    }
}
