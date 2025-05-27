package tests;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.DashboardPage;
import pages.LoginPage;
import pages.pim.AddEmployeePage;
import pages.pim.EmployeeListPage;
import pages.pim.PIMPage;

public class VerifyRecordsInTable {
	WebDriver driver;
	LoginPage loginPage;
	DashboardPage dashboardPage;
	PIMPage pimPage;
	AddEmployeePage addEmployeePage;
	EmployeeListPage employeeListPage;
	//MainMenu mainMenu;
	
	
	@BeforeClass
	public void setup() {
		driver = new BaseTest().setUpDriver("chrome");
	}

	@Test (priority = 1)
	public void login() throws Exception {
		loginPage = new LoginPage(driver);
		dashboardPage = loginPage.login("Admin", "admin123");
		assertTrue(dashboardPage.verifyDashboardPageDisplayed(), "DashboardPage is not displayed correctly after login");
		System.out.println("Login successfully");
		
	}
	
	@Test (priority = 2)
	public void verifyRecordsMatchingValue() throws Exception {
		String expectedSearchName = "Admin";
		pimPage = dashboardPage.menu().goToPIMPage();
		
		employeeListPage = pimPage.clickEmployeeList();
		
		employeeListPage.searchByName(expectedSearchName);
		
		List<String> actualFirstNames = employeeListPage.getColumnValues("first name");
		for (String firstname : actualFirstNames) {
			assertTrue(firstname.contains(expectedSearchName), "Expected First Name '" + expectedSearchName + "' not found in: " + firstname);
			}
		
		Thread.sleep(2000);
		
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
