package page_objects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.DriverSetup;

/**
 * this is a page object class for
 * "https://www.urbanladder.com/bookshelf?src=explore_categories"
 * 
 * this has the methods for interacting with the web page
 * 
 * @author 2308934
 *
 */

public class BookshelvesPage {

	private WebDriver driver;
	private Actions actions;
	private WebDriverWait waitFor4Second;
	private String url = "https://www.urbanladder.com/bookshelf?src=explore_categories";

	private By closeSignInPopupLocator = By.xpath("//a[contains(text(), 'Close')]");
	private By priceDropdownLocator = By.xpath("//ul/li/div[contains(text(), 'Price')]");
	private By priceRangeSliderLocator = By.xpath("//div[@class='noUi-base']/div[2]/div");
	private By storageTypeDropdownLocator = By.xpath("//ul/li/div[contains(text(), 'Storage Type')]");
	private By storageTypeOpenLocator = By.xpath("//label[@for='filters_storage_type_Open']");
	private By excludeOutOfStockLocator = By.xpath("//label[contains(text(), 'Exclude Out Of Stock')]");
	private By removePriceRangeFilterLocator = By.xpath("//li[@class='selrange-filter']");
	private By removeStorageTypeFilterLocator = By.xpath("(//li[@data-option-name='Open'])[2]");
	private By removeExcludeOutOfStockLocator = By.xpath("//li[@data-option-name='In Stock Only']/span");

	public BookshelvesPage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(driver);
		this.waitFor4Second = new WebDriverWait(driver, Duration.ofSeconds(4));
	}

	// launch the web page on a new browser window
	public void launch() {
		driver.get(url);
		driver.manage().window().maximize();
	}

	// close the sign-up pop-up
	public void closeSignInPopup() {

		waitFor4Second.until(ExpectedConditions.visibilityOfElementLocated(closeSignInPopupLocator));
		driver.findElement(closeSignInPopupLocator).click();
	}

	// Set the price range filter in drop-down using the slider with actions.
	public String setPriceRange() throws InterruptedException {
		driver.findElement(priceDropdownLocator).click();
		actions.dragAndDropBy(driver.findElement(priceRangeSliderLocator), -273, 0).perform();
		Thread.sleep(2000);
		waitFor4Second.until(ExpectedConditions.visibilityOfElementLocated(removePriceRangeFilterLocator));
		return driver.findElement(removePriceRangeFilterLocator).getText();
	}

	// select storage type filter from drop-down
	public String setStorageType() {
		driver.findElement(storageTypeDropdownLocator).click();
		driver.findElement(storageTypeOpenLocator).click();
		waitFor4Second.until(ExpectedConditions.visibilityOfElementLocated(removeStorageTypeFilterLocator));
		return driver.findElement(removeStorageTypeFilterLocator).getText();
	}

	// select the 'Exclude out of stock' filter
	public String excludeOutOfStock() {
		driver.findElement(excludeOutOfStockLocator).click();
		waitFor4Second.until(ExpectedConditions.visibilityOfElementLocated(removeExcludeOutOfStockLocator));
		return driver.findElement(removeExcludeOutOfStockLocator).getText();
	}

	// Console output the first few (noOfItems) items from the page after applying
	// filters
	public void displayNamePrice(int noOfItems) {
		waitFor4Second.until(ExpectedConditions.visibilityOfElementLocated(removePriceRangeFilterLocator));
		for (int i = 1; i <= noOfItems; i++) {
			String name = driver
					.findElement(By.xpath("//ul[@class='productlist withdivider clearfix bookshelves productgrid']/li["
							+ i + "]/div/div/a/div/span[@class='name']"))
					.getText();
			String price = driver
					.findElement(By.xpath("//ul[@class='productlist withdivider clearfix bookshelves productgrid']/li["
							+ i + "]/div/div/a/div[@class='price-number']/span"))
					.getText();
			System.out.println(i + ". Item name: " + name + " || Price: " + price);
		}
	}

	// main method to separately print out bookshelves items
	public static void main(String[] args) throws Exception {
		WebDriver driver = DriverSetup.getDriver();
		BookshelvesPage page = new BookshelvesPage(driver);
		page.launch();
		page.closeSignInPopup();
		page.setPriceRange();
		page.setStorageType();
		page.excludeOutOfStock();
		page.displayNamePrice(3);
		driver.quit();
	}

}
