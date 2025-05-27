package utils.listeners;

import java.awt.AWTException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import utils.AllureManager;
import utils.Log;
import utils.helpers.CaptureHelper;

public class ReportListener implements ITestListener {
	
	@Override
	public void onStart(ITestContext context) {
    	Log.info("[TEST] ========= SUITE STARTED =========");
    	Log.info("[INFO] Suite Name : " + context.getName());
    	Log.info("[INFO] Start Time : " + new Date(context.getStartDate().getTime()));
	  }
    
    @Override
	public void onFinish(ITestContext context) {
    	Log.info("[TEST] ========= SUITE FINISHED =========");
    	Log.info("[INFO] End Time : " + new Date(context.getEndDate().getTime()));
    	Log.info("[INFO] Total Passed : " + context.getPassedTests().size());
    	Log.info("[INFO] Total Failed : " + context.getFailedTests().size());
    	Log.info("[INFO] Total Skipped : " + context.getSkippedTests().size());

	  }

    @Override
	public void onTestStart(ITestResult result) {
    	String testName = result.getMethod().getMethodName();
    	Log.info("[TEST] ---> STARTING TEST: " + testName);
    	Allure.addAttachment("🔹 Test started: " + testName, "TestPassed");
	  }
    
    @Override
	public void onTestSuccess(ITestResult result) {
    	String testName = result.getMethod().getMethodName();
    	 String message = result.getThrowable() != null ? result.getThrowable().getMessage() : "Unknown error";

    	 Allure.addAttachment("❌ Test Failed - " + testName, message);
    	 
    	//takeScreenshot(result);

	  }
	
    @Override
	public void onTestFailure(ITestResult result) {
    	Log.error("TEST FAILURE: " + result.getName());
    	// Đường dẫn đến hình ảnh cần đính kèm
        File file = new File("/myauto/screenshots/all/thisStepIsFailed_2025-05-24 22.09.39.png");
        FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(file);
			// Đính kèm hình ảnh vào báo cáo Allure
	        Allure.addAttachment("Screenshot", fileInputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        
    	
    	//takeScreenshot(result);
    	
	  }
    
    @Override
	public void onTestSkipped(ITestResult result) {
    	System.out.println("TEST SKIPPED: " + result.getName());
    	
    	takeScreenshot(result);
    	
	  }
    
    @Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	  }
    
    @Override
	public void onTestFailedWithTimeout(ITestResult result) {
	    // not implemented
	  }
    
    @Attachment(value = "Screenshot after Test", type = "image/png")
    public byte[] captureScreenshot(ITestResult result) {
    	Object testInstance = result.getInstance();
    	WebDriver driver = ((BaseTest) testInstance).getDriver();
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
    
    private void takeScreenshot(ITestResult result) {
    	Object testInstance = result.getInstance();
    	WebDriver driver = ((BaseTest) testInstance).getDriver();
    	try {
    		if (testInstance instanceof BaseTest) {
    			
    			CaptureHelper.captureFullScreenshot(driver, result);
    			System.out.println("Screenshot saved successfully.");
    		}
			
		} catch (IOException | AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
