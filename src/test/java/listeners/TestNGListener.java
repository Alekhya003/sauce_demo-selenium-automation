package listeners;
import jakarta.mail.MessagingException;
import org.testng.*;
import utils.ConfigReader;
import utils.DateAndTime;
import utils.EmailUtil;
import utils.WhatsAppNotifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestNGListener implements ITestListener, ISuiteListener{
    private int passed = 0, failed = 0, skipped = 0;
    private Set<String> browsers = new HashSet<>();
    @Override
    public void onTestStart(ITestResult result) {
        String browser = result.getTestContext()
                .getCurrentXmlTest()
                .getParameter("browser");
        browsers.add(browser);
    }
    public void onTestSuccess(ITestResult result) {
        passed++;
    }
    public void onTestFailure(ITestResult result) {
        failed++;
    }
    public void onTestSkipped(ITestResult result) {
        skipped++;
    }
    public void onFinish(ISuite suite)
    {
        int total = passed + failed + skipped ;
        String report =
                "Suite : " + suite.getName() + "\n" +
                "Browser : " + browsers + "\n\n" +
                "Total Test : " + total + "\n" +
                "Passed: " + passed +"\n" +
                "Failed: " + failed +"\n" +
                "Skipped: " + skipped + "\n";

        String toNumber = ConfigReader.getProperty("whatsappToNumber");
        WhatsAppNotifier.sendMessage(toNumber, report);
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.mailSend(report,"royalekhya1999@gmail.com",suite.getName());
    }
}
