import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    // Các Locators (địa chỉ của các phần tử trên trang)
    private By emailInput = By.cssSelector("input[type='email']");
    private By passwordInput = By.cssSelector("input[type='password']");
    private By loginButton = By.xpath("//button[contains(text(), 'Đăng nhập')]");

    // Constructor để nhận driver từ Test class
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Các Actions (hành động trên trang)
    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    // Hành động gộp: Đăng nhập với email và password
    public void loginAs(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }
}
