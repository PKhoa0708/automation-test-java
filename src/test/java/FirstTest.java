import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions; // Import ChromeOptions
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class FirstTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        // Cấu hình ChromeOptions cho chế độ headless
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Chạy Chrome ở chế độ không có giao diện người dùng
        options.addArguments("--disable-gpu"); // Tắt GPU (thường cần thiết cho headless trên Linux)
        options.addArguments("--no-sandbox"); // Tắt sandbox (thường cần thiết cho môi trường CI/CD)
        options.addArguments("--window-size=1920,1080"); // Đặt kích thước cửa sổ ảo

        driver = new ChromeDriver(options); // Truyền options vào ChromeDriver
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize(); // Lệnh này có thể không có tác dụng trong headless, nhưng không gây hại
        driver.get("https://banve.my-board.org/auth/login.php");
        
        // Khởi tạo đối tượng LoginPage
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1)
    public void testLoginWithEmptyFields() {
        loginPage.clickLogin();
        Assert.assertTrue(driver.getCurrentUrl().contains("login.php"), "Lỗi: Đã rời khỏi trang đăng nhập khi bỏ trống thông tin!");
    }

    @Test(priority = 2)
    public void testLoginWithInvalidEmailFormat() {
        loginPage.loginAs("emailkhonghople", "111111");
        Assert.assertTrue(driver.getCurrentUrl().contains("login.php"), "Lỗi: Form cho phép email sai định dạng vượt qua!");
    }

    @Test(priority = 3)
    public void testLoginWithCorrectEmailButWrongPassword() {
        loginPage.loginAs("nguyenvana@gmail.com", "matkhausai123");
        Assert.assertTrue(driver.getCurrentUrl().contains("login.php"), "Lỗi: Sai pass nhưng lại chuyển trang!");
    }

    @Test(priority = 4)
    public void testLoginWithUnregisteredEmail() {
        loginPage.loginAs("emailchuadangky_123@gmail.com", "111111");
        Assert.assertTrue(driver.getCurrentUrl().contains("login.php"), "Lỗi: Tài khoản chưa đăng ký nhưng lại cho phép chuyển trang!");
    }

    @Test(priority = 5)
    public void testLoginSuccess() {
        loginPage.loginAs("nguyenvana@gmail.com", "111111");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertFalse(currentUrl.contains("login.php"), "Lỗi: Đăng nhập thành công nhưng không thoát khỏi trang login!");
        Assert.assertNotNull(currentUrl, "URL không được để trống!");
        System.out.println("Đã chạy xong Test Case đăng nhập thành công.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}