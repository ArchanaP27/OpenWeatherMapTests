package pages.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.Page;

/**
 * This class contains all the required actions in the page that displays the possible entries for the given city
 * @author Archana Patel
 */
public class SearchResultsPage extends Page{
	
	/**
	 * Function for clicking on the entry depending on the city information we pulled from the cities.json file
	 * @param ID of the city that we are interested in
	 * @exception Generic exception
	 * @return Instance of CityWeatherPage signifying that we have navigated to the next page
	 */
	public CityWeatherPage clickOnCityEntry(String cityID) {
		try {
			WebElement requiredSearchResultElement = driver.findElement(By.xpath("//a[contains(@href, '" + cityID + "')]"));
			click(requiredSearchResultElement);
			Thread.sleep(3000); //Wait time since page is taking time to load at times
		} catch(Exception e) {
			e.printStackTrace();
		}

		return new CityWeatherPage();
	}
}