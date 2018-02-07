package tests;

import java.util.ArrayList;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import api.APIRequests;
import base.Base;
import helpers.Common;
import helpers.DataProviders;
import objects.CityWeatherInfo;
import objects.TestInput;
import pages.actions.CityWeatherPage;
import pages.actions.HomePage;
import pages.actions.SearchResultsPage;

/**
 * This is the test class that contains the test that does the following:
 * 1. Checks whether the first available forecast weather information displayed in the OpenWeatherMaps.org website matches the data from API for a given city
 * 2. Checks whether the difference between the maximum and minimum forecast temperatures for a given city exceeds 10 degrees farenheit 
 * @author Archana Patel
 */
public class WeatherTest extends Base{
	@Test(dataProvider="WeatherTests", dataProviderClass=DataProviders.class, enabled=true)
	public void WeatherTests(TestInput testInput) {
		//Start the Extent Report test entry for the given city
		test = extent.startTest("Weather Test for " + testInput.cityName);
		test.log(LogStatus.INFO, "Starting tests for " + testInput.cityName);

		try {
			//Checks if we were able to find an entry for the given city in the cities.json file
			if(testInput.city.id != null) {
				log.debug("Found an entry for the city " + testInput.cityName + " in the Cities JSON File. Starting.");

				//Extract the weather information from API for the given city
				CityWeatherInfo currenctCityWeatherInfoAPI = APIRequests.extractLatestWeatherForecastFromAPI(testInput, config.getProperty("APIKEY"));

				//Extract the weather information from the website
				HomePage home = new HomePage();
				home.setTemperatureToFarenheit(); //Switch temperature to farenheit to be inline with the information from API

				SearchResultsPage resultsPage = home.searchCity(testInput.cityName.trim());
				CityWeatherPage cityWeatherPage = resultsPage.clickOnCityEntry(testInput.city.id.trim());

				ArrayList<Double> forecastWeathers = cityWeatherPage.extractForecastTemperatures();
				cityWeatherPage.navigateToHourlyTab();
				CityWeatherInfo currenctCityAPIWeatherInfoWeb = cityWeatherPage.extractFirstForecastInfo();

				//////////// TEST 1 //////////////
				//Checks to see if the forecast weather information from API matches the data from website
				if(currenctCityWeatherInfoAPI.equals(currenctCityAPIWeatherInfoWeb))
					test.log(LogStatus.PASS, "TEST1: Weather information from API matches with website");
				else {
					test.log(LogStatus.FAIL, "TEST1: Weather information from API does not match with website");

					if(currenctCityWeatherInfoAPI.temperatureInFarenheit != currenctCityAPIWeatherInfoWeb.temperatureInFarenheit)
						test.log(LogStatus.FAIL, "TEST1: Temperature from API (" + currenctCityWeatherInfoAPI.temperatureInFarenheit +
													") does not match Temperature from Web (" + currenctCityAPIWeatherInfoWeb.temperatureInFarenheit + ")");
	
					if(currenctCityWeatherInfoAPI.windSpeedInMPH != currenctCityAPIWeatherInfoWeb.windSpeedInMPH)
						test.log(LogStatus.FAIL, "TEST1: Wind Speed from API (" + currenctCityWeatherInfoAPI.windSpeedInMPH +
													") does not match Wind Speed from Web (" + currenctCityAPIWeatherInfoWeb.windSpeedInMPH + ")");
	
					if(currenctCityWeatherInfoAPI.cloudCoverPercentage != currenctCityAPIWeatherInfoWeb.cloudCoverPercentage)
						test.log(LogStatus.FAIL, "TEST1: Cloud Coverage from API (" + currenctCityWeatherInfoAPI.cloudCoverPercentage +
													"%) does not match Cloud Coverage from Web (" + currenctCityAPIWeatherInfoWeb.cloudCoverPercentage + "%)");
	
					if(currenctCityWeatherInfoAPI.atmPressureInHPA != currenctCityAPIWeatherInfoWeb.atmPressureInHPA)
						test.log(LogStatus.FAIL, "TEST1: Atmospheric Pressure from API (" + currenctCityWeatherInfoAPI.atmPressureInHPA +
													") does not match Atmospheric Pressure from Web (" + currenctCityAPIWeatherInfoWeb.atmPressureInHPA + ")");
				}

				//////////// TEST 2 //////////////
				//Check whether the difference between minimum and maximum forecast temperature listed on website is less than 10 or not
				double minForecastWeather = Common.minValueInList(forecastWeathers), maxForecastWeather = Common.maxValueInList(forecastWeathers);
				double diffForecastWeather = maxForecastWeather - minForecastWeather; 
				if(diffForecastWeather <= 10)
					test.log(LogStatus.PASS, "TEST2: Difference between Maximum Forecast Temperature (" + maxForecastWeather + ") "
												+ "and Minimum Forecast Temperature (" + minForecastWeather + ") is " + diffForecastWeather
												+ " which is less than 10 degrees farenheit");
				else
					test.log(LogStatus.FAIL, "TEST2: Difference between Maximum Forecast Temperature (" + maxForecastWeather + ") "
												+ "and Minimum forecast temperature (" + minForecastWeather + ") is " + diffForecastWeather
												+ " which is greater than 10 degrees farenheit");
			} else {
				log.debug("Could not find an entry for the city " + testInput.cityName + " in the Cities JSON File. Skipping.");
				test.log(LogStatus.FAIL, "Unable to find entry in cities.json for the city: " + testInput.cityName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			extent.endTest(test);
		}
	}
}