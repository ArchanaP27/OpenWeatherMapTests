package pages.actions;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import base.Page;
import pages.locators.HomePageLocators;

/**
 * This class contains all the required actions in the Home page that displays as soon as we navigate to OpenWeatherMap.org
 * @author Archana Patel
 */
public class HomePage extends Page{
	public HomePageLocators home;

	public HomePage(){	
		this.home = new HomePageLocators();
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 10);
		PageFactory.initElements(factory, this.home);
	}

	/**
	 * Function for switching the temperature display to Farenheit as we are getting information in Farenheit from the API as well
	 * @param None
	 * @exception None
	 * @return No return value
	 */
	public void setTemperatureToFarenheit() {
		click(home.farenheitSelector);
	}

	/**
	 * Function for entering the required city in the search box and navigating to next page
	 * @param None
	 * @exception None
	 * @return Instance of SearchResultsPage signifying that we have navigated to the next page
	 */
	public SearchResultsPage searchCity(String cityName) {
		type(home.citySearchBox, cityName);
		click(home.searchButton);

		return new SearchResultsPage();
	}
}