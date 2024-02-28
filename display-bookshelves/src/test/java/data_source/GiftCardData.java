package data_source;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.DataProvider;

import util.GiftCardsTestDataFetcher;

/**
 * this class provides test data for class GiftCardTest it uses the annotation
 * DataProvider for transmitting data between the stored excel file and the test
 * cases using utility class GiftCardsTestDataFetcher
 * 
 * @see util.GiftCardsTestDataFetcher GiftDataFetcher
 * @see org.testng.annotations.DataProvider DataProvider
 * @see test_scenarios.GiftCardTest
 * @author 2308990
 *
 */
public class GiftCardData {

	GiftCardsTestDataFetcher fetcher;

	/**
	 * Initializing the fetcher object
	 * 
	 * @throws IOException
	 */
	public GiftCardData() throws IOException {
		fetcher = new GiftCardsTestDataFetcher();
	}

	/**
	 * for each test method looking for dataProvider named "amount",
	 * <ul>
	 * <li>positiveAmounTest - returns all valid amounts</li>
	 * <li>negativeAmounTest - returns all invalid amounts</li>
	 * <li>positiveButtonAmountTest - return indexes of buttons that should fix a
	 * valid amount</li>
	 * <li>positiveButtonAmountTest - return indexes of buttons that should not be
	 * able to fix a valid amount</li>
	 * </ul>
	 * 
	 * @param method
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "amount")
	public Object[][] amountInputData(Method method) throws Exception {
		// 1. read input from excel file
		String[][] inputs = fetcher.getAmountInputs();

		// 2. res object will store the filtered data
		List<Object[]> res = new ArrayList<>();

		// 3. return filtered data according to method name that's calling it
		switch (method.getName()) {
		case "positiveAmounTest":
			// 3.1 add all the rows where the last column indicates a positive outcome in
			// res
			for (String input[] : inputs) {
				if (input[input.length - 1].equals("POSITIVE")) {
					res.add(input);
				}
			}
			break;

		case "negativeAmountTest":
			// 3.2 add all the rows where the last column indicates a negative outcome in
			// res
			for (String input[] : inputs) {
				if (input[input.length - 1].equals("NEGATIVE")) {
					res.add(input);
				}
			}
			break;

		case "positiveButtonAmountTest":
			// 3.4 return valid indexes of amount option buttons

			// 3.4.1 Initializing the message to display on assertion error
			String message1 = "the user should be able to order with all three amount option buttons";

			// 3.4.2 returning test data directly
			return new Object[][] { { 0, message1 }, { 1, message1 }, { 2, message1 } };

		case "negativeButtonAmountTest":
			// 3.5 return invalid indexes of amount option buttons

			// 3.5.1 Initializing the message to display on assertion error
			String message2 = "there should be no more than 3 amount options buttons";

			// 3.5.2 returning test data directly
			return new Object[][] { { 3, message2 } };

		default:
			// 3.6 if an unlisted method tries to access the amount data, throw an exception
			throw new Exception("cannot provide data to unlisted method");
		}

		// 4. return null if no data exists
		if (res.size() == 0)
			return null;

		// 5. filter out the positive / negative test data markers and copy data into an
		// array of type Object

		// 5.1 initializing results array of type Object
		Object[][] result = new Object[res.size()][res.get(0).length - 1];

		// 5.2 for each row in res
		for (int i = 0; i < res.size(); i++) {
			// 5.2.1 make a deep copy of row, exclude the last column, and add it in result
			// array
			result[i] = Arrays.copyOfRange(res.get(i), 0, res.get(i).length - 1);
		}

		// 6. return the result array
		return result;
	}

	/**
	 * for each method 'm' looking for data provider names 'date'
	 * <ul>
	 * <li>positiveDateTest - return the dates with which the user should be able to
	 * order a gift card</li>
	 * <li>negativeDateTest - return the dates with which the user should not be
	 * able to order a gift card</li>
	 * </ul>
	 * 
	 * @param m
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "date")
	public Object[] getDates(Method m) throws Exception {
		String methodName = m.getName();

		// 1. get the first valid day - today
		LocalDate today = LocalDate.now();

		// 2. get the last valid day

		// 2.1. determine the interval over which the user should be able to order a
		// gift card
		int interval = today.getMonth().length(today.isLeapYear()) - today.getDayOfMonth()
				+ today.getMonth().plus(1).length(today.isLeapYear())
				+ today.getMonth().plus(2).length(today.isLeapYear());

		// 2.2. determine the last valid date by adding the interval to today's date
		LocalDate lastValiDate = today.plusDays(interval);

		// 3. return the test data in format Object[date, date, ....]
		switch (methodName) {
		case "positiveDateTest":
			// 3.1 return the first valid date, the second valid date, the second last valid
			// date and the last valid date
			return new Object[] { today, today.plusDays(1), lastValiDate.minusDays(1), lastValiDate };

		case "negativeDateTest":
			// 3.2 return yesterday's date and the next date to the last valid date
			return new Object[] { today.minusDays(1), lastValiDate.plusDays(1) };

		default:
			// 3.3 throw an exception if an unlisted method tries to access this data
			throw new Exception("cannot provide data to unlisted method");
		}
	}

	/**
	 * read all the zip codes listed in the excel sheet and return it all the zip
	 * codes listed are valid
	 * 
	 * @return
	 */
	@DataProvider(name = "zip")
	public String[] getZipcodes() {
		return fetcher.getPincodes();
	}

