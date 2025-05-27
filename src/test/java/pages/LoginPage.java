package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;
import io.qameta.allure.Step;
import utils.ElementUtil;
import utils.WaitUtil;

public class LoginPage extends BasePage{

	private WebDriver driver;
	private ElementUtil elementUtils;
	private WaitUtil waitUtils;
	
	private String URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	
	
	private By usernameField = By.cssSelector("input[name='username']");
	private By passwordField = By.cssSelector("input[name='password']");
	private By loginBtn = By.cssSelector("button[type='submit']");

	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		elementUtils = new ElementUtil(driver);
		waitUtils = new WaitUtil(driver);

	}

	public DashboardPage login(String usernameValue, String passwordValue) {
		
		driver.get(URL);
		waitUtils.waitForPageLoaded();
		enterUsername(usernameValue);
		//elementUtils.setText(usernameField, usernameValue);
		elementUtils.setText(passwordField, passwordValue);
		elementUtils.clickElement(loginBtn);
		return  new DashboardPage(driver);
	}
	
	@Step("Nhập username: {username}")
    public void enterUsername(String usernameValue) {
		elementUtils.setText(usernameField, usernameValue);
    }

}
