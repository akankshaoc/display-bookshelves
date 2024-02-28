package test_scenarios;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import page_objects.BookshelvesPage;
import util.DriverSetup;

public class BookshelvesTest {
	private WebDriver driver;
	private BookshelvesPage page;

	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		this.driver = DriverSetup.getDriver();
		this.page = new BookshelvesPage(driver);
		this.page.launch();
		this.page.closeSignInPopup();
	}

	@Test(priority = 0, retryAnalyzer = listeners.RetryAnalyzer.class)
	public void testPriceRange() {
		String actualText = page.setPriceRange();
		String expectedText = "₹608 - ₹15,430";
		Assert.assertEquals(actualText, expectedText, "Price Range not selected properly!");

	}

	@Test(priority = 1, retryAnalyzer = listeners.RetryAnalyzer.class)
	public void testStorageType() {
		String actualText = page.setStorageType();
		String expectedText = "Open";
		Assert.assertEquals(actualText, expectedText, "Storage type not set properly!");
	}

	@Test(priority = 2, retryAnalyzer = listeners.RetryAnalyzer.class)
	public void testExcludeOutOfStock() {
		String actualText = page.excludeOutOfStock();
		String expectedText = "In Stock Only";
		Assert.assertEquals(actualText, expectedText, "Also displaying out of stock items!");
	}
	
	@AfterClass(alwaysRun = true)
	public void destroy() {
		this.driver.quit();
	}
}
