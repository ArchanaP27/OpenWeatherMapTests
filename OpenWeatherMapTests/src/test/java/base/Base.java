package base;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import helpers.Common;
import io.restassured.RestAssured;
import reporting.ExtentManager;

/**
 * This class contains the base setup for every test class that will be used in this project
 * @author Archana Patel
 */
public class Base {
	public static ExtentReports extent;
    public static ExtentTest test;
    public static Properties config;
    public static Logger log = Logger.getLogger(Base.class);

    @BeforeSuite
    public void setUp()
    {
    		try {
	        extent = ExtentManager.getInstance();

	        extent.addSystemInfo("OS", "Mac Sierra");
	        extent.addSystemInfo("Host Name", "archana-mac");
	        extent.addSystemInfo("Environment", "QA");
	        extent.addSystemInfo("User Name", "Archana Patel");
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    }

	@BeforeClass
	public void globalSetup() {
		config = Common.loadGlobalConfig();
		RestAssured.baseURI = config.getProperty("APIHOST");
	}

    @BeforeMethod
	public void localSetUp() {
		Page.initConfiguration();
	}

    @AfterMethod
    public void localTearDown()
    {
        if(Page.driver != null)
			Page.quitBrowser();
    }

    @AfterSuite
    public void tearDown() {
    		extent.flush();
    		extent.close();
    }
}