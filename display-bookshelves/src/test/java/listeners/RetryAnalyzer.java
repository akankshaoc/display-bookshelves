package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry analyzer class, used here for re running any failed test cases.
 * specify parameter <code> retryAnalyzer = listeners.RetryAnalyzer.class </code> in the <code>@Test</code> annotation to use this.
 * 
 * All Tests will be retried for a maximum of 4 times
 * @author 2308990
 * @see BookshelvesTest
 */
public class RetryAnalyzer implements IRetryAnalyzer {
	private int counter = 0;
	public final int RETRY_CAP = 4;

	@Override
	public boolean retry(ITestResult result) {

		if (counter < RETRY_CAP) {
			counter++;
			return true;
		}
		return false;
	}

}
