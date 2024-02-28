package page_objects;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

	public BookshelvesPage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(driver);
		this.waitFor4Second = new WebDriverWait(driver, Duration.ofSeconds(4));
	}

	public void launch() {
		driver.get(url);
	}

	public void closeSignInPopup() {

		waitFor4Second.until(ExpectedConditions.visibilityOfElementLocated(closeSignInPopupLocator));
		driver.findElement(closeSignInPopupLocator).click();
	}

	public String setPriceRange() {
		driver.findElement(priceDropdownLocator).click();
		actions.dragAndDropBy(driver.findElement(priceRangeSliderLocator), -273, 0).perform();
		waitFor4Second.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='selrange-filter']")));
		return driver.findElement(By.xpath("//li[@class='selrange-filter']")).getText();
	}

	public String setStorageType() {
		driver.findElement(storageTypeDropdownLocator).click();
		driver.findElement(storageTypeOpenLocator).click();
		waitFor4Second
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//li[@data-option-name='Open'])[2]")));
		return driver.findElement(By.xpath("(//li[@data-option-name='Open'])[2]")).getText();
	}

	public String excludeOutOfStock() {
		driver.findElement(excludeOutOfStockLocator).click();
		waitFor4Second.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//li[@data-option-name='In Stock Only']/span")));
		return driver.findElement(By.xpath("//li[@data-option-name='In Stock Only']/span")).getText();
	}

	public void displayNamePrice(int noOfItems) {
		waitFor4Second.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@data-option-name='In Stock Only']")));
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
	
	public void saveScreenShot() {
		String fileName = this.driver.getTitle() + " [" 
				+ LocalDateTime.now().toString().replace(' ', '-').replace(':', '-') 
				+ "]";
		File img = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
		img.renameTo(new File("./src/test/resources/screenshots/" + fileName + ".png"));
	}

}
