package api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.relevantcodes.extentreports.LogStatus;

import base.Base;
import helpers.Common;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import objects.CityWeatherInfo;
import objects.TestInput;

/**
 * This class contains the functions that are related to interacting with the API for OpenWeatherMaps
 * @author Archana Patel
 */
public class APIRequests extends Base {
	/**
	 * Function for querying the OpenWeatherMaps' Forecast API for the given city
	 * @param City ID that we are interested in
	 * @param API Key that can be used for the request
	 * @exception None
	 * @return JSON response as a string
	 */
	private static String generateWeatherForecastAPIResponse(TestInput currentCityInput, String apiKey) {
		Response rawResponse = 
				given().
					param("id", currentCityInput.city.id).
					param("APPID", apiKey).
					param("units", "imperial").
					//log().all().
				when().
					get(Resources.weatherForecastResource()).
				then().
					assertThat().
						statusCode(200).
						and().contentType(ContentType.JSON).
						and().body("cod", equalTo("200")).
						//log().all().
				extract().
					response();

		return rawResponse.asString();
	}

	/**
	 * Function for extracting the required information from the JSON response of the OpenWeatherMaps' Forecast API
	 * @param City ID that we are interest in
	 * @param API Key that can be used for the request
	 * @exception Generic Exception
	 * @return Instance of CityWeatherInfo object that contains the required weather information for the given city
	 */
	public static CityWeatherInfo extractLatestWeatherForecastFromAPI(TestInput currentCityInput, String apiKey) {
		String weatherForecastResponse = generateWeatherForecastAPIResponse(currentCityInput, apiKey);
		CityWeatherInfo currentCityLatestForecast = new CityWeatherInfo();

		try {
			if(weatherForecastResponse != null) {
				DocumentContext jsonContext = JsonPath.parse(weatherForecastResponse);
				currentCityLatestForecast = new CityWeatherInfo();

				//Construct the API Response data object and populate the fields
				currentCityLatestForecast.temperatureInFarenheit = Common.roundUp(1, Double.parseDouble(jsonContext.read("$['list'][0]['main']['temp']").toString()));
				currentCityLatestForecast.windSpeedInMPH = jsonContext.read("$['list'][0]['wind']['speed']");
				currentCityLatestForecast.cloudCoverPercentage = jsonContext.read("$['list'][0]['clouds']['all']");
				currentCityLatestForecast.atmPressureInHPA = jsonContext.read("$['list'][0]['main']['grnd_level']");

				test.log(LogStatus.PASS, "Weather information from API has been successfully extracted for " + currentCityInput.cityName);
				test.log(LogStatus.INFO, "Weather Information from API. Temperature: " + currentCityLatestForecast.temperatureInFarenheit + 
										 ". Wind Speed: " + currentCityLatestForecast.windSpeedInMPH + 
										 ". Cloud Coverage: " + currentCityLatestForecast.cloudCoverPercentage +
										 ". Atmospheric Pressure: " + currentCityLatestForecast.atmPressureInHPA);
			} else {
				test.log(LogStatus.FAIL, "Unable to extract weather information from API for " + currentCityInput.cityName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return currentCityLatestForecast;
	}
}