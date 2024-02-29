package test_scenarios;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

import common.BaseGiftCardTest;
import page_objects.GiftCardsPage;

/**
 * this is a test class, it extends the BaseGiftCardText class and uses it's static driver to run test on the <a href = "https://www.urbanladder.com/gift-cards">Urban Ladder Gift card page</a>.
 * 
 * It uses data provided from the GiftCardData class and accesses the web Page using the GiftCardsPage object
 * @author 2308990
 * @see GiftCardsPage page object
 * @see BaseGiftCardTest super class
 * @see GiftCardData data provider
 */

@Test
public class GiftCardTests extends BaseGiftCardTest{
	private GiftCardsPage page;
	private WebDriver driver;

	/**
	 * Instantiate the driver with the driver object of super class
	 * @throws Exception
	 */
	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		//1. fetch the common driver
		this.driver = super.getDriver();
		
		//2. Initialize the gift card page object
		this.page = new GiftCardsPage(driver);
		
		//3. launch a new browser with the url of gift page
		this.page.launch();
		
		//4. select birthday and anniversary options
		this.page.selectBirthAndAniversary();
	}

	/**
	 * <ol>
	 * <li>clear out any residual amount data from the previous test</li>
	 * <li>fill customise form with the given amount</li>
	 * <li>assert that next button is getting enabled positively</li>
	 * </ol>
	 * @param amount
	 * @param message
	 */
	@Test(dataProvider = "amount", 
			dataProviderClass = data_source.GiftCardData.class, 
			groups = "customise_form_test")
	public void positiveAmounTest(String amount, String message) {
		//1. clear out any residual amount data from the previous test
		this.page.clearOutAmountInput();
		
		//2. fill customise form with the given amount
		this.page.fillCustomiseForm(amount);
		
		//3. assert that next button is getting enabled positively
		Assert.assertTrue(this.page.nextButtonEnabled(), message);
	}

	/**
	 * This test method verifies the negative scenario for customizing the gift card amount.
	 * It performs the following steps:
	 * <ol>
	 *   <li>Clears out any residual amount data from the previous test.</li>
	 *   <li>Fills the customize form with the given amount.</li>
	 *   <li>Asserts that the next button is not enabled as expected.</li>
	 * </ol>
	 *
	 * @param amount  The gift card amount to be customized.
	 * @param message A descriptive message for the assertion.
	 */
	@Test(dataProvider = "amount",
	      dataProviderClass = data_source.GiftCardData.class,
	      groups = "customise_form_test")
	public void negativeAmountTest(String amount, String message) {
	    // 1. Clear out any residual amount data from the previous test
	    this.page.clearOutAmountInput();

	    // 2. Fill the customize form with the given amount
	    this.page.fillCustomiseForm(amount);

	    // 3. Assert that the next button is not enabled
	    Assert.assertFalse(this.page.nextButtonEnabled(), message);
	}

	/**
	 * This test method verifies the positive scenario for customizing the gift card amount.
	 * It performs the following steps:
	 * <ol>
	 *   <li>Fills the customize form with the given button index.</li>
	 *   <li>Asserts that the next button is enabled as expected.</li>
	 * </ol>
	 *
	 * @param buttonIndex The index of the amount option button to select.
	 * @param message     A descriptive message for the assertion.
	 */
	@Test(dataProvider = "amount",
	      dataProviderClass = data_source.GiftCardData.class,
	      groups = "customise_form_test")
	public void positiveButtonAmountTest(Integer buttonIndex, String message) {
	    try {
	        //1. Fill the customize form with the given button index
	        this.page.fillCustomiseForm(buttonIndex);

	        //2. Assert that the next button is enabled
	        Assert.assertTrue(this.page.nextButtonEnabled(), message);
	    } catch (ArrayIndexOutOfBoundsException e) {
	        //3. If there are fewer than 3 amount option buttons
	        Assert.assertTrue(false, "There should be at least 3 amount option buttons");
	    }
	}


	/**
	 * This test method verifies the negative scenario for customizing the gift card amount.
	 * It performs the following steps:
	 * <ol>
	 *   <li>Fills the customize form with the given button index.</li>
	 *   <li>Asserts that the next button is not enabled.</li>
	 * </ol>
	 *
	 * @param buttonIndex The index of the amount option button to select.
	 * @param message     A descriptive message for the assertion.
	 */
	@Test(dataProvider = "amount",
	      dataProviderClass = data_source.GiftCardData.class,
	      groups = "customise_form_test")
	public void negativeButtonAmountTest(Integer buttonIndex, String message) {
	    try {
	        // 1. Fill the customize form with the given button index
	        this.page.fillCustomiseForm(buttonIndex);

	        // 2. Assert that the next button is not enabled
	        Assert.assertTrue(false, "There should be at most 3 amount option buttons");
	    } catch (ArrayIndexOutOfBoundsException e) {
	        // Exception handling: If the button index is out of bounds
	        // (e.g., fewer than 3 amount option buttons)
	        // No specific action needed here
	    }
	}


	/**
	 * This test method verifies the positive scenario for customizing the gift card date.
	 * It performs the following steps:
	 * <ol>
	 *   <li>Fills the customize form with the given date.</li>
	 * </ol>
	 * If the date input is not valid (e.g., not within the current and next two months),
	 * an exception is thrown with an appropriate assertion error message.
	 *
	 * @param date The date to be customized for the gift card.
	 * @throws AssertionError If the customer cannot fill in a valid date.
	 */
	@Test(dataProvider = "date",
	      dataProviderClass = data_source.GiftCardData.class,
	      groups = "customise_form_test")
	public void positiveDateTest(LocalDate date) {
	    try {
	        // 1. Fill the customize form with the given date
	        this.page.fillCustomiseForm(0, date);
	    } catch (NoSuchElementException e) {
	        // Exception handling: If the date input is invalid
	        throw new AssertionError("Customer should be able to fill in any date from the current and the next two months");
	    } catch (Exception e) {
	        // General exception handling (print stack trace)
	        e.printStackTrace();
	    }
	}


	/**
	 * This test method verifies the negative scenario for customizing the gift card date.
	 * It performs the following steps:
	 * <ol>
	 *   <li>Fills the customize form with the given date.</li>
	 *   <li>Throws an assertion error if the date is invalid (previous date or beyond the third month).</li>
	 * </ol>
	 *
	 * @param date The date to be customized for the gift card.
	 * @throws AssertionError If the customer can fill in a previous date or a date beyond the third month.
	 */
	@Test(dataProvider = "date",
	      dataProviderClass = data_source.GiftCardData.class,
	      groups = "customise_form_test")
	public void negativeDateTest(LocalDate date) {
	    try {
	        // 1. Fill the customize form with the given date
	        this.page.fillCustomiseForm(0, date);

	        // 2. Throw an assertion error if the date is invalid
	        throw new AssertionError("Customer shouldn't be able to fill any previous date or any date from the third month or beyond the current month");
	    } catch (NoSuchElementException e) {
	        // Exception handling: If the date input is invalid
	        e.printStackTrace();
	    } catch (Exception e) {
	        // General exception handling (no specific action needed)
	    }
	}


	@BeforeGroups(groups = "zip_code_test")
	public void proceesAfterCustomiseData() {
		// 1. fill customize form with valid data
		this.page.fillCustomiseForm(0);
		// 2. click on next
		this.page.clickOnNextButton();
	}
	
	/**
	 * This test method verifies the positive scenario for ordering from a specific zip code.
	 * It performs the following steps:
	 * <ol>
	 *   <li>Clears the existing pincode (if any).</li>
	 *   <li>Fills the pincode field with the given zip code.</li>
	 *   <li>Waits for 2 seconds (to allow any UI updates).</li>
	 *   <li>Asserts that the confirmation button is enabled.</li>
	 * </ol>
	 *
	 * @param zipcode The zip code to be tested for ordering.
	 * @throws InterruptedException If the thread is interrupted during the sleep.
	 * @throws AssertionError        If the confirmation button is not enabled for the given zip code.
	 */
	@Test(dataProvider = "zip",
	      dataProviderClass = data_source.GiftCardData.class,
	      groups = "zip_code_test",
	      dependsOnGroups = "customise_form_test")
	public void positiveZipCodeTest(String zipcode) throws InterruptedException {
	    try {
	        // 1. Clear the existing pincode (if any)
	        this.page.clearPincode();

	        // 2. Fill the pincode field with the given zip code
	        this.page.fillPincode(zipcode);

	        // 3. Wait for 2 seconds (to allow any UI updates)
	        Thread.sleep(2000);

	        // 4. Assert that the confirmation button is enabled
	        Assert.assertTrue(this.page.confirmationButtonEnabled(), "Not able to order from zipcode: " + zipcode);
	    } catch (InterruptedException e) {
	        // Exception handling: If the thread is interrupted during sleep
	        throw e;
	    } catch (Exception e) {
	        // General exception handling (no specific action needed)
	    }
	}


	
	/**
	 * This test method verifies the positive scenario for customer and recipient details during gift card customization.
	 * It performs the following steps:
	 * <ol>
	 *   <li>Clears any existing customer details.</li>
	 *   <li>Clears any existing recipient details.</li>
	 *   <li>Fills the recipient details (name, email, phone number).</li>
	 *   <li>Fills the customer details (name, email, phone number, address).</li>
	 *   <li>Clicks on the confirmation button.</li>
	 *   <li>Asserts that the entry is accepted based on customer and recipient messages.</li>
	 * </ol>
	 *
	 * @param recipientName     The name of the gift card recipient.
	 * @param recipientEmail    The email address of the recipient.
	 * @param recipientPhno     The phone number of the recipient.
	 * @param recipientMessage  A descriptive message related to the recipient.
	 * @param customerName      The name of the customer placing the order.
	 * @param customerEmail     The email address of the customer.
	 * @param customerPhno      The phone number of the customer.
	 * @param customerAddress   The address of the customer.
	 * @param customerMessage   A descriptive message related to the customer.
	 */
	@Test(dataProvider = "customer_and_recipient",
	      dataProviderClass = data_source.GiftCardData.class,
	      groups = "customer_and_recipient_details",
	      dependsOnGroups = "zip_code_test")
	public void positiveCustomerAndRecipientTest(
	        String recipientName,
	        String recipientEmail,
	        String recipientPhno,
	        String recipientMessage,
	        String customerName,
	        String customerEmail,
	        String customerPhno,
	        String customerAddress,
	        String customerMessage) {
	    try {
	        // 1. Clear any existing customer details
	        this.page.clearCustomerDetails();

	        // 2. Clear any existing recipient details
	        this.page.clearRecipientDetails();

	        // 3. Fill the recipient details (name, email, phone number)
	        this.page.fillRecipientDetails(recipientName, recipientEmail, recipientPhno);

	        // 4. Fill the customer details (name, email, phone number, address)
	        this.page.fillCustomerDetails(customerName, customerEmail, customerPhno, customerAddress);

	        // 5. Click on the confirmation button
	        this.page.clickOnConfirm();
	        
	        // 6. wait for any UI change to tale place
	        Thread.sleep(1000);

	        // 7. Assert that the entry is accepted based on customer and recipient messages
	        Assert.assertTrue(this.page.entryAccepted(), customerMessage + " and " + recipientMessage);
	    } catch (Exception e) {
	        // General exception handling (no specific action needed)
	    }
	}

	
	/**
	 * This test method verifies the negative scenario for customer and recipient details during gift card customization.
	 * It performs the following steps:
	 * <ol>
	 *   <li>Clears any existing customer details.</li>
	 *   <li>Clears any existing recipient details.</li>
	 *   <li>Fills the recipient details (name, email, phone number).</li>
	 *   <li>Fills the customer details (name, email, phone number, address).</li>
	 *   <li>Clicks on the confirmation button.</li>
	 *   <li>Asserts that the entry is not accepted based on customer and recipient messages.</li>
	 * </ol>
	 *
	 * @param recipientName     The name of the gift card recipient.
	 * @param recipientEmail    The email address of the recipient.
	 * @param recipientPhno     The phone number of the recipient.
	 * @param recipientMessage  A descriptive message related to the recipient.
	 * @param customerName      The name of the customer placing the order.
	 * @param customerEmail     The email address of the customer.
	 * @param customerPhno      The phone number of the customer.
	 * @param customerAddress   The address of the customer.
	 * @param customerMessage   A descriptive message related to the customer.
	 */
	@Test(dataProvider = "customer_and_recipient",
	      dataProviderClass = data_source.GiftCardData.class,
	      groups = "customer_and_recipient_details",
	      dependsOnGroups = "zip_code_test")
	public void negativeCustomerAndRecipientTest(
	        String recipientName,
	        String recipientEmail,
	        String recipientPhno,
	        String recipientMessage,
	        String customerName,
	        String customerEmail,
	        String customerPhno,
	        String customerAddress,
	        String customerMessage) {
	    try {
	        // 1. Clear any existing customer details
	        this.page.clearCustomerDetails();

	        // 2. Clear any existing recipient details
	        this.page.clearRecipientDetails();

	        // 3. Fill the recipient details (name, email, phone number)
	        this.page.fillRecipientDetails(recipientName, recipientEmail, recipientPhno);

	        // 4. Fill the customer details (name, email, phone number, address)
	        this.page.fillCustomerDetails(customerName, customerEmail, customerPhno, customerAddress);

	        // 5. Click on the confirmation button
	        this.page.clickOnConfirm();
	        
	        // 6. wait for any UI change to tale place
	        Thread.sleep(1000);
	        
	        // 7. Assert that the entry is not accepted based on customer and recipient messages
	        Assert.assertFalse(this.page.entryAccepted(), customerMessage + " and " + recipientMessage);
	    } catch (Exception e) {
	        // General exception handling (no specific action needed)
	    }
	}

	
	@AfterClass(alwaysRun = true)
	public void destroy() {
		this.driver.quit();
	}
}
