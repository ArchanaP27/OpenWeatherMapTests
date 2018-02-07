package helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import objects.City;
import objects.Coordinate;
import objects.TestInput;

/**
 * This class contains some of the common functions that are being used in this project
 * @author Archana Patel
 */
public class Common {
	/**
	 * Function for loading the config.properties file
	 * @param None
	 * @exception FileNotFoundException, IOException
	 * @return Object containing the properties
	 */
	public static Properties loadGlobalConfig() {
		FileInputStream fis;
		Properties config = new Properties();

		try {
			fis = new FileInputStream(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + 
												"resources" + File.separator + "properties" + File.separator + "config.properties");
			config.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return config;
	}

	/**
	 * Function for enriching the input data given by the user with the data from cities.json file (ID, Country, Coordinates)
	 * @param List of inputs from the user read from src/test/resources/datapools folder
	 * @exception FileNotFoundException
	 * @return Enriched list of inputs
	 */
	public static ArrayList<TestInput> addCityInfoToInput(ArrayList<TestInput> testInputs) {
		try {
			FileInputStream reader = new FileInputStream(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + 
															"resources" + File.separator + "datapools" + File.separator + "city.list.json");
			DocumentContext jsonContext = JsonPath.parse(reader);

			for(TestInput currentTestInput : testInputs) {
				//ArrayList<HashMap<String, Object>> dataList = jsonContext.read("$..[?(@.name == 'Atlanta' || @.name == 'Phoenix')]");
				ArrayList<HashMap<String, Object>> currentCityDataList = jsonContext.read("$..[?(@.name == '" + currentTestInput.cityName + "')]");
				City currentCity = new City();
				Coordinate coord = new Coordinate();

				if(currentCityDataList.size() > 0) { //Making sure we were able to find at least one instance of the city in the JSON file
					currentCity.id = currentCityDataList.get(0).get("id").toString();
					currentCity.name = currentTestInput.cityName;
					currentCity.country = currentCityDataList.get(0).get("country").toString();

					coord.lon = currentCityDataList.get(0).get("coord").toString().split(",")[0].split("=")[1].toString();
					coord.lat = currentCityDataList.get(0).get("coord").toString().split(",")[1].split("=")[1].toString();
				}

				currentCity.coord = coord;
				currentTestInput.city = currentCity;
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}

		return testInputs;
	}

	/**
	 * Function for rounding up a given number
	 * @param Number of decimals to round to
	 * @param Value that needs to be rounded
	 * @exception None
	 * @return Rounded value
	 */
	public static double roundUp(int numDecimals, double value) {
		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(numDecimals, BigDecimal.ROUND_CEILING);

		return bd.doubleValue();
	}

	/**
	 * Function for returning the minimum value from a given list of numbers
	 * @param List of numbers
	 * @exception None
	 * @return The minimum number
	 */
	public static double minValueInList(ArrayList<Double> list) {
		if(list.size() > 0)
			return Collections.min(list);
		else
			return -99;
	}

	/**
	 * Function for returning the maximum value from a given list of numbers
	 * @param List of numbers
	 * @exception None
	 * @return The maximum number
	 */
	public static double maxValueInList(ArrayList<Double> list) {
		if(list.size() > 0)
			return Collections.max(list);
		else
			return -99;
	}
}