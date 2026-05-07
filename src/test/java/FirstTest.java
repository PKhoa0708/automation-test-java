import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
        ChromeOptions options = new ChromeOptions();
        
        // Đọc System property "headless" (được truyền vào từ Maven command line)
        String headlessMode = System.getProperty("headless", "false");
        
        if (Boolean.parseBoolean(headlessMode)) {
            System.out.println("Running Chrome in Headless mode...");
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--window-size=1920,1080");
        } else {
            System.out.println("Running Chrome in Normal mode...");
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
        if (!Boolean.parseBoolean(headlessMode)) {
            driver.manage().window().maximize();
        }
        
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