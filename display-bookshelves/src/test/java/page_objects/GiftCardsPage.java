package page_objects;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * this is a page object class for <a href = "https://www.urbanladder.com/gift-cards"> Urban Ladder's Gift Card Page</a>
 * 
 * this has the methods for interacting with the web page
 * @author 2308990
 *
 */
public class GiftCardsPage {
	private WebDriver driver;
	private Actions actions;
	private String url = "https://www.urbanladder.com/gift-cards";
	
	private By birthdayAndAnniversayLocator = By.xpath("//li[contains(@class, '_11b4v')][3]");
	private By amountButtonsLocator = By.className("HuPJS");
	private By monthDropDownLocator = By.cssSelector("._3PNvG select:nth-of-type(1)");
	private By dateDropDownLocator = By.cssSelector("._3PNvG select:nth-of-type(2)");
	private By nextButtonLocator = By.xpath("//button[@class = '_1IFIb _1fVSi action-button _1gIUf _1XfDi']");
	private By recipientNameInputLocator = By.name("recipient_name");
	private By recipientEmailInputLocator = By.name("recipient_email");
	private By recipientMobileNumberLocator = By.name("recipient_mobile_number");
	private By customerNameInputLocator = By.name("customer_name");
	private By customerEmailInputLocator = By.name("customer_email");
	private By customerMobileNumberLocator = By.name("customer_mobile_number");
	private By customerAdressInputLocator = By.name("customer_address");
	private By customerPincodeInputLocator = By.name("zip");
	private By optionalMessageLocator = By.name("message");
	private By confirmButtonLocator = By.xpath("//button[@class = '_3Hxyv _1fVSi action-button _1gIUf _1XfDi']");
	private By amountInputLocator = By.name("amount_select");
	private By confirmationSectionLocator = By.cssSelector("._14QEd._1QpDs");
	Wait<WebDriver> waitFor1Second;
	
