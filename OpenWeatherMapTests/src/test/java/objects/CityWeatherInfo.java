package objects;

/**
 * This defines the structure of an object that contains the required weather information of a City
 * @author Archana Patel
 */
public class CityWeatherInfo{
	public double temperatureInFarenheit;
	public double windSpeedInMPH;
	public Integer cloudCoverPercentage;
	public double atmPressureInHPA;
	
	@Override
	public String toString() {
		return "API Weather Info [temperature=" + temperatureInFarenheit + 
								", windspeed=" + windSpeedInMPH + ", cloudcoverage=" + cloudCoverPercentage + 
								", atmosphericpressure=" + atmPressureInHPA + "]";
	}

	/**
	 * Custom Overridden equals method that compares whether two CityWeatherInfo objects contains the same data or not
	 * @param CityWeatherInfo object that needs to be compared
	 * @exception None
	 * @return true/false
	 */
	public boolean equals(Object compareObject) {
		if(compareObject == null)
			return false;
		else if(compareObject instanceof CityWeatherInfo) {
			CityWeatherInfo compareCityWeatherInfo = (CityWeatherInfo) compareObject;
			return(temperatureInFarenheit == compareCityWeatherInfo.temperatureInFarenheit 
					&& windSpeedInMPH == compareCityWeatherInfo.windSpeedInMPH
					&& cloudCoverPercentage == compareCityWeatherInfo.cloudCoverPercentage
					&& atmPressureInHPA == compareCityWeatherInfo.atmPressureInHPA);
		} else
			return false;
	}
}