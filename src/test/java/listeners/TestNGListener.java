package listeners;
import jakarta.mail.MessagingException;
import org.testng.*;
import utils.EmailUtil;
import utils.WhatsAppNotifier;

public class TestNGListener implements ITestListener, ISuiteListener{
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
       // String toMail = System.getProperty("toMail","royalekhya207@gmail.com");
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.mailSend(report,"royalekhya1999@gmail.com");
    }
}
