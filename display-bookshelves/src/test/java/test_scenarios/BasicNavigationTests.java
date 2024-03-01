package test_scenarios;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import page_objects.HomePage;
import util.DriverSetup;

/**
 * this class contains Tests of basic navigation
 * 
 * @author 2308990
 */

@Test
public class BasicNavigationTests {
	HomePage homePage;
	WebDriver driver;

	@BeforeClass
	public void init() throws Exception {
		// 1. Initialize a driver
		driver = DriverSetup.getDriver();

		// 2. initialize home page object
		homePage = new HomePage(driver);
	}

	@Test(priority = 0)
	@Parameters("home-page__title")
	public void basicNavigationToHomePageTest(String homePageTitle) {
		// 1. launch home page
		homePage.launch();

		// 2. check if the title is the same as expected
		Assert.assertEquals(driver.getTitle(), homePageTitle);
	}

	@Test(priority = 1)
	@Parameters("bookshelf-page__title")
	public void navigationFromHomePageToBookShelf(String bookShelfTitle) {
		// 1. click on the bookshelves link card
		homePage.cilckOnBookshelves();

		// 2. Store the current title 
		String actualTitle = driver.getTitle();

		// 3. go back to the home page
		driver.navigate().back();
		
		//4. Assertion for the expected title match
		Assert.assertEquals(actualTitle, bookShelfTitle);
	}

	@Test(priority = 2)
	@Parameters("giftcard-page__title")
	public void navigationFromHomePageToGiftCards(String giftCardTitle) {
		// 1. click on the gift card link
		homePage.clickOnGiftCard();

		// 2. Store the current title
		String title = driver.getTitle();

		// 3. navigate back to the home page
		driver.navigate().back();
		
		//4. Assertion
		Assert.assertEquals(title, giftCardTitle);
	}

	@AfterClass
	public void destroy() {
		driver.quit();
	}
}
