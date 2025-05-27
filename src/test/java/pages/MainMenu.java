package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pages.pim.PIMPage;
import utils.ElementUtil;
import utils.WaitUtil;

public class MainMenu {
	
	private WebDriver driver;
	private ElementUtil elementUtils;
	private WaitUtil waitUtils;
	
	private String expectedUrl = "/pim";
	private String expectedPageHeader = "PIM";
	
	
	private By pimMenu = By.xpath("//ul[@class='oxd-main-menu']//span[normalize-space()='PIM']");
	private By pageHeader = By.tagName("h6");

	public MainMenu(WebDriver driver) {
		this.driver = driver;
		this.elementUtils = new ElementUtil(driver);
		waitUtils = new WaitUtil(driver);

	}

	public PIMPage goToPIMPage() throws Exception {
		
		elementUtils.clickElement(pimMenu);
		
		return new PIMPage(driver);
	}
	
	public Boolean isPIMPageDisplayed() {
		waitUtils.waitForPageLoaded();
		Boolean isUrlCorrect = driver.getCurrentUrl().contains(expectedUrl);
		
		waitUtils.waitForElementVisible(pageHeader);
		Boolean isHeaderCorect = driver.findElement(pageHeader).getText().contains(expectedPageHeader);
		
		return isUrlCorrect && isHeaderCorect;
	}
}
