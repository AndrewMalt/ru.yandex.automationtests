import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class MainPage {
    public WebDriver webDriver;

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @FindBy(xpath = "//a[@data-id='market']")
    private WebElement market;

    MainPage open() {
        webDriver.get("https://yandex.ru");
        return this;
    }

    MainPage goToMarket() {
        market.click();
        return this;
    }

}


