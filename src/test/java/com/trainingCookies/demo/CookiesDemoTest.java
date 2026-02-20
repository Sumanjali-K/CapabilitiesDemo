package demo;
 
import java.time.Duration;
import java.util.Set;
 
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;  // <-- add
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
 
public class CookieDemoTest {
    private WebDriver driver;
 
    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
 
        // Always set a size instead of maximize (robust for CI/headless)
        options.addArguments("--window-size=1920,1080");
 
        // Run headless on CI by setting HEADLESS=true (see workflow below)
        if ("true".equalsIgnoreCase(System.getenv("HEADLESS"))) {
            options.addArguments(
                "--headless=new",
                "--no-sandbox",
                "--disable-dev-shm-usage"
            );
        }
 
        driver = new ChromeDriver(options);
 
        // ❌ Do NOT call maximize() in CI/headless
        // driver.manage().window().maximize();
 
        // Give CI more time for slow page loads
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
    }
 
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
 
    @Test
    public void addAndDeleteCookie() {
        driver.get("https://the-internet.herokuapp.com/");
 
        Cookie mycookie = new Cookie("traineeCookie", "Bhuvana");
        driver.manage().addCookie(mycookie);
 
        Cookie fetched = driver.manage().getCookieNamed("traineeCookie");
        Assert.assertNotNull(fetched, "Cookie was not added");
        Assert.assertEquals(fetched.getValue(), "Bhuvana", "Cookie added Successfully");
        System.out.println("Added Cookie " + fetched);
 
        System.out.println("---- Cookie Details ----");
        System.out.println("Name      : " + fetched.getName());
        System.out.println("Value     : " + fetched.getValue());
        System.out.println("Domain    : " + fetched.getDomain());
        System.out.println("Path      : " + fetched.getPath());
        System.out.println("Secure    : " + fetched.isSecure());
        System.out.println("HttpOnly  : " + fetched.isHttpOnly());
        System.out.println("Expiry    : " + fetched.getExpiry());
        System.out.println("SameSite  : " + fetched.getSameSite());
 
        Set<Cookie> all = driver.manage().getCookies();
        System.out.println("Total Cookies are: " + all.size());
        for (Cookie c : all) {
            System.out.println("Cookie -> " + c.getName() + "=" + c.getValue());
        }
 
        driver.manage().deleteCookieNamed("traineeCookie");
        Cookie afterDelete = driver.manage().getCookieNamed("traineeCookie");
        Assert.assertNull(afterDelete, "Cookie was not deleted");
        System.out.println("Deleted the Cookie of trainee");
 
        driver.manage().deleteAllCookies();
        Assert.assertEquals(driver.manage().getCookies().size(), 0, "All cookies were not deleted");
        System.out.println("Deleted all the cookies");
    }
}
