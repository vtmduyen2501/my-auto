package tests;

import static org.testng.Assert.assertTrue;

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

public class AddEmployeeTest {
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
	public void addEmployee() throws Exception {
		pimPage = dashboardPage.menu().goToPIMPage();
		//assertTrue(pimPage.isPIMPageDisplayed(), "PIM Page is not displayed");
		addEmployeePage = pimPage.clickAddButton();
		String employeeIdString = addEmployeePage.addEmployee("FirstNameDVU", "LastNameAutoTest");
		//System.out.println("CURRENT URL: " + driver.getCurrentUrl());
		//assertTrue(driver.getCurrentUrl().contains(expectedURL), "View Detail Employee is not displayed");
		employeeListPage = pimPage.clickEmployeeList();
		System.out.println("Employee Id: " + employeeIdString);
     assertTrue(employeeListPage.isEmployeeRecordPresentById(employeeIdString), "Record is not found");
//		assertTrue(employeeListPage.isEmployeeRecordPresentByName("FirstNameDVU", "LastNameAutoTest"), "Record is not found");
//		assertTrue(employeeListPage.isEmployeeRecordPresent(employeeIdString, "FirstNameDVU", "LastNameAutoTest" ), "Record not found");
		Thread.sleep(2000);
		
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
