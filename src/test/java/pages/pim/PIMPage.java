package pages.pim;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;
import utils.ElementUtil;
import utils.WaitUtil;

public class PIMPage extends BasePage {
	
	private WebDriver driver;
	private ElementUtil elementUtils;
	private WaitUtil waitUtils;
	
	private By addBtn = By.xpath("//button[normalize-space()='Add']");
	private By employeeListBtn = By.xpath("//nav[@role='navigation']//a[normalize-space()='Employee List']");
	protected By employeeIdInput = By.xpath("//label[text()='Employee Id']/ancestor::div[contains(@class, 'oxd-input-group')]//input");

	public PIMPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		elementUtils = new ElementUtil(driver);
		waitUtils = new WaitUtil(driver);
	}
	
	public AddEmployeePage clickAddButton() {
		elementUtils.clickElement(addBtn);
		waitUtils.waitForPageLoaded();

		return new AddEmployeePage(driver);
	}
    public EmployeeListPage clickEmployeeList() {
    	elementUtils.clickElement(employeeListBtn);
    	return new EmployeeListPage(driver);
    }
	
    
	
	
}
