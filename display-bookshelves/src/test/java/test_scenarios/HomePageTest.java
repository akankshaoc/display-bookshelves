package test_scenarios;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import net.bytebuddy.asm.Advice.This;
import page_objects.HomePage;
import util.DriverSetup;

public class HomePageTest {

	public HomePage page;
	WebDriver driver;
	
	List<String> subMenuList = new ArrayList<>(); 
	
	@BeforeClass(alwaysRun = true)
	public void init() throws Exception {
		this.driver = DriverSetup.getDriver();
		this.page = new HomePage(driver);
		this.page.launch();
	}
	
	@Test(priority = 0)
	public void subMenuCount() throws InterruptedException {
		this.page.dealZoneClick();
		subMenuList = this.page.storeSubMenu();
		
		Assert.assertEquals(page.subMenuCount(), 8);
	}
	
	@Test(priority = 1)
	public void subMenuItemEmpty() {
		for(String item : subMenuList) {
			if(item.length() <= 0) {
				throw new AssertionError("Sub-Menu Item found empty !");
			}
		}
	}
	
	@AfterClass(alwaysRun = true)
	public void destroy() {
		this.driver.quit();
	}
	
}