	/**
	 * Initializing 
	 * <ol>
	 * 	<ul>driver object</ul>
	 * 	<ul>actions object</ul>
	 * 	<ul>all waits as per requirements</ul>
	 * </ol>
	 * @param driver
	 */
	public GiftCardsPage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(driver);
		this.waitFor1Second = new WebDriverWait(driver, Duration.ofSeconds(1));
	}
	
	/**
	 * launch the web page on a new browser window
	 */
	public void launch() {
		driver.get(url);
	}
	
	/**
	 * choose the birth and anniversary option on the <a href = "https://www.urbanladder.com/gift-cards"> Web Page </a>
	 */
	public void selectBirthAndAniversary() {
		//1. hover on the birthday anniversary card
		WebElement birthdayAndAnniversay = driver.findElement(birthdayAndAnniversayLocator);
		actions.scrollToElement(birthdayAndAnniversay)
		.moveToElement(birthdayAndAnniversay)
		.build()
		.perform();
		
		//2. wait for choose this button to appear
		By choosebirthdayAndAnniversayButtonLocator = By.xpath("(//button[contains(@class, '_1fVSi action-button _1njbS _1XfDi _3J6Eb')])[3]");
		waitFor1Second.until(ExpectedConditions.visibilityOfElementLocated(choosebirthdayAndAnniversayButtonLocator));
		
		//3. click on choose this button
		WebElement birthdayAndAnniversayActionButton = driver.findElement(choosebirthdayAndAnniversayButtonLocator);
		birthdayAndAnniversayActionButton.click();
	}
	
	/**
	 * fill the date passed as an argument in the date input under the 'customize form' on the web page
	 * @param date
	 * @see <a href = "https://www.urbanladder.com/gift-cards"> web page </a>
	 */
	private void fillDate(LocalDate date) {
		//1. generate the string that matches the format of the value attribute in the dropddown mwnues for selecting date
		String dateNum = Integer.toString(date.getDayOfMonth());
		String monthYear = date.getMonthValue() + "/" + date.getYear();
		
		//2. select the month and the year
		//2.1. make a Select object with the month drop down
		Select monthDropDown = new Select(driver.findElement(monthDropDownLocator));
		//2.2 select the specified month using monthYear generated string
		monthDropDown.selectByValue(monthYear);
		
		//3. select the date
		//3.1 make a Select object with the date drop down
		Select dateDropDown = new Select(driver.findElement(dateDropDownLocator));
		//3.2 select the specified date using the generated string
		dateDropDown.selectByValue(dateNum);
	}
	
	/**
	 * fill the customise form with the amount and date passed as arguments
	 * @param amount
	 * @param date
	 * @see #fillCustomiseForm(int) Overloaded Method
	 * @see #fillCustomiseForm(String) Oveloaded Method
	 * @see #fillCustomiseForm(int, LocalDate) Overloaded Method
	 */
	public void fillCustomiseForm(String amount, LocalDate date) {
		fillCustomiseForm(amount);
		this.fillDate(date);
	}
	
	/**
	 * fill the customise form with the amount passed as arguments, date remains the same as set by default in the web page
	 * @param amount
	 * 
	 * @see #fillCustomiseForm(int) Overloaded Method
	 * @see #fillCustomiseForm(int, LocalDate) Overloaded Method
	 * @see #fillCustomiseForm(String, LocalDate) Overloaded Method
	 */
	public void fillCustomiseForm(String amount) {
		driver.findElement(amountInputLocator).sendKeys(amount);
	}
	
	/**
	 * fill the customise form by selecting amount option button with the index and date passed as arguments
	 * @param amount
	 * @param date
	 * @see #fillCustomiseForm(int) Overloaded Method
	 * @see #fillCustomiseForm(String) Oveloaded Method
	 * @see #fillCustomiseForm(String, LocalDate) Overloaded Method
	 */
	public void fillCustomiseForm(int amountButtonIndex, LocalDate date) throws Exception {
		fillCustomiseForm(amountButtonIndex);
		this.fillDate(date);
	}
	
	/**
	 * clear out the amount input under the customize form on the web page
	 * 
	 * @see <a href = "https://www.urbanladder.com/gift-cards">Web Page</a>
	 */
	public void clearOutAmountInput() {
		driver.findElement(amountInputLocator).clear();
	}
	
	/**
	 * fill the customise form by selecting amount option button with the index passed as an argument, date remains the same as set by default in the web page
	 * @param amount
	 * 
	 * @see #fillCustomiseForm(String) Overloaded Method
	 * @see #fillCustomiseForm(int, LocalDate) Overloaded Method
	 * @see #fillCustomiseForm(String, LocalDate) Overloaded Method
	 */
	public void fillCustomiseForm(int amountButtonIndex) throws ArrayIndexOutOfBoundsException {
		//1. get all amount buttons
		List<WebElement> ammountButtons = driver.findElements(amountButtonsLocator);
		
		//2. throw exception if index exceeds the length of the button lists
		if(amountButtonIndex >= ammountButtons.size())
			throw new ArrayIndexOutOfBoundsException("index out bound, only " + ammountButtons.size()+ " buttons on the page");
		
		//3. click on the button with the specified index
		ammountButtons.get(amountButtonIndex).click();
	}
	
	/**
	 * clicks on the next button under the customise form in the web page
	 * @see <a href = "https://www.urbanladder.com/gift-cards">Web Page</a>
	 */
	public void clickOnNextButton() {
		WebElement nextButton = driver.findElement(nextButtonLocator);
		nextButton.click();
	}
	
	/**
	 * fill pincode input under the Customer and Recipient Details form on the Web Page
	 * 
	 * @see <a href = "https://www.urbanladder.com/gift-cards">Web Page</a>
	 * @param pincode
	 */
	public void fillPincode(String pincode) {
		driver.findElement(customerPincodeInputLocator).sendKeys(pincode);
	}
	
	/**
	 * clears out the pincode input under the Customer and Recipient Details form on the Web Page
	 * 
	 * @see <a href = "https://www.urbanladder.com/gift-cards">Web Page</a>
	 */
	public void clearPincode() {
		driver.findElement(customerPincodeInputLocator).clear();
	}
	
	/**
	 * fill the recipient form with the given data
	 * @param recipientName
	 * @param recipientEmail
	 * @param recipientMobileNumber
	 * @see <a href = "https://www.urbanladder.com/gift-cards">Web Page</a>
	 */
	public void fillRecipientDetails(String recipientName, String recipientEmail, String recipientMobileNumber) {
		//1. fill out the name
		driver.findElement(recipientNameInputLocator).sendKeys(recipientName);
		
		//2. fill out the email
		driver.findElement(recipientEmailInputLocator).sendKeys(recipientEmail);
		
		//3. fill out the mobile number
		driver.findElement(recipientMobileNumberLocator).sendKeys(recipientMobileNumber);
	}
	
	/**
	 * clears out the recipient data
	 */
	public void clearRecipientDetails() {
		//1. clear out name
		driver.findElement(recipientNameInputLocator).clear();
		
		//2. clear out email
		driver.findElement(recipientEmailInputLocator).clear();
		
		//3. clear out mobile number
		driver.findElement(recipientMobileNumberLocator).clear();
	}
	
	/**
	 * fill out the customer details with the given data
	 * @param customerName
	 * @param customerEmail
	 * @param customerMobileNumber
	 * @param customerAddress
	 */
	public void fillCustomerDetails(String customerName, String customerEmail, String customerMobileNumber, String customerAddress) {
		//1. fill in name
		driver.findElement(customerNameInputLocator).sendKeys(customerName);
		
		//2. fill in email
		driver.findElement(customerEmailInputLocator).sendKeys(customerEmail);
		
		//3. fill in mobile number
		driver.findElement(customerMobileNumberLocator).sendKeys(customerMobileNumber);
		
		//4. fill in the customer address
		driver.findElement(customerAdressInputLocator).sendKeys(customerAddress);
	}
	
	/**
	 * clears out the customer form
	 */
	public void clearCustomerDetails() {
		//1. clear out the name
		driver.findElement(customerNameInputLocator).clear();
		
		//2. clear out email
		driver.findElement(customerEmailInputLocator).clear();
		
		//3. clear out mobile number
		driver.findElement(customerMobileNumberLocator).clear();
		
		//4. clear out the customer address
		driver.findElement(customerAdressInputLocator).clear();
	}
	
	/**
	 * fill in the optional message with the message passed as an argument
	 * @param message
	 */
	public void fillOptionalMessage(String message) {
		driver.findElement(optionalMessageLocator).sendKeys(message);
	}
	
	/**
	 * click on the confirmation button under the customer and recipient data form
	 */
	public void clickOnConfirm() {
		driver.findElement(confirmButtonLocator).click();
	}
	
	/**
	 * @return true if confirmation button under the customer and recipient data form is enabled
	 */
	public boolean confirmationButtonEnabled() {
		return driver.findElement(confirmButtonLocator).isEnabled();
	}
	
	/**
	 * @return true if next button under the customise form is enabled
	 */
	public boolean nextButtonEnabled() {
		return driver.findElement(nextButtonLocator).isEnabled();
	}
	
	/**
	 * @return true if the entries to the customise and customer/recipient details forms were accepted and confirmation sections became visible
	 */
	public boolean entryAccepted() {
		return this.driver.findElement(confirmationSectionLocator).isDisplayed();
	}
}
