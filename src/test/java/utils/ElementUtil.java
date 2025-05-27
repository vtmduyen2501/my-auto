package utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementUtil {
	
	/***Các hàm cơ bản có thể viết để dùng lại:
	 Click Element
	 clickElementByJs
	 sendkeys
	 select options: by Text, value, index
	 double click
	 right click
	 move to element
	 scrollToElement (tìm và nhảy tới element đó)
	 movetoFrame
	 alert: accept, dismiss, setText
	 dialog
	 model
	 waitForPageLoaded
	 getPageTile
	 verifyPageText - kiểm tra đã tới đúng trang
	 verifyPageTitle
	 verifyPageURL
	 setDateTimeField
	 searchRowByValue
	 
	***/
	private WebDriver driver;
	private WaitUtil waitUtils;
	
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		waitUtils = new WaitUtil(driver);
	}
	
	// ==================== Basic Methods ====================
	public void clickElement(By locator) {
		waitUtils.waitForElementClickable(locator);	
		driver.findElement(locator).click();
	}
    
	public void setText(By locator, String value) {
		WebElement element = waitUtils.waitForElementVisible(locator);	
		element.clear();
		element.sendKeys(value);			
	}
	
	public String getAttribute(By locator, String value) {
		return driver.findElement(locator).getAttribute(value);
	}
	
	public String getText(By locator) {
		WebElement element = waitUtils.waitForElementPresence(locator);
		return element.getText();
	}
	
	public String getCurrentUrl() {
		waitUtils.waitForPageLoaded();
		return driver.getCurrentUrl();
	}
	
	public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
	
	public List<WebElement> findElements(By locator) {
		waitUtils.waitForPageLoaded();
		return driver.findElements(locator);
	}
	
	
	
	// ==================== Dropdown Methods ====================
}
