package pages.actions;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.relevantcodes.extentreports.LogStatus;

import base.Page;
import objects.CityWeatherInfo;
import pages.locators.CityWeatherPageLocators;

/**
 * This class contains all the required actions in the page that displays the weather specific to the city
 * @author Archana Patel
 */
public class CityWeatherPage extends Page{
	public CityWeatherPageLocators cityWeatherPage;

	public CityWeatherPage(){	
		this.cityWeatherPage = new CityWeatherPageLocators();
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 10);
		PageFactory.initElements(factory, this.cityWeatherPage);
	}

	/**
	 * Function for navigating to the Hourly tab once the webpage for city's weather opens up
	 * @param None
	 * @exception None
	 * @return No return value
	 */
	public void navigateToHourlyTab() {
		click(cityWeatherPage.hourlyTab);
	}

	/**
	 * This function extracts the first available forecast information in the Hourly tab for a given city
	 * @param None
	 * @exception None
	 * @return Object containing the first instance of forecast weather information for the given city
	 */
	public CityWeatherInfo extractFirstForecastInfo() {
		CityWeatherInfo currentCityWeatherInfo = new CityWeatherInfo();
		String firstForecastTempInfo, firstForecastOtherInfo;

		if(isElementPresent(cityWeatherPage.firstForecastTemperature)) {
			firstForecastTempInfo = cityWeatherPage.firstForecastTemperature.getText();
			firstForecastOtherInfo = cityWeatherPage.firstForecastOtherInfo.getText();

			currentCityWeatherInfo.temperatureInFarenheit = Double.parseDouble(((firstForecastTempInfo == null || firstForecastTempInfo.length() == 0) ? "0" : (firstForecastTempInfo.substring(0, firstForecastTempInfo.length()-3))));

			if(firstForecastOtherInfo.length() > 0 || firstForecastOtherInfo != null) {
				String[] otherForecastInfoArr = firstForecastOtherInfo.split(",");
				if(otherForecastInfoArr.length == 3) {
					currentCityWeatherInfo.windSpeedInMPH = Double.parseDouble(otherForecastInfoArr[0].trim());
					currentCityWeatherInfo.cloudCoverPercentage = Integer.parseInt(otherForecastInfoArr[1].split(": ")[1].replaceAll("%", ""));
					currentCityWeatherInfo.atmPressureInHPA = Double.parseDouble(otherForecastInfoArr[2].split(" ")[1]);

					test.log(LogStatus.PASS, "Weather information from website has been successfully extracted");
					test.log(LogStatus.INFO, "Weather Information from website. Temperature: " + currentCityWeatherInfo.temperatureInFarenheit + 
							 ". Wind Speed: " + currentCityWeatherInfo.windSpeedInMPH + 
							 ". Cloud Coverage: " + currentCityWeatherInfo.cloudCoverPercentage +
							 ". Atmospheric Pressure: " + currentCityWeatherInfo.atmPressureInHPA);
				} else {
					test.log(LogStatus.FAIL, "Unable to extract weather information from website");
				}
			}
		} else
			test.log(LogStatus.FAIL, "Unable to extract weather information from website");

		return currentCityWeatherInfo;
	}

	/**
	 * This function extracts the temperature for all the visible 10 forecast periods in the Main tab of the weather page for the given city
	 * @param None
	 * @exception None
	 * @return Array containing the temperatures for the 10 forecast period visible on the webpage
	 */
	public ArrayList<Double> extractForecastTemperatures() {
		ArrayList<Double> forecastTemps = new ArrayList<Double>();
		String forecastTemp;

		if(forecastTemps.size() > 0) {
			for(WebElement forecastTempDiv : cityWeatherPage.mainForecastTempDivs) {
				forecastTemp = forecastTempDiv.getText();
				forecastTemps.add(Double.parseDouble(((forecastTemp == null || forecastTemp.length() == 0) ? "0" : (forecastTemp.substring(0, forecastTemp.length()-3)))));
			}
		}

		return forecastTemps;
	}
}