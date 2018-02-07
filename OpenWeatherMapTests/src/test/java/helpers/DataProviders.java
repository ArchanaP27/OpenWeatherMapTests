package helpers;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.testng.annotations.DataProvider;

import excel.ExcelReader;
import objects.TestInput;

/**
 * This class contains all the data provider methods for the tests
 * @author Archana Patel
 *
 */
public class DataProviders {
	/**
	 * Data Provider method that reads the input from src/test/resources/datapools folder and tries to search for Excel file that matches the name of the test method
	 * @param Name of method calling this data provider
	 * @exception None
	 * @return Object containing the input data
	 */
	@DataProvider(name = "WeatherTests")
	public Object[][] getTestData(Method methodName) {
		ArrayList<TestInput> testDataList = new ArrayList<TestInput>(); //Object to store Test Run instances

		Object[][] testDataArray = null;
		Object[][] testListObj = null;

		int numTestInput = 1;

		String inputFileName = methodName.getName().trim();
		String inputFileWithPath = System.getProperty("user.dir") + File.separator + "src" + File.separator+ "test" + File.separator + 
											"resources" + File.separator + "datapools" + File.separator + inputFileName + ".xlsx";
		File f = new File(inputFileWithPath);

		if(f.exists() && f.isFile()) {
			testDataArray = ExcelReader.readExcelForDataProvider(inputFileWithPath, "InputData", true);
		} else {
			System.out.println("File not there");
		}

		if(testDataArray != null) {
			for(int rowNum = 0; rowNum < testDataArray.length; rowNum++) {
				TestInput testInput = new TestInput();

				if(testDataArray[rowNum][0].toString().trim().toUpperCase().equals("RUN")) {
					testInput.seqNumber = numTestInput++;	
					testInput.cityName = (String) testDataArray[rowNum][1];
					testDataList.add(testInput);
				}
			}

			if(testDataList.size() > 0) {
				testDataList = Common.addCityInfoToInput(testDataList);
				//Store each Test Data Instance in one row of the Object
				testListObj = new Object[testDataList.size()][1];

				for(int i = 0; i < testDataList.size(); i++)
					testListObj[i][0] = testDataList.get(i);
			} else {
				testListObj = new Object[1][1];
				testListObj[0][0] = new TestInput(); //Sending a blank TestInput
			}
		}

		return testListObj;
	}
}