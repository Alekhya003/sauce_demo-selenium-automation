package listeners;
import jakarta.mail.MessagingException;
import org.testng.*;
import utils.ConfigReader;
import utils.EmailUtil;
import utils.WhatsAppNotifier;

import java.util.ArrayList;
import java.util.List;

public class TestNGListener implements ITestListener, ISuiteListener{
    private int passed = 0, failed = 0, skipped = 0;
    private List<String> passedTests = new ArrayList<>();
    private List<String> failedTests = new ArrayList<>();
    private List<String> skippedTests = new ArrayList<>();
    public void onTestSuccess(ITestResult result) {
        passed++;
        passedTests.add(result.getName());
    }
    public void onTestFailure(ITestResult result) {
        failed++;
        failedTests.add(result.getName());
    }
    public void onTestSkipped(ITestResult result) {
        skipped++;
        skippedTests.add(result.getName());
    }
    public void onFinish(ISuite suite)
    {
        String report = "📢 Today's Regression Report\n" +
                "Suite: " + suite.getName()+"\n"+
                "✅ Passed: " + passed + "→"+ passedTests +"\n" +
                "❌ Failed: " + failed + "→"+failedTests+"\n" +
                "⚠️ Skipped: " + skipped +"→"+skippedTests+ "\n";

        String toNumber = ConfigReader.getProperty("whatsappToNumber");
        WhatsAppNotifier.sendMessage(toNumber, report);
       // String toMail = System.getProperty("toMail","royalekhya207@gmail.com");
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.mailSend(report,"royalekhya1999@gmail.com");
    }
}
