package frontend_tests.pages;

import org.openqa.selenium.By;

public class Locators {
    public By marketBtn = By.cssSelector("[data-id='market']");

    public By categoriesBtn = By.cssSelector("[data-tid='89fe4462']");
    public By categoriesField = By.className("_1UCDW");
    public By subcategoriesField = By.cssSelector("[data-tid='de537da7 b8bff0b6 dd5c707d']");

    public By maxPriceField = By.id("glpriceto");
    public By minPriceField = By.id("glpricefrom");

    public By manufacturerField = By.xpath("//*[@id=\"search-prepack\"]/div/div/div/div/div[2]/div[3]/div/div/fieldset");
    public By manufacturerName = By.cssSelector("span[class='_1o8_r xUzR2'");
    public By showMoreManufacturerBtn = By.xpath("//*[@id=\"search-prepack\"]/div/div/div/div/div[2]/div[3]/div/div/fieldset/footer/button");
    public By searchManufacturer = By.id("7893318-suggeter");
    public By manufacturerFieldAfterSearchCheck = By.xpath("//*[@id=\"search-prepack\"]/div/div/div/div/div[2]/div[3]/div/div/fieldset/ul");
    public By manufacturerMoreName = By.cssSelector("span[class='_1o8_r xUzR2 _17C4L'");

    public By showElementBtn = By.cssSelector(("div[class='_3JNss _1BSH6 v3cFc']"));
    public By spinerLoader = By.cssSelector(("div[class='_2Lvbi _1oZmP']"));
    public By productInfoBox = By.cssSelector(("div[class='_37suf']"));

    public By searchLine = By.id("header-search");
    public By searchBtn = By.cssSelector(("button[data-tid='8e34e3c2 8166f242']"));

    public By showListLable = By.cssSelector(("label[class='_1AaH5']"));
}