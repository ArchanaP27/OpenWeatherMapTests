package base;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.relevantcodes.extentreports.LogStatus;

import helpers.Common;

/**
 * Page class is intended to be a base class for all the other page classes that have been developed using POM model  
 * @author Archana Patel
 */
public class Page extends Base{
	public static WebDriver driver;
	public static FileInputStream fis;
	public static Properties config = new Properties();

	/**
	 * This function initializes the driver based on the browser tyoe mentioned in the src/test/resources/properties/config.properties file
	 * @param None
	 * @exception None
	 * @return No return value
	 */
	public static void initConfiguration(){
		config = Common.loadGlobalConfig();
		PropertyConfigurator.configure("src" + File.separator + "test" + File.separator + "resources" + File.separator + "properties" + File.separator + "log4j.properties");

		if(config.getProperty("browser").toLowerCase().equals("chrome")) {
			String chromeDriver;
			if(SystemUtils.IS_OS_WINDOWS)
				chromeDriver = "chromedriver.exe";
			else
				chromeDriver = "chromedriver";

			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + 
															"resources" + File.separator+ "executables" + File.separator + chromeDriver);

			HashMap<String, Object> chromePreferences = new HashMap<String, Object>();
			chromePreferences.put("profile.default_content_setting_values.notifications", 2);
			chromePreferences.put("credentials_enable_service", false);
			chromePreferences.put("profile.password_manager_enabled", false);

			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePreferences);
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-infobars"); //Disables the yellow infobar that shows on the top while debugging

			driver = new ChromeDriver(options);
		} else if(config.getProperty("browser").toLowerCase().equals("firefox")) {
			//System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + 
			//												"resources" + File.separator+ "executables" + File.separator+ "geckodriver");
			driver = new FirefoxDriver();
		}

		driver.get(config.getProperty("weathersiteurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Long.parseLong(config.getProperty("implicitwaitdur")), TimeUnit.SECONDS);
		driver.switchTo().window(driver.getWindowHandle());
	}

	/**
	 * This is a global function that can be used for click on a given WebElement
	 * @param WebElement to be clicked on
	 * @exception None
	 * @return No return value
	 */
	public static void click(WebElement element) {
		element.click();
		log.debug("Clicking the element : " + element);
		test.log(LogStatus.INFO, "Clicking the element : " + element);
	}

	/**
	 * This is a global function that can be used to enter value in a textbox
	 * @param WebElement for the text box
	 * @param Value to be entered into the text box 
	 * @exception None
	 * @return No return value
	 */
	public static void type(WebElement element, String value) {
		element.sendKeys(value);
		log.debug("Typing value " + value + " into element : " + element);
		test.log(LogStatus.INFO, "Typing value " + value + " into element : " + element);
	}

	/**
	 * This is a global function to check whether an element is present
	 * @param WebElement to be checked
	 * @exception NoSuchElementException
	 * @return true/false
	 */
	public boolean isElementPresent(WebElement element) {
		try {
			element.isDisplayed(); //If element exists, then this should pass without throwing error
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * This is the function that will be called at the end of web browsing activities that will quit and kill the WebDriver object
	 * @param None
	 * @exception None
	 * @return No return value
	 */
	public static void quitBrowser(){	
		driver.quit();
		test.log(LogStatus.INFO, "Quitting and closing browser");
	}
}