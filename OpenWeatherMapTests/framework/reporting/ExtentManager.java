package reporting;

import java.io.File;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

/**
 * This class contains some definition for the Extent Reports configuration
 * @author Archana Patel
 */
public class ExtentManager {
	private static ExtentReports extent;	
	
	public static ExtentReports getInstance(){	
		if(extent==null){
			extent = new ExtentReports(System.getProperty("user.dir") + File.separator + "target" + File.separator + "surefire-reports" + File.separator + 
												"html" + File.separator + "extent.html", true, DisplayOrder.OLDEST_FIRST);
			extent.loadConfig(new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + 
												"resources" + File.separator + "reporting" + File.separator + "ReportsConfig.xml"));
		}

		return extent;
	}
}