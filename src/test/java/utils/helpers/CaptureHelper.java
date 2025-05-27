package utils.helpers;
import org.testng.ITestResult;

import utils.WaitUtil;

import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.monte.media.math.Rational;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.Registry;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class CaptureHelper extends ScreenRecorder {


	public enum CaptureMode {
		FAILURE,
		SUCCESS,
		ALL
	}
	public static ScreenRecorder screenRecorder;

	private static String dateFormat = "yyyy-MM-dd HH.mm.ss";
	
	
	private final String videoName;


    public CaptureHelper(GraphicsConfiguration cfg, Rectangle captureArea,
            Format fileFormat, Format screenFormat,
            Format mouseFormat, Format audioFormat,
            File movieFolder, String videoName) throws IOException, AWTException {
super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
this.videoName = videoName;
    }
	
	

	@Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }
        SimpleDateFormat timeStamp = new SimpleDateFormat(dateFormat);
        return new File(movieFolder,
                videoName + "-" + timeStamp.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
    }


    public static void startRecording(String videoFileName) throws Exception {
    	//videoFileName.get
        File file = new File("recordings");
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;
		
		Rectangle captureSize = new Rectangle(0, 0, width, height);

		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		screenRecorder = new CaptureHelper(gc, captureSize,
				new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_MJPG,
						CompressorNameKey, ENCODING_AVI_MJPG, DepthKey, 24, FrameRateKey,
						Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
				null, file, videoFileName);

        screenRecorder.start();
    			
        System.out.println("Recording started...");
    }

    public static void stopRecording() throws Exception {
        if (screenRecorder != null) {
            screenRecorder.stop();
            System.out.println("Stopped video.");
        }
    }
	
	

	public static void captureFullScreenshot(WebDriver driver, ITestResult testResult) throws IOException, AWTException {
		
		CaptureMode screenshotMode = getCaptureScreenshotMode();
		
		
		// Check if screenshot should be taken based on result
		if (isScreenshotTaken(testResult)) {
		
	        
	        File destination = getDestinationFolderFile(driver, testResult, screenshotMode);

	        // Get window position and size
	        Point position = driver.manage().window().getPosition();
	        Dimension size = driver.manage().window().getSize();
	        Rectangle captureRect = new Rectangle(position.getX(), position.getY(), size.getWidth(), size.getHeight());

	        // Capture screenshot using Robot of Java
	        Robot robot = new Robot();
	        BufferedImage image = robot.createScreenCapture(captureRect);

	        // Write to file
	        ImageIO.write(image, "png", destination);
		}	

	}
	
public static File returnScreenshot(WebDriver driver, ITestResult testResult) throws IOException, AWTException {
		
	try {
CaptureMode screenshotMode = getCaptureScreenshotMode();

File destination = getDestinationFolderFile(driver, testResult, screenshotMode);
		// Check if screenshot should be taken based on result
		if (isScreenshotTaken(testResult)) {
		
	        
			

	        // Get window position and size
	        Point position = driver.manage().window().getPosition();
	        Dimension size = driver.manage().window().getSize();
	        Rectangle captureRect = new Rectangle(position.getX(), position.getY(), size.getWidth(), size.getHeight());

	        // Capture screenshot using Robot of Java
	        Robot robot = new Robot();
	        BufferedImage image = robot.createScreenCapture(captureRect);
	        // Write to file
	        ImageIO.write(image, "png", destination);

		}	
     
		return destination;
        
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}

	
	public static void captureViewportScreenshot(WebDriver driver, ITestResult testResult) {
		
		
		CaptureMode screenshotMode = getCaptureScreenshotMode();
		// Check if screenshot should be taken based on result
		if (isScreenshotTaken(testResult)) {
			
			File destination = getDestinationFolderFile(driver, testResult, screenshotMode);
			
			// Capture screenshot using TakesScreenshot of Selenium
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			

			try {
				Files.copy(screenshot.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
				System.out.println("✅ Screenshot saved: " + destination.getAbsolutePath());
			} catch (IOException e) {
				System.err.println("❌ Failed to save screenshot: " + e.getMessage());
			}
			
		}
		}

	
    public static CaptureMode getCaptureScreenshotMode() {
        String mode = PropertiesFileHelper.getProperty("captureScreenshotMode");
        try {
            return CaptureMode.valueOf(mode.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            System.err.println("⚠️ Invalid screenshot mode in config. Defaulting to ON_FAILURE.");
            return CaptureMode.FAILURE;
        }
    }
    
    public static File getDestinationFolderFile(WebDriver driver, ITestResult testResult, CaptureMode screenshotMode  ) {
  
    	// Build timestamp and folder path
    	String  timestamp = (new SimpleDateFormat(dateFormat)).format(new Date());
		String folderPath = "screenshots/" + screenshotMode.name().toLowerCase();
		String fileName = testResult.getMethod().getMethodName() + "_" + timestamp + ".png";
			
			//Wait for page loaded
		    (new WaitUtil(driver)).waitForPageLoaded();
		    
			// Create folder if it doesn't exist
			File folder = new File(folderPath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			
	     // Full path to file
       return new File(folderPath + File.separator + fileName);
    }
    
public static Boolean isScreenshotTaken(ITestResult testResult) {
    	
		CaptureMode screenshotMode = getCaptureScreenshotMode();
		boolean isPassed = testResult.getStatus() == ITestResult.SUCCESS;
		boolean isFailed = testResult.getStatus() == ITestResult.FAILURE;

		// Check if screenshot should be taken based on result
		if ((screenshotMode == CaptureMode.ALL) || (screenshotMode == CaptureMode.FAILURE && isFailed)
				|| (screenshotMode == CaptureMode.SUCCESS && isPassed)) {
			return true;
		} else return false;
    
}
}
