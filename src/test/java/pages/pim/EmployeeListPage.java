package pages.pim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.ElementUtil;
import utils.WaitUtil;

public class EmployeeListPage extends PIMPage {
	private ElementUtil elementUtils;
	private WaitUtil waitUtils;

	private By employeeNameInput = By
			.xpath("//label[text()='Employee Name']/ancestor::div[contains(@class, 'oxd-input-group')]//input");
	private By searchBtn = By.cssSelector("button[type='submit']");
	private By tableRows = By.xpath("//div[@class='oxd-table-body']//div[@role='row']");
	private By tableHeader = By.xpath("//div[@class='oxd-table-header']");

	public EmployeeListPage(WebDriver driver) {
		super(driver);
		elementUtils = new ElementUtil(driver);
		waitUtils = new WaitUtil(driver);
	}

	public By getRowByTexts(String... values) {
		StringBuilder xpathBuilder = new StringBuilder("(//div[@role='row'])[2]");
		for (String val : values) {
			xpathBuilder.append("[contains(., '").append(val).append("')]");
		}
		return By.xpath(xpathBuilder.toString());
	}

	public void populateField(By locator, String value) {
		elementUtils.setText(locator, value);
	}

	public void clickSearchButton() {
		// Kiểm tra phần tử đã được hiển thị và có thể nhấn

		WebElement element = waitUtils.waitForElementPresence(searchBtn);

		if (element.isDisplayed() && element.isEnabled()) {
			elementUtils.clickElement(searchBtn);
		} else {
			System.out.println("Search button is not ready.");
		}

	}

	public void searchByName(String name) {
		populateField(employeeNameInput, name);
		clickSearchButton();
		waitUtils.waitForPageLoaded();
		elementUtils.scrollToElement(waitUtils.waitForElementPresence(tableHeader));

	}

	public int getColumnIndexByName(String columnName) {
		switch (columnName.toLowerCase()) {
		case "id":
			return 1;
		case "first name":
			return 2;
		case "middle name":
			return 2;
		case "last name":
			return 3;
		default:
			throw new IllegalArgumentException("Unknown column: " + columnName);
		}
	}

	public List<String> getColumnValues(String columnName) {

		int columnIndex = getColumnIndexByName(columnName);
		List<String> columnValues = new ArrayList<>();
		waitUtils.waitForPageLoaded();
		try {
				
				List<WebElement> rows = waitUtils.waitForElementsVisible(tableRows);
				
				elementUtils.scrollToElement(waitUtils.waitForElementPresence(tableHeader));

				System.out.println("Total " + columnName + " columns returned: " + rows.size());
				
					for (WebElement row : rows) {
						
						try {
							List<WebElement> columns = row.findElements(By.xpath("./div"));
							if (columns.size() > columnIndex) {
								columnValues.add(columns.get(columnIndex).getText().trim().toLowerCase());
							}
					    } catch (StaleElementReferenceException e) {
					        // Reload lại danh sách nếu DOM thay đổi
					    	rows = waitUtils.waitForElementsVisible(tableRows);
					    }
						
						
						
						
					}
					

					return columnValues;			
			
		} catch (TimeoutException e) {
		    System.out.println("No columns found after waiting.");
		    return Collections.emptyList();
		}

	}

	public Boolean isRecordMatchingByTexts(String... values) {

		By xpathBuilderBy = getRowByTexts(values);

		elementUtils.scrollToElement(waitUtils.waitForElementPresence(xpathBuilderBy));
		waitUtils.waitForElementVisible(xpathBuilderBy);
		return true;

	}

	public Boolean verifyTotalRecordFoundMessage(String expectedRecordTotal) {

		String messageHeaderXPath = "//span[normalize-space()='(" + expectedRecordTotal + ") Record Found']";
		By locator = By.xpath(messageHeaderXPath);

		waitUtils.waitForElementPresence(locator);
		WebElement element = waitUtils.waitForElementVisible(locator);
		System.out.println("Expected message: " + "(" + expectedRecordTotal + ") Record Found");
		System.out.println("Actual message found: " + element.getText());
		return true;

	}

	public Boolean isEmployeeRecordPresent(String employeeId, String firstName, String lastName) throws Exception {

		populateField(employeeNameInput, firstName + " " + lastName);
		populateField(employeeIdInput, employeeId);
		clickSearchButton();
		Thread.sleep(1000);
		return isRecordMatchingByTexts(employeeId, firstName, lastName);
	}

	public Boolean isEmployeeRecordPresentById(String employeeId) throws Exception {

		populateField(employeeIdInput, employeeId);
		Thread.sleep(1000);
		clickSearchButton();
		waitUtils.waitForPageLoaded();

		return isRecordMatchingByTexts(employeeId) && verifyTotalRecordFoundMessage("1");

	}

	public Boolean isEmployeeRecordPresentByName(String firstName, String lastName) throws Exception {

		populateField(employeeNameInput, firstName + " " + lastName);
		Thread.sleep(1000);

		clickSearchButton();
		waitUtils.waitForPageLoaded();

		return isRecordMatchingByTexts(firstName, lastName);
	}
	
	public List<WebElement> search(String employeeName) {
	    // 1. Nhập tên nhân viên
	    populateField(employeeNameInput, employeeName);

	    // 2. Nhấn nút search
	    clickSearchButton();

	    // 3. Đợi bảng hiển thị lại, cuộn tới header
	    waitUtils.waitForPageLoaded();
	    WebElement header = waitUtils.waitForElementPresence(tableHeader);
	    elementUtils.scrollToElement(header);

	    // 4. Đợi cho đến khi bảng có ít nhất 1 row (tránh stale element)
	    List<WebElement> rows = waitUtils.waitForElementsVisible(tableRows);

	    // 5. Thêm cơ chế retry để xử lý stale element (tối đa 3 lần)
	    for (int attempt = 0; attempt < 3; attempt++) {
	        try {
	            if (!rows.isEmpty()) {
	                // Kiểm tra và đảm bảo các row có nội dung
	                for (WebElement row : rows) {
	                    if (row.getText().isEmpty()) {
	                        throw new StaleElementReferenceException("Row chưa load xong");
	                    }
	                }
	                System.out.println("Total rows after search: " + rows.size());
	                return rows;
	            }
	        } catch (StaleElementReferenceException e) {
	            System.out.println("Retry getting rows due to stale element... Attempt: " + (attempt + 1));
	            rows = waitUtils.waitForElementsVisible(tableRows);
	        }
	    }

	    System.out.println("No valid rows found after search with name: " + employeeName);
	    return Collections.emptyList();
	}


}
