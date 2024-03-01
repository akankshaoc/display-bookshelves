package common;

import org.openqa.selenium.WebDriver;

import util.DriverSetup;

/**
 * this class consists of private and static field <code>driver</code>
 * use method <code>getDriver()</code> to access this driver
 * only a single instance of driver is instanciated and made accessible using the <code>getDriver()</code> method
 * @author 2308990
 *
 */
public class BaseGiftCardTest {
	static private WebDriver driver;
	
	/**
	 * returns the static field of driver
	 * @return WebDriver
	 * @throws Exception
	 */
	public WebDriver getDriver() throws Exception {
		//1. if the driver has not been instantiated yet, get a new web driver from DriverSetup utility class
		if(driver == null) driver = DriverSetup.getDriver();
		
		//2. if a driver exists, return that
		return driver;
	}
}
