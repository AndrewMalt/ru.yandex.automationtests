import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MarketPage {
    private WebDriver webDriver;
    private WebDriverWait wait;

    public MarketPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        wait = new WebDriverWait(webDriver, 30, 500);
    }

    @FindBy(xpath = "//input[@id='header-search']")
    private WebElement search;

    @FindBy(xpath = "//span[.='Найти']")
    private WebElement searchButton;

    @FindBy(xpath = "//input[@id='glpricefrom']")
    private WebElement priceFrom;

    @FindBy(xpath = "//input[@id='glpriceto']")
    private WebElement priceTo;

    @FindBy(xpath = "//fieldset[@data-autotest-id='7893318']")
    private WebElement checkBoxesList;

    private By currentPriceTable = By.xpath("//div[contains(@class,'n-snippet-list')]//a[contains(@href,'pricefrom=100000')]");

    @FindBy(xpath = "//div[contains(@class,'n-snippet-list')]")
    private WebElement priceTable;

    By searchField = By.xpath("//span[contains(@class,'suggest2-autocomplete__entered')]");
    By product = By.xpath("//div[@class='price']");

    MarketPage search(String searchName) {
        search.clear();
        search.sendKeys(searchName);
        return this;
    }

    MarketPage checkFillField(){
        Assertions.assertEquals(webDriver
                .findElement(searchField)
                .getAttribute("innerHTML"), "ноутбуки", "Упс, что-то пошло не так");
        return this;
    }

    MarketPage clickSearchButton() {
        searchButton.click();
        return this;
    }

    MarketPage setPriceFrom(String price) {
        priceFrom.clear();
        priceFrom.sendKeys(price);
        return this;
    }

    MarketPage setPriceTo(String price) {
        priceTo.clear();
        priceTo.sendKeys(price);
        return this;
    }

    MarketPage setCheckBoxes(String[] brands) {
        for (String brand : brands) {
            String path = "//input[@name='Производитель " + brand + "']";
            String id = webDriver.findElement(By.xpath(path)).getAttribute("id");
            path = "//label[@for='" + id + "']";
            webDriver.findElement(By.xpath(path)).click();
        }
        return this;
    }
    MarketPage assertCheckBoxes(String[] brands) {
        List<WebElement> elems = checkBoxesList.findElements(By.tagName("span"));
        for (WebElement elem : elems) {
            String brandName = elem.getText();
            String path = "//input[@name='Производитель " + brandName + "']";
            for (int j = 0; j < brands.length; j++) {
                if (brandName.equals(brands[j])) {
                    Assertions.assertTrue(webDriver
                                    .findElement(By.xpath(path))
                                    .isSelected()
                            , "Упс, что-то пошло не так"); break;
                } else {
                    if (brands.length == j + 1) {
                        Assertions.assertFalse(webDriver
                                .findElement(By.xpath(path))
                                .isSelected()
                                , "Упс, что-то пошло не так");
                    }
                }
            }
        }
        return this;
    }
    MarketPage clickCheckBox(String boxName) {
        String path = "//input[@name='" + boxName + "']";
        String id = webDriver.findElement(By.xpath(path)).getAttribute("id");
        path = "//label[@for='" + id + "']";
        webDriver.findElement(By.xpath(path)).click();
        return this;
    }

    MarketPage assertPriceTable(int priceFrom, int priceTo) {
        wait.until(ExpectedConditions.presenceOfElementLocated(currentPriceTable));
        int lengthList = priceTable.findElements(product).size();
        for (int i = 0; i < lengthList; i++) {
            String currentPriceString = priceTable
                    .findElements(product)
                    .get(i)
                    .getText();
            int currentPriceInt = Integer.parseInt(currentPriceString.replaceAll("\\D+", ""));
            Assertions.assertTrue(
                    currentPriceInt >= priceFrom && currentPriceInt <= priceTo
                    , "Упс, что-то пошло не так");
        }
        return this;
    }

    MarketPage clickProduct(String productName) {
        String path = "//a[contains(@title,'" + productName + "')]";
        webDriver.findElement(By.xpath(path)).click();
        return this;
    }


}
