package utils;

import java.awt.AWTException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.Attachment;
import utils.helpers.CaptureHelper;

public class AllureManager {
	//Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    //HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    //Text attachments for Allure
    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
    
    public static void attachScreenshot(WebDriver driver, ITestResult result) throws IOException, AWTException {
    	// Đính kèm vào báo cáo Allure
        //Allure.addAttachment("Screenshot", new ByteArrayInputStream(CaptureHelper.returnScreenshot(driver, result)));
        
        
        try {
            // Chuyển đổi file thành byte[]
            File screenshotFile = CaptureHelper.returnScreenshot(driver, result);
            byte[] fileBytes = Files.readAllBytes(screenshotFile.toPath());
            
            // Đính kèm vào báo cáo Allure
            Allure.addAttachment("Screenshot on Failure",  new ByteArrayInputStream(fileBytes));

            System.out.println("Screenshot saved & attached: " + screenshotFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void attachScreenshot2(WebDriver driver, ITestResult result) throws IOException, AWTException {
    	// Đính kèm vào báo cáo Allure
        //Allure.addAttachment("Screenshot", new ByteArrayInputStream(CaptureHelper.returnScreenshot(driver, result)));
//        AllureLifecycle lifecycle = Allure.getLifecycle();
//        lifecycle.addAttachment(
//            "Screenshot on Failure",
//            "image/png",
//            "png",
//            new ByteArrayInputStream(CaptureHelper.returnScreenshot(driver, result))
//        );
    }
    
    public static void saveAndAttachScreenshot(File screenshotFile) {
        try {
            // Chuyển đổi file thành byte[]
            byte[] fileBytes = Files.readAllBytes(screenshotFile.toPath());
            
            // Đính kèm vào báo cáo Allure
            Allure.addAttachment("Screenshot on Failure",  new ByteArrayInputStream(fileBytes));

            System.out.println("Screenshot saved & attached: " + screenshotFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
