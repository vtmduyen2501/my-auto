package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
public class Login {
	
	WebDriver driver;
	LoginPage loginPage;
	
	@BeforeClass
	public void setup() {
		driver = new BaseTest().setUpDriver("chrome");
	}
	
	@Test
	public void login() throws Exception {
		loginPage = new LoginPage(driver);
		loginPage.login("Admin", "admin123");
		Thread.sleep(2000);
		
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
