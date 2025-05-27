package tests;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.qameta.allure.*;
import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import utils.helpers.CaptureHelper;
import utils.listeners.ReportListener;


public class NewTest {
	WebDriver driver;

	@Step("Open login page")
	public void openLoginPage() {
	    driver.get("https://allurereport.org/docs/install-for-windows/");
	}

	@Step("Open Google")
	public void openGoogle() throws IOException, AWTException {
	    driver.get("https://www.google.com/");
	    
	    
	    
	    assertTrue(false);
	}

	@Test(description = "Test login")
	@Description("This test attempts to log into the website using a login and a password. Fails if any error happens.\n\nNote that this test does not test 2-Factor Authentication.")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("John Doe")
    @Link(name = "Website", url = "https://dev.example.com/")
    @Issue("AUTH-123")
    @TmsLink("TMS-456")
	@Epic("Web interface")
    @Feature("Essential features")
    @Story("Authentication")
	public void login() throws IOException, AWTException {
	    driver = new ChromeDriver();
	    openLoginPage();
	    openGoogle();
	    
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, AWTException {
		
		captureScreenshot();
		if (driver != null) {
            driver.quit();
        }
    }
	
	@Attachment(value = "Screenshot after Test", type = "image/png")
    public byte[] captureScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}
