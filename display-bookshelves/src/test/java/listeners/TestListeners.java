package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

import util.CaptureScreenShot;

public class TestListeners implements ITestListener{
	@Override
	public void onTestFailure(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		if(methodName.contains("CustomerAndRecipientTest")) {
			CaptureScreenShot captureScreenShot;
			try {
				captureScreenShot = new CaptureScreenShot();
				captureScreenShot.save(result.getMethod().getMethodName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
