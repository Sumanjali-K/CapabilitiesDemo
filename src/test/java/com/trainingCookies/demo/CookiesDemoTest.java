package com.trainingCookies.demo;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CookiesDemoTest {
	
	private WebDriver driver;
	
	@BeforeMethod
	public void setpu() {
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown() {
		if(driver !=null){
			driver.quit();
	}
	
	@Test
	public void addAndDeleteCookies_demo() {
		driver.get("https://the-internet.herokuapp.com/");
		
		Cookie mycookie=new Cookie("trainerCookie", "Mohan123");
		driver.manage().addCookie(mycookie);
		
		Cookie fetched=driver.manage().getCookieNamed("trainerCookie");
		Assert.assertNotNull(fetched,"Cookie was not added!");
		Assert.assertEquals(fetched.getValue(), "Mohan123","Cookie value mismatch!");
		
		System.out.println("Added Cookie: "+ fetched);
		
		System.out.println("Cookie name : "+ fetched.getName());
		Set<Cookie> all= driver.manage().getCookies();
		System.out.println("Total cookies now: "+all.size());
		for(Cookie c:all) {
			System.out.println("Cookie -> "+c.getName()+" = "+c.getValue());
		}
		
		driver.manage().deleteCookieNamed("trainerCookie");
		Cookie afterDelete=driver.manage().getCookieNamed("trainerCookie");
		Assert.assertNull(afterDelete,"Cookie was not Deleted!");
		
		System.out.println("Delete cookie trainerCookie");
		
		driver.manage().deleteAllCookies();
		Assert.assertEquals(driver.manage().getCookies().size(),0,"All Cookies were not deleted");
		
		System.out.println("Deleted the Cookies");
	}

}
