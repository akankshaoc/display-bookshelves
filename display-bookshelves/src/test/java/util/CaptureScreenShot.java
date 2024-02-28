package util;

import java.io.File;
import java.time.LocalDateTime;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import common.BaseGiftCardTest;

public class CaptureScreenShot extends BaseGiftCardTest{

	public void save(String name) throws Exception {
		WebDriver driver = super.getDriver();
		String fileName = name
				+ "[" 
				+ LocalDateTime.now().toString()
					.replace(' ', '-')
					.replace(':', '-')
					.replace('/', '-')
				+ "]";
		File img = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		img.renameTo(new File("./src/test/resources/screenshots/" + fileName + ".png"));
	}
}