	/**
	 * return a product of tuple X records in format
	 * <p>
	 * [ [tuple[0], tuple[1], ..., records[0][0], records[0][1], ... ], [tuple[0],
	 * tuple[1], ..., records[1][0], records[1][1], ... ], ... ]
	 * </p>
	 * 
	 * @param tuple
	 * @param records
	 * @return cartesian product
	 * 
	 * @see #tupleProductHelper(List, String[]) overloaded method
	 */
	private List<String[]> tupleProductHelper(String[] tuple, List<String[]> records) {
		// 1. instantiate res list to store the results dynamic in number
		List<String[]> res = new ArrayList<>();

		// 2. determine the length of each tuple in res
		int resLen = tuple.length - 1 + records.get(0).length - 1;
		// +ve / -ve will be excluded form the tuple and the first (or any) record

		// 3. for each record in records
		for (String[] record : records) {
			// 3.1 instantiate a new auxiliary tuple to store the resulting tuple
			String aux[] = new String[resLen];

			// 3.2 insert values, first from tuple, then from the record into the auxiliary
			// tuple
			int index = 0;

			for (int i = 0; i < tuple.length - 1; index++, i++) {
				aux[index] = tuple[i];
			}

			for (int i = 0; i < record.length - 1; index++, i++) {
				aux[index] = record[i];
			}

			// 3.3 add the auxiliary record into res
			res.add(aux);
		}

		//4. return the Cartesian Product
		return res;
	}

	/**
	 * return a product of records X tuple in format
	 * <p>
	 * [ [records[0][0], records[0][1], ..., tuple[0], tuple[1], ...], [records[1][0], records[1][1], ..., tuple[0], tuple[1], ...], ... ]
	 * </p>
	 * 
	 * @param tuple
	 * @param records
	 * @return cartesian product
	 * 
	 * @see #tupleProductHelper(String[], List) overloaded method
	 */
	private List<String[]> tupleProductHelper(List<String[]> records, String[] tuple) {
		// 1. instantiate res list to store the results dynamic in number
		List<String[]> res = new ArrayList<>();

		// 2. determine the length of each tuple in res
		int resLen = tuple.length - 1 + records.get(0).length - 1; 
		// +ve / -ve will be excluded form the tuple and the first (or any) record

		// 3. for each record in records
		for (String[] record : records) {
			// 3.1 instantiate a new auxiliary tuple to store the resulting tuple
			String aux[] = new String[resLen];

			// 3.2 insert values, first from the record, then from the tuple into the auxiliary
			// tuple
			int index = 0;

			for (int i = 0; i < record.length - 1; index++, i++) {
				aux[index] = record[i];
			}
			
			for (int i = 0; i < tuple.length - 1; index++, i++) {
				aux[index] = tuple[i];
			}

			// 3.3 add the auxiliary record into res
			res.add(aux);
		}

		//4. return the Cartesian Product
		return res;
	}
	
