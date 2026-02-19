package com.trainingCookies.demo;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class capabilitiesDemoTest {
	private WebDriver driver;

	

	@Test
	public void localCapabilities_demo() {
		ChromeOptions options=new ChromeOptions();
		options.addArguments("---start-maximized");
		//options.setAcceptInsecureCerts(true);
		
		WebDriver driver= new ChromeDriver(options);
		try {
			driver.get("https://the-internet.herokuapp.com/");
			
			Capabilities caps=((HasCapabilities) driver).getCapabilities();
			System.out.println("Browser: "+caps.getBrowserName());
			System.out.println("Browser version : "+caps.getBrowserVersion());
			System.out.println("Platform: "+caps.getPlatformName());
			
			Map<String, Object> chromeOpts = (Map<String, Object>) caps.getCapability("goog:chromeOptions");
			if (chromeOpts != null) {
			    System.out.println("Chrome options: " + chromeOpts);
			    // Example: detect headless
			    List<String> args = (List<String>) chromeOpts.get("args");
			    boolean isHeadless = args != null && args.stream().anyMatch(a -> a.contains("headless"));
			    System.out.println("Headless? " + isHeadless);
			}
		} finally {
			driver.quit();
		}
	}

}
