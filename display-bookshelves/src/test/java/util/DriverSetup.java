package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverSetup {
	/**
	 * fetches the property file from the given file path
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static Properties readPropertiesFile(String filePath) throws FileNotFoundException, IOException {
		FileInputStream fileInputStream = null;
		Properties prop = null;
		try {
			// reading file from the specified path
			fileInputStream = new FileInputStream(filePath);

			// initializing the property object
			prop = new Properties();
			prop.load(fileInputStream);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if (fileInputStream != null)
				fileInputStream.close();
		}
		return prop;
	}

	/**
	 * fetches the value corresponding the provided propertyKey from the property
	 * file.
	 * 
	 * @param filePath
	 * @param propertyKey
	 * @return
	 * @throws IOException
	 */
	public static String getProperty(String filePath, String propertyKey) throws IOException {
		// get the file
		Properties prop = readPropertiesFile(filePath);
		// return value against propertyKey
		return prop.getProperty(propertyKey);
	}

	public static WebDriver getDriver() throws Exception {
		String driverName = getProperty("./src/test/resources/configurations/config.properties", "driver");
		WebDriver driver = null;

		switch (driverName) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			throw new Exception("specified browser not handled");
		}

		return driver;
	}
}
