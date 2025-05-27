package base;

import org.openqa.selenium.WebDriver;

import pages.MainMenu;

public class BasePage {

	private WebDriver driver;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public MainMenu menu() {
		return new MainMenu(driver);
	}

	
	
	
	
}
