import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class FirstTest {

    private WebDriver driver;

    // Chạy TRƯỚC mỗi @Test: Dùng để setup Browser
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    // Kịch bản Test chính
    @Test
    public void testLogin() {
        driver.get("https://banve.my-board.org/auth/login.php");

        WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        emailField.sendKeys("nguyenvana@gmail.com"); // Thay bằng email của bạn

        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.sendKeys("111111"); // Thay bằng mật khẩu của bạn

        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Đăng nhập')]"));
        loginButton.click();

        // Sử dụng Assert để kiểm tra tự động xem Test Case Pass hay Fail
        // Ở đây lấy ví dụ kiểm tra URL hiện tại sau khi bấm đăng nhập không bị rỗng
        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotNull(currentUrl, "URL không được để trống!");
        
        System.out.println("Đã chạy xong Test Case đăng nhập bằng TestNG.");
    }

    // Chạy SAU mỗi @Test: Dùng để dọn dẹp, đóng Browser
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
