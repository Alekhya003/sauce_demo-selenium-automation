package listeners;

import jakarta.mail.MessagingException;
import org.testng.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.*;

import java.util.*;

/**
 * TestNGListener - Listens to test execution events
 * Captures screenshots on failure, sends reports via email/WhatsApp
 */
public class TestNGListener implements ITestListener, ISuiteListener {
    private static final Logger log = LoggerFactory.getLogger(TestNGListener.class);
    
    private int passed = 0;
    private int failed = 0;
    private int skipped = 0;
    private Set<String> browsers = new HashSet<>();
    private List<String> failedTests = new ArrayList<>();
    private long suiteStartTime;
    private long suiteEndTime;
    
    @Override
    public void onStart(ISuite suite) {
        suiteStartTime = System.currentTimeMillis();
        log.info("Test Suite Started: {}", suite.getName());
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        String browser = result.getTestContext()
                .getCurrentXmlTest()
                .getParameter("browser");
        browsers.add(browser);
        log.info("Test Started: {} [Browser: {}]", result.getMethod().getMethodName(), browser);
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        passed++;
        log.info("✅ Test Passed: {}", result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        failed++;
        String testName = result.getTestClass().getRealClass().getSimpleName() + 
                         "." + result.getMethod().getMethodName();
        failedTests.add(testName);
        
        // Capture screenshot on failure
        try {
            String screenshot = ScreenshotUtil.captureScreenshot(result.getMethod().getMethodName());
            log.info("Screenshot captured for failed test: {}", screenshot);
        } catch (Exception e) {
            log.error("Failed to capture screenshot", e);
        }
        
        // Log exception details
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            log.error("❌ Test Failed: {} \nError: {}", testName, throwable.getMessage());
        } else {
            log.error("❌ Test Failed: {} (No exception details available)", testName);
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        skipped++;
        log.warn("⊘ Test Skipped: {}", result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.info("Test failed but within success percentage: {}", result.getMethod().getMethodName());
    }
    
    @Override
    public void onFinish(ISuite suite) {
        suiteEndTime = System.currentTimeMillis();
        long duration = (suiteEndTime - suiteStartTime) / 1000; // Convert to seconds
        
        int total = passed + failed + skipped;
        
        // Build comprehensive report
        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("TEST EXECUTION REPORT\n");
        report.append("═══════════════════════════════════════════════════════════════\n\n");
        report.append("Suite Name: ").append(suite.getName()).append("\n");
        report.append("Browsers: ").append(browsers).append("\n");
        report.append("Execution Time: ").append(duration).append(" seconds\n");
        report.append("Timestamp: ").append(DateAndTime.getCurrentDateTime()).append("\n\n");
        
        report.append("─────────────────────────────────────────────────────────────────\n");
        report.append("TEST RESULTS\n");
        report.append("─────────────────────────────────────────────────────────────────\n");
        report.append("Total Tests: ").append(total).append("\n");
        report.append("✅ Passed: ").append(passed).append("\n");
        report.append("❌ Failed: ").append(failed).append("\n");
        report.append("⊘ Skipped: ").append(skipped).append("\n\n");
        
        if (!failedTests.isEmpty()) {
            report.append("Failed Tests:\n");
            for (String failedTest : failedTests) {
                report.append("  - ").append(failedTest).append("\n");
            }
            report.append("\n");
        }
        
        // Calculate pass percentage
        double passPercentage = total > 0 ? (passed * 100.0 / total) : 0;
        report.append("Pass Rate: ").append(String.format("%.2f", passPercentage)).append("%\n");
        report.append("═══════════════════════════════════════════════════════════════\n");
        
        log.info("Test Suite Finished: {}", suite.getName());
        
        // Send notifications only if WhatsApp/Email are configured
        try {
            sendNotifications(report.toString(), suite.getName());
        } catch (Exception e) {
            log.error("Failed to send notifications", e);
        }
    }
    
    /**
     * Send notifications via email and WhatsApp
     */
    private void sendNotifications(String report, String suiteName) {
        try {
            // Send WhatsApp notification
            String whatsappNumber = ConfigReader.getProperty("whatsappToNumber", "");
            if (!whatsappNumber.isEmpty()) {
                WhatsAppNotifier.sendMessage(whatsappNumber, report);
            } else {
                log.debug("WhatsApp number not configured, skipping notification");
            }
        } catch (Exception e) {
            log.warn("Failed to send WhatsApp notification: {}", e.getMessage());
        }
        
        try {
            // Send Email notification
            String recipientEmail = ConfigReader.getProperty("recipientEmail", "");
            if (!recipientEmail.isEmpty()) {
                EmailUtil emailUtil = new EmailUtil();
                emailUtil.mailSend(report, recipientEmail, suiteName);
            } else {
                log.debug("Recipient email not configured, skipping email notification");
            }
        } catch (Exception e) {
            log.warn("Failed to send email notification: {}", e.getMessage());
        }
    }
}
