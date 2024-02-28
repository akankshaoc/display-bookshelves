package util;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GiftCardsTestDataFetcher {
	public final String FILE_PATH = "./src/test/resources/test_data/gift_card_test_data.xlsx";
	private XSSFWorkbook workbook;
	private XSSFSheet amount;
	private XSSFSheet recipientDetails;
	private XSSFSheet customerDetails;
	private XSSFSheet pincodes;
	
	/**
	 * Initialize all workbooks and sheets using the FILE_PATH
	 * @throws IOException
	 * @see <a href = "/display-bookshelves/src/test/resources/test_data/gift_card_test_data.xlsx"> Data Source Excel Sheet </a>
	 */
	public GiftCardsTestDataFetcher() throws IOException {
		FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
		this.workbook = new XSSFWorkbook(fileInputStream);
		this.amount = workbook.getSheet("Amount Input");
		this.recipientDetails = workbook.getSheet("Recipient Details");
		this.customerDetails = workbook.getSheet("Customer Details");
		this.pincodes = workbook.getSheet("Pincodes");
	}
	
	/**
	 * return records for amounts
	 * @return [amount, assertion message, expected result]
	 */
	public String[][] getAmountInputs() {
		return readForSheet(amount);
	}
	
	/**
	 * read the sheet and returns a 2D String array storing its values
	 * @param sheet
	 * @return 2D array representing sheet
	 */
	private String[][] readForSheet(XSSFSheet sheet) {
		//1. res 2D array to store results
		String [][] res = new String[sheet.getLastRowNum() - 1][sheet.getRow(0).getLastCellNum()];
		
		//2. DataFormatter object to format all data read from the excel sheet
		DataFormatter formatter = new DataFormatter();
		
		//3. read each cell in the sheet and store it in res
		for(int rowIndex = 1; rowIndex < sheet.getLastRowNum(); rowIndex++) {
			XSSFRow row = sheet.getRow(rowIndex);
			for(int colIndex = 0; colIndex < row.getLastCellNum(); colIndex++) {
				res[rowIndex - 1][colIndex] = formatter.formatCellValue(row.getCell(colIndex));
			}
		}
		
		//4. return res
		return res;
	}
	
	/**
	 * read the sheet and return a 1D String array for the specified column
	 * @param sheet
	 * @param col
	 * @return 1D Array representing the specified column of the given sheet
	 */
	private String[] readColForSheet(XSSFSheet sheet, int col) {
		//1. res 1D array to store the results
		String [] res = new String[sheet.getLastRowNum() - 1];
		
		//2. storing all the datat from the sheet of the specified column in res
		for(int row = 1; row < sheet.getLastRowNum(); row++) {
			res[row - 1] = sheet.getRow(row).getCell(col).toString();
		}
		
		//3. return res
		return res;
	}
	
	/**
	 * return recipient records from the excel sheet
	 * @return [name, email, mobile number, assertion message, expected results]
	 */
	public String[][] getRecipientDetails() {
		return this.readForSheet(recipientDetails);
	}
	
	/**
	 * return customer records from the excel sheet
	 * @return [name, email, mobile number, address, assertion message, expected results]
	 */
	public String[][] getCustomerDetails() {
		return this.readForSheet(customerDetails);
	}
	
	/**
	 * return pincodes from the excel sheet
	 * @return [pincodes]
	 */
	public String[] getPincodes() {
		String data[] = this.readColForSheet(pincodes, 0);
		
		// cleaning string in format of decimals (e.g. 500001.0 -> 500001)
		for(int i = 0; i < data.length; i++) {
			data[i] = data[i].substring(0, data[i].indexOf('.')); 
		}
		return data;
	}
}
