package pages.pim;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.ElementUtil;
import utils.WaitUtil;

public class AddEmployeePage extends PIMPage {

	private ElementUtil elementUtils;
	private WaitUtil waitUtils;
	
	
	private By firstName = By.name("firstName");
	private By lastName = By.name("lastName");
	private By saveBtn = By.cssSelector("button[type='submit']");
	private String expectedURL = "/viewPersonalDetails";

	public AddEmployeePage(WebDriver driver) {
		super(driver);
		elementUtils = new ElementUtil(driver);
		waitUtils = new WaitUtil(driver);
	}
	
	public String getEmployeeId() {
		return elementUtils.getAttribute(employeeIdInput, "value");
	}
	public void populateFirstName(String value) {
		elementUtils.setText(firstName, value);
	}
	
	public void populateLastName(String value) {
		elementUtils.setText(lastName, value);
	}
	
	public void clickSaveButton() {
		elementUtils.clickElement(saveBtn);
	}
	
	public String addEmployee(String firstName, String lastName) throws Exception {
		waitUtils.waitForPageLoaded();
		populateFirstName(firstName);
		populateLastName(lastName);
		String employeeIdString = getEmployeeId();
		clickSaveButton();
		waitUtils.waitForUrlContains(expectedURL);
		System.out.println("Save Button clicked");

		return employeeIdString;
	}
}