	/**
	 * data provide for
	 * <ul>
	 * 	<ol>positiveCustomerAndRecipientTest - provides data that should be accepted for ordering a gift card</ol>
	 * 	<ol>negativeCustomerAndRecipientTest - provides data that should be rejected for ordering a gift card</ol>
	 * </ul>
	 * @param m
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "customer_and_recipient")
	public Object[][] getCustomerAndRecipientData(Method m) throws Exception {
		//1. fetch customer and recipient details from the excel sheet
		String customerDetails[][] = fetcher.getCustomerDetails();
		String recipientDetails[][] = fetcher.getRecipientDetails();
		
		//2. instantiate lists to store the filtered data
		List<String[]> positiveCustomerDetails = new ArrayList<>();
		List<String[]> negativeCustomerDetails = new ArrayList<>();
		List<String[]> positiveRecipientDetails = new ArrayList<>();
		List<String[]> negativeRecipientDetails = new ArrayList<>();
		
		//3. filter and store customer and recipient data into lists
		
		//3.1. for each customer detail
		for (String[] detail : customerDetails) {
			if (detail[detail.length - 1].equals("POSITIVE"))
				//3.1.1. store details marked 'POSITIVE' in the last cell in positiveCustomerDetails list
				positiveCustomerDetails.add(detail);
			else
				//3.1.2. store details marked 'NEGATIVE' in the last cell in negativeCustomerDetails list
				negativeCustomerDetails.add(detail);
		}
		
		//3.2 for each recipient detail
		for (String[] detail : recipientDetails) {
			if (detail[detail.length - 1].equals("POSITIVE"))
				//3.2.1. store details marked 'POSITIVE' in the last cell in positiveRecipientDetails list
				positiveRecipientDetails.add(detail);
			else
				//3.2.2. store details marked 'NEGATIVE' in the last cell in negativeRecipientDetails list
				negativeRecipientDetails.add(detail);
		}

		//4. get the name of the calling function
		String methodName = m.getName();
		
		//5. instantiate the res list to store the test data
		List<String[]> res = new ArrayList<>();
		
		//6. fill in res according to the method name
		switch (methodName) {
		case "positiveCustomerAndRecipientTest":
			//6.1.1 +ve recipient X all +ve customer
			res.addAll(tupleProductHelper(positiveRecipientDetails.get(0), positiveCustomerDetails));
			
			//6.1.2 all +ve recipient X +ve customer
			res.addAll(tupleProductHelper(positiveRecipientDetails, positiveCustomerDetails.get(0)));
			break;

		case "negativeCustomerAndRecipientTest":
			//6.2.1 all -ve recipient X +ve customer
			res.addAll(tupleProductHelper(negativeRecipientDetails, positiveCustomerDetails.get(0)));

			//6.2.2 +ve recipient X all -ve customer
			res.addAll(tupleProductHelper(positiveRecipientDetails.get(0), negativeCustomerDetails));
			break;

		default:
			//6.3 if an unlisted method tries to access the data, throw an error
			throw new Exception("cannot provide data to unlisted method");
		}
		
		//7. read results into an Object array
		Object[][] result = new Object[res.size()][res.get(0).length];

		for (int i = 0; i < res.size(); i++) {
			String detail[] = res.get(i);
			result[i] = detail;
		}
		
		//8. return this array
		return result;
	}
}
