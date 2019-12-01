import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;

public class YandexTest extends WebDriverSettings{

    @Test
    public void firstTest() {

        MainPage mainPage = PageFactory.initElements(webDriver, MainPage.class);
        mainPage
                .open()
                .goToMarket();

        MarketPage marketPage = PageFactory.initElements(webDriver, MarketPage.class);
        marketPage
                .search("ноутбуки")
                .checkFillField()
                .clickSearchButton()
                .setPriceFrom("100000")
                .setPriceTo("200000")
                .setCheckBoxes(new String[] {"Apple", "ASUS", "HP", "Xiaomi"})
                .assertCheckBoxes(new String[] {"Apple", "ASUS", "HP", "Xiaomi"})
                .clickCheckBox("Процессор Core i7")
                .assertPriceTable(100000, 200000)
                .assertPriceTable(100000, 200000)
                .search("Зеленый слоник")
                .clickSearchButton()
                .clickProduct("Футболка");

        mainPage
                .open();
    }
}
