import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirstTest {
    public static void main(String[] args) {
        // 1. Khởi tạo trình duyệt Chrome
        WebDriver driver = new ChromeDriver();

        // 2. Phóng to cửa sổ
        driver.manage().window().maximize();

        // 3. Truy cập vào Google
        driver.get("https://google.com");

        // 4. Lấy tiêu đề trang và in ra màn hình Console
        System.out.println("Tiêu đề trang là: " + driver.getTitle());

        // 5. Đợi 3 giây để nhìn kết quả
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 6. Đóng trình duyệt
        driver.quit();
    }
}