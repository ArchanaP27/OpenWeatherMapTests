package pages.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * This class contains all the required locators for the Home page that displays as soon as we navigate to OpenWeatherMap.org
 * @author Archana Patel
 */
public class HomePageLocators {

	@FindBy(id="imperial")
	public WebElement farenheitSelector;

	@FindBy(xpath="//form[@id='searchform']/div[1]/input")
	public WebElement citySearchBox;

	@FindBy(xpath="//form[@id='searchform']/button[contains(text(), 'Search')]")
	public WebElement searchButton;
}