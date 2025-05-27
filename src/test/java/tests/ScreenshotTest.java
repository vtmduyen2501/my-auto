package tests;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import pages.DashboardPage;
import pages.LoginPage;
import pages.pim.AddEmployeePage;
import pages.pim.EmployeeListPage;
import pages.pim.PIMPage;
import utils.*;
import utils.helpers.ExcelHelper;
import utils.helpers.PropertiesFileHelper;
import utils.helpers.CaptureHelper.CaptureMode;
import utils.listeners.AllureAttachmentListener;
import utils.listeners.ReportListener;
import utils.listeners.TestListener;

public class ScreenshotTest extends BaseTest {
	
	WebDriver driver;
	LoginPage loginPage;
	DashboardPage dashboardPage;
	ExcelHelper excelUtils;
	PropertiesFileHelper propertiesFileHandler;
	PIMPage pimPage;
	AddEmployeePage addEmployeePage;
	EmployeeListPage employeeListPage;
	CaptureMode screenshotMode;
	
	@BeforeClass
	public void setup() throws Exception {
		PropertiesFileHelper.setPropertiesFilePath("src/test/resources/config.properties");
		driver = setUpDriver(PropertiesFileHelper.getProperty("browser"));
		//CaptureUtil.startRecording("ScreenshotTest");
		excelUtils = new ExcelHelper ("src/test/resources/Book2.xlsx", "Sheet1");
		//screenshotMode = CaptureUtil.getCaptureScreenshotMode();
	}
	
	@Test (priority = 10, description = "Open website and log in")
	public void login() throws Exception {
		
		Allure.step("aaaaaaaaa", () -> {
			loginPage = new LoginPage(driver);
			dashboardPage = loginPage.login(excelUtils.getCellData("username", 1), excelUtils.getCellData("password", 1));
			//dashboardPage = loginPage.login("Admin", "admin123");
			assertTrue(dashboardPage.verifyDashboardPageDisplayed(), "DashboardPage is not displayed correctly after login");
			Log.info("Login successfully");
	    });
		
//		loginPage = new LoginPage(driver);
//		dashboardPage = loginPage.login(excelUtils.getCellData("username", 1), excelUtils.getCellData("password", 1));
//		//dashboardPage = loginPage.login("Admin", "admin123");
//		assertTrue(dashboardPage.verifyDashboardPageDisplayed(), "DashboardPage is not displayed correctly after login");
//		Log.info("Login successfully");
		
		
//		PropertiesFileUtil.setProperty("pin", "1234567878");
		
//		excelUtils.setCellData("password", 10, "DVUtest");
//		excelUtils.save();
//		
	}
	
//	@Test (priority = 20)
//	@Step ("Add employee")
//	public void addEmployee() throws Exception {
//		pimPage = dashboardPage.menu().goToPIMPage();
//		//assertTrue(pimPage.isPIMPageDisplayed(), "PIM Page is not displayed");
//		addEmployeePage = pimPage.clickAddButton();
//		String employeeIdString = addEmployeePage.addEmployee("FirstNameDVU", "LastNameAutoTest");
//		//System.out.println("CURRENT URL: " + driver.getCurrentUrl());
//		//assertTrue(driver.getCurrentUrl().contains(expectedURL), "View Detail Employee is not displayed");
//		employeeListPage = pimPage.clickEmployeeList();
//		Log.info("Employee Id: " + employeeIdString);
//     assertTrue(employeeListPage.isEmployeeRecordPresentById(employeeIdString), "Record is not found");
////		assertTrue(employeeListPage.isEmployeeRecordPresentByName("FirstNameDVU", "LastNameAutoTest"), "Record is not found");
////		assertTrue(employeeListPage.isEmployeeRecordPresent(employeeIdString, "FirstNameDVU", "LastNameAutoTest" ), "Record not found");
//		
//	}
//	

	
//	@Test (priority = 30)
//	public void search() throws Exception {
//		String expectedSearchName = "Rowe";
//		pimPage = dashboardPage.menu().goToPIMPage();
//		
//		employeeListPage = pimPage.clickEmployeeList();
//		
//		employeeListPage.searchByName(expectedSearchName);
//		
//		List<String> actualFirstNames = employeeListPage.getColumnValues("first name");
//		List<String> actualLastNames = employeeListPage.getColumnValues("last name");
//	    String actualName = "";
//	    int totalFirstNameValues = actualFirstNames.size();
//	    int totalLastNameValues = actualLastNames.size();
//	    
//	    int size = Math.min(totalFirstNameValues, totalLastNameValues);
//	    
//	    if (totalFirstNameValues > 0 || totalLastNameValues > 0 ) {
//	    	for (int i = 0; i < size; i++) {
//	    	    String firstName = actualFirstNames.get(i);
//	    	    String lastName = actualLastNames.get(i);
//	    	    System.out.println("firstname: " + firstName);
//	    	    System.out.println("lastName: " + lastName);
//
//	    	    actualName = firstName + " " + lastName;
//	    	    
//	    	    System.out.println("firstname contains: " + firstName.contains(expectedSearchName.trim().toLowerCase()));
//	    	    System.out.println("lastname contains: " + lastName.contains(expectedSearchName.trim().toLowerCase()));
//	    	    
//	    	    if (firstName.contains(expectedSearchName.toLowerCase()) || lastName.contains(expectedSearchName.toLowerCase())) {
//	    	    	
//	    	        System.out.println("FOUND: " + actualName);
//	    	    } else {
//	    	    	
//	    	    	System.out.println("No columns matching expected name: " + expectedSearchName);
//				}
//	    	}
//	    	Thread.sleep(2000);
//				
//			} else {
//			System.out.println("No expected " + expectedSearchName + " columns found after search");
//		}
//		
//		
//	}
	
//	@Test (priority = 30)
//	public void search2() throws Exception {
//		String expectedSearchName = "Rowe";
//		
//		pimPage = dashboardPage.menu().goToPIMPage();
//		
//		employeeListPage = pimPage.clickEmployeeList();
//		
//		List<WebElement> rows = employeeListPage.search("Rowe");
//		
//		employeeListPage.searchByName(expectedSearchName);
//		
//		for (WebElement row : rows) {
//		    System.out.println("Row text: " + row.getText());
//		}
//	}
	
	@Test (priority = 20)
	@Step ("Failed step")
	public void thisStepIsFailed() {
		Log.info("This is a failed step");
		assertTrue(false);
	}
	
	@AfterClass
	public void tearDown() throws Exception {
		//CaptureUtil.stopRecording();
		driver.quit();
	}
    
	@AfterMethod
	public void captureScreenshot(ITestResult testResult) throws IOException, AWTException {
		
		AllureManager.attachScreenshot(driver, testResult);
		System.out.println("Screenshot saved successfully.");
	}
}
