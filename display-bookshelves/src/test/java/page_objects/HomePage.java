package page_objects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import util.DriverSetup;

public class HomePage {
	private String url = "https://www.urbanladder.com/";
	private By giftCardLinkLocator = By.linkText("Gift Cards");
	private By bookShelvesLocator = By.xpath("//a[@class='category']/h4[contains(text(), 'Bookshelves')]");
	private By dealZoneLocator = By.xpath("//span[contains(text(), 'Deal Zone')]");
	private By subMenuLocator = By.xpath("//li[@class = 'topnav_item dealzoneunit']//li[contains(@class, 'subnav_item')]//span");
	
	private List<String> subMenuList = new ArrayList<>();

	public String getUrl() {
		return url;
	}

	private WebDriver driver;
	private Actions actions;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(driver);
	}
	/**
	 * launches the home page in the browser
	 * 
	 * @return
	 */
	public void launch() {
		driver.get(url);
		driver.manage().window().maximize();
	}

	/**
	 * clicks on the gift card link
	 */
	public void clickOnGiftCard() {
		WebElement giftCardLink = driver.findElement(giftCardLinkLocator);
		giftCardLink.click();
	}

	/**
	 * clicks on the bookshelves card element
	 */
	public void cilckOnBookshelves() {
		WebElement bookshelves = driver.findElement(bookShelvesLocator);
		bookshelves.click();
	}
	
	public void dealZoneClick() {
		WebElement dealZoneButton = driver.findElement(dealZoneLocator);
		actions.moveToElement(dealZoneButton).build().perform();
	}
	
	public List<String> storeSubMenu() throws InterruptedException{
		List<WebElement> list = driver.findElements(subMenuLocator);
		
		for(WebElement element : list) {
			Thread.sleep(500);
			subMenuList.add(element.getText());
		}
		
		return subMenuList;
	}
	
	public void printSubMenuList() {
		int count = 1;
		for(String string : subMenuList) {
			System.out.println(count++ + ". " + string);
		}
	}
	
	public int subMenuCount() {
		int count = subMenuList.size();
		return count;
	}
	
	public void closeOperations() {
		this.driver.quit();
	}
	
	public static void main(String[] args) throws Exception {
		HomePage obj = new HomePage(DriverSetup.getDriver());
		obj.launch();
		obj.dealZoneClick();
		obj.storeSubMenu();
		obj.printSubMenuList();
		obj.closeOperations();
	}
}
