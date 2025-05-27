package utils.listeners;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import base.BaseTest;
import io.qameta.allure.Attachment;
import utils.Log;
import utils.helpers.CaptureHelper;

public class TestListener implements ITestListener {
	

	
    @Override
	public void onTestStart(ITestResult result) {
	    System.out.println("Test start: " + result.getName());
	  }
    
    @Override
	public void onTestSuccess(ITestResult result) {
    	System.out.println("TEST SUCCESS: " + result.getName());
    	
    	takeScreenshot(result);

	  }
	
    @Override
	public void onTestFailure(ITestResult result) {
    	Log.error("TEST FAILURE: " + result.getName());
    	
    	takeScreenshot(result);
    	
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
    
    @Override
	public void onStart(ITestContext context) {
    	System.out.println("------TEST CASE STARTED: " + context.getName() + "-------");
	  }
    
    @Override
	public void onFinish(ITestContext context) {
    	System.out.println("------TEST CASE ENDED: " + context.getName() + "-------");
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
