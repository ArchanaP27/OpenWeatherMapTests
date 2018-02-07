package pages.locators;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

/**
 * This class contains all the required locators for the page that displays the weather specific to the city
 * @author Archana Patel
 */
public class CityWeatherPageLocators {

	@FindBy(id="tab-hourly")
	public WebElement hourlyTab;

	@FindBy(xpath="(//td[@class='weather-forecast-hourly-list__sub-item'])[2]/span[1]")
	public WebElement firstForecastTemperature;

	@FindBy(xpath="(//td[@class='weather-forecast-hourly-list__sub-item'])[2]/p[1]")
	public WebElement firstForecastOtherInfo;

	@FindAll({
		@FindBy(className="weather-forecast-icons__item--active"),
		@FindBy(className="weather-forecast-icons__item")
	})
	public List<WebElement> mainWeatherForecastSection;

	@FindAll({
		@FindBy(xpath="//div[@title='Temp']")
	})
	public List<WebElement> mainForecastTempDivs;
}