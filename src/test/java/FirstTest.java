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

    // Chạy TRƯỚC mỗi @Test: Mở browser và vào luôn trang Login để dùng chung
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://banve.my-board.org/auth/login.php");
    }

    // --- CÁC CASE THẤT BẠI (NEGATIVE CASES) ---

    @Test(priority = 1)
    public void testLoginWithEmptyFields() {
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Đăng nhập')]"));
        loginButton.click();

        // Kiểm tra xem vẫn còn ở trang login hay không
        Assert.assertTrue(driver.getCurrentUrl().contains("login.php"), "Lỗi: Đã rời khỏi trang đăng nhập khi bỏ trống thông tin!");
    }

    @Test(priority = 2)
    public void testLoginWithInvalidEmailFormat() {
        WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        emailField.sendKeys("emailkhonghople");
        
        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.sendKeys("111111");

        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Đăng nhập')]"));
        loginButton.click();

        // Kiểm tra xem có thông báo lỗi từ HTML5 validation không (nếu có validation bằng type='email')
        // Hoặc kiểm tra xem vẫn bị giữ lại trang login
        Assert.assertTrue(driver.getCurrentUrl().contains("login.php"), "Lỗi: Form cho phép email sai định dạng vượt qua!");
    }

    @Test(priority = 3)
    public void testLoginWithCorrectEmailButWrongPassword() {
        WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        emailField.sendKeys("nguyenvana@gmail.com"); 

        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.sendKeys("matkhausai123"); 

        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Đăng nhập')]"));
        loginButton.click();

        // Thông thường khi login sai sẽ có thông báo lỗi hiển thị, ở đây ta kiểm tra URL tạm
        Assert.assertTrue(driver.getCurrentUrl().contains("login.php"), "Lỗi: Sai pass nhưng lại chuyển trang!");
    }

    @Test(priority = 4)
    public void testLoginWithUnregisteredEmail() {
        WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        emailField.sendKeys("emailchuadangky_123@gmail.com"); 

        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.sendKeys("111111"); 

        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Đăng nhập')]"));
        loginButton.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("login.php"), "Lỗi: Tài khoản chưa đăng ký nhưng lại cho phép chuyển trang!");
    }

    // --- CASE THÀNH CÔNG (POSITIVE CASE) ---

    // Đặt priority cao nhất (hoặc không set nếu theo thứ tự alphabet/chuẩn ngẫu nhiên) 
    // Trong ví dụ này, để chạy độc lập thì thứ tự không ảnh hưởng vì ta dùng @BeforeMethod.
    @Test(priority = 5)
    public void testLoginSuccess() {
        WebElement emailField = driver.findElement(By.cssSelector("input[type='email']"));
        emailField.sendKeys("nguyenvana@gmail.com"); 

        WebElement passwordField = driver.findElement(By.cssSelector("input[type='password']"));
        passwordField.sendKeys("111111"); 

        WebElement loginButton = driver.findElement(By.xpath("//button[contains(text(), 'Đăng nhập')]"));
        loginButton.click();

        // Kiểm tra URL hiện tại sau khi bấm đăng nhập
        String currentUrl = driver.getCurrentUrl();
        Assert.assertFalse(currentUrl.contains("login.php"), "Lỗi: Đăng nhập thành công nhưng không thoát khỏi trang login!");
        Assert.assertNotNull(currentUrl, "URL không được để trống!");
        
        System.out.println("Đã chạy xong Test Case đăng nhập thành công.");
    }

    // Chạy SAU mỗi @Test: Dùng để dọn dẹp, đóng Browser
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
