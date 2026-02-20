package com.trainingCookies.demo;
 
import java.util.List;
import java.util.Map;
 
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
 
public class capabilitiesDemoTest {
    @Test
    public void modernOptions() {
        ChromeOptions options = new ChromeOptions();
        // Avoid start-maximized in CI; prefer explicit size
        options.addArguments("--window-size=1920,1080");
 
        if ("true".equalsIgnoreCase(System.getenv("HEADLESS"))) {
            options.addArguments(
                "--headless=new",
                "--no-sandbox",
                "--disable-dev-shm-usage"
            );
        }
 
        WebDriver driver = new ChromeDriver(options);
        try {
            driver.get("https://the-internet.herokuapp.com/");
 
            Capabilities caps = ((HasCapabilities) driver).getCapabilities();
            System.out.println("Browser: " + caps.getBrowserName());
            System.out.println("Browser Version: " + caps.getBrowserVersion());
            System.out.println("Platform: " + caps.getPlatformName());
 
            @SuppressWarnings("unchecked")
            Map<String, Object> chromeOpts = (Map<String, Object>) caps.getCapability("goog:chromeOptions");
            if (chromeOpts != null) {
                System.out.println("Chrome options: " + chromeOpts);
 
                @SuppressWarnings("unchecked")
                List<String> args = (List<String>) chromeOpts.get("args");
                boolean isHeadless = args != null && args.stream().anyMatch(a -> a.contains("headless"));
                System.out.println("Headless? " + isHeadless);
            }
        } finally {
            driver.quit();
        }
    }
}
