package excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This class contains the required functions for reading data from excel
 * @author Archana Patel
 *
 */
public class ExcelReader {
	private static XSSFSheet sheet;
	private static XSSFWorkbook workbook;
	private static XSSFCell cell;

	public static Object[][] readExcelForDataProvider(String dataFilePath, String dataSheetName, boolean hasHeader) {
		String[][] dataArray = null;
		int startRow = 0, startCol = 0;
		int totalRows = 1, totalCols = 1;
		int dataArrayRowCounter = 0, dataArrayColCounter = 0;

		try {
			FileInputStream inputFile = new FileInputStream(dataFilePath);

			//Access the required test data sheet
			workbook = new XSSFWorkbook(inputFile);
			sheet = workbook.getSheet(dataSheetName);

			totalRows = sheet.getLastRowNum() + 1;
			totalCols = sheet.getRow(1).getLastCellNum();

			if(hasHeader) {
				startRow++; //Start from Row #2 after skipping the header row
				dataArray = new String[totalRows-1][totalCols];
			} else
				dataArray = new String[totalRows][totalCols];

			for(int rowNum = startRow; rowNum < totalRows; rowNum++, dataArrayRowCounter++) {
				dataArrayColCounter = 0; //Resetting Column Counter

				for(int colNum = startCol; colNum < totalCols; colNum++, dataArrayColCounter++) {
					dataArray[dataArrayRowCounter][dataArrayColCounter] = getCellData(rowNum, colNum, dataFilePath);
				}
			}
		} catch(FileNotFoundException e) {
			System.out.println("Could not read the excel sheet located at: " + dataSheetName);
			e.printStackTrace();
		} catch(IOException e) {
			System.out.println("Could not read the excel sheet located at: " + dataSheetName);
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}

		return dataArray;
	}

	private static String getCellData(int rowNum, int colNum, String dataFilePath) {
		String cellData = "";
		CellType dataType;

		try{
			cell = sheet.getRow(rowNum).getCell(colNum);
			dataType = cell.getCellTypeEnum();

			if(dataType == CellType.BLANK)
				return "";
			else if(dataType == CellType.STRING)
				cellData = cell.getStringCellValue();
			else if(dataType == CellType.NUMERIC) {
				if(DateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:MM:SS");
					cellData = String.valueOf(dateFormat.format(cell.getDateCellValue()));
				} else
					cellData = String.valueOf(cell.getNumericCellValue());
			}
			else if(dataType == CellType.BOOLEAN)
				cellData = String.valueOf(cell.getBooleanCellValue());
			else
				return "";
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return cellData;
	}
}