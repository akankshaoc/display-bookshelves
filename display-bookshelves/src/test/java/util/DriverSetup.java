package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverSetup {
	/**
	 * fetches the property file from the given file path
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static Properties readPropertiesFile(String filePath) throws FileNotFoundException, IOException {
		FileInputStream fileInputStream  = null;
		Properties prop = null;
		try {
			//reading file from the specified path
		fileInputStream = new FileInputStream(filePath);
		
		// initializing the property object
		prop = new Properties();
		prop.load(fileInputStream);
		} catch(FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if(fileInputStream != null) fileInputStream.close();
		}
		return prop;
	}
	
	/**
	 * fetches the value corresponding the provided propertyKey from the property file.
	 * @param filePath
	 * @param propertyKey
	 * @return
	 * @throws IOException
	 */
	public static String getProperty(String filePath, String propertyKey) throws IOException {
		// get the file
		Properties prop = readPropertiesFile(filePath);
		//return value against propertyKey
		return prop.getProperty(propertyKey);
	}
	
	public static WebDriver getDriver() throws Exception {
		String driverName = getProperty("./src/test/resources/configurations/config.properties", "driver");
		WebDriver driver = null;
		
		switch (driverName) {
		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			//chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
			//chromeOptions.addArguments("--headless");
			driver = new ChromeDriver(chromeOptions);
			break;
		case "firefox": 
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
			firefoxOptions.addArguments("--headless");
			driver = new FirefoxDriver(firefoxOptions);
			break;
		case "edge":
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
			edgeOptions.addArguments("--headless");
			driver = new EdgeDriver(edgeOptions);
			break;
		default:
			throw new Exception("specified browser not handled");
		}
		
		return driver;
	}
}
