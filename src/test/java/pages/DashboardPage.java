package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;
import utils.ElementUtil;
import utils.WaitUtil;

public class DashboardPage extends BasePage {

	private WebDriver driver;
	private WaitUtil waitUtils;

	
	private String expectedUrl = "/dashboard";
	private String expectedPageHeader = "Dashboard";
	private By pageHeader = By.tagName("h6");


	public DashboardPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		waitUtils = new WaitUtil(driver);
	}

	public boolean verifyDashboardPageDisplayed() {
		
		waitUtils.waitForPageLoaded();
		Boolean isUrlCorrect = waitUtils.waitForUrlContains(expectedUrl);
		waitUtils.waitForElementPresence(pageHeader);
		waitUtils.waitForTextToBePresentInElement(driver.findElement(pageHeader), expectedPageHeader);
//		waitUtils.waitForElementVisible(pageHeader);
        Boolean isHeaderCorect = driver.findElement(pageHeader).getText().contains(expectedPageHeader);
        waitUtils.waitForPageLoaded();
		return isUrlCorrect && isHeaderCorect;
	}
}
