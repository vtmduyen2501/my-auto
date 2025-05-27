package utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtil {
	 // Chờ element hiển thị (visibility)
	private WebDriverWait wait;
	private int timeoutInSeconds = 10;
	
	public WaitUtil(WebDriver driver) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
	}
    

	public WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Chờ element có thể click
    public WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Chờ element tồn tại trong DOM (không cần hiển thị)
    public WebElement waitForElementPresence(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

//    // Chờ element biến mất khỏi DOM hoặc bị ẩn đi
//    public boolean waitForElementInvisible(By locator) {
//        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
//    }

    // Chờ text trong element thay đổi
    public boolean waitForTextToChange(By locator, String oldText) {
        return wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(locator, oldText)));
    }

    // Chờ page load hoàn tất (document.readyState == 'complete')
    public void waitForPageLoaded() {
        wait.until(driver -> ((JavascriptExecutor) driver)
        		.executeScript("return document.readyState").equals("complete")
        );
        
        // Optional: wait for jQuery to be inactive (if the site uses jQuery)
        wait.until(webDriver -> (Boolean) ((JavascriptExecutor) webDriver)
            .executeScript("return typeof jQuery !== 'function' || jQuery.active === 0"));
    }
    
    public boolean waitForUrlContains(String partialUrl) {
        return wait.until(driver -> driver.getCurrentUrl().contains(partialUrl));
    }
    
 // Chờ text trong element thay đổi
    public Boolean waitForTextToBePresentInElement(WebElement element, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }
    
    public List<WebElement> waitForElementsVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    
    public Boolean isElementsInvisible(By locator) {
    	return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}
