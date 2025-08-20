package listeners;
import org.testng.*;
import utils.WhatsAppNotifier;

public class TestNGWhatsAppListener implements ITestListener, ISuiteListener{
    private int passed = 0, failed = 0, skipped = 0;
    public void onTestSuccess(ITestResult result) { passed++; }
    public void onTestFailure(ITestResult result) { failed++; }
    public void onTestSkipped(ITestResult result) { skipped++; }
    public void onFinish(ISuite suite) {
        String report = "📢 Today's Regression Report\n" +
                "Suite: " + suite.getName()+"\n"+
                "✅ Passed: " + passed + "\n" +
                "❌ Failed: " + failed + "\n" +
                "⚠️ Skipped: " + skipped + "\n";

        WhatsAppNotifier.sendMessage("+917980064700", report);
    }
}
