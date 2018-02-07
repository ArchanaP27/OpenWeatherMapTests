# Project Title

This Project has been created for development of Test Cases to validate data that is being displayed in OpenWeatherMaps.org

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to run the tests

```
Java JDK1.8
Eclipse
Maven
TestNG
Excel
Chrome
```

## Running the tests

The following steps should be done for running the tests

* Open the datapool file located at src/test/resources/datapools/WeatherTests.xlsx and include the city names that needs to be tested, one in each line
* Include which ones to run by updating Column A to "Run" for the cities that need to be tested
* Modify the configuration parameters, if needed , which is located at src/test/resources/properties/config.properties
* Ensure that the chromedriver has execute permissions set by right clicking on src/test/resources/executuables/chromedriver[.exe] and clicking Properties and check the Execute permissions
* Run the tests using the runner located at src/test/resources/runner/testng.xml
