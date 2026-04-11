package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RetryAnalyzer - Implements TestNG's IRetryAnalyzer for automatic test retry
 * Retries failed tests based on configuration
 * Useful for handling intermittent/flaky tests
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private static final Logger log = LoggerFactory.getLogger(RetryAnalyzer.class);
    
    // Number of times to retry a failed test
    private static final int MAX_RETRY_COUNT = 2;
    
    // Current retry count - per instance, not static
    private int retryCount = 0;
    
    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (retryCount < MAX_RETRY_COUNT) {
                retryCount++;
                log.warn("Test {} failed. Retrying ({}/{})", 
                    result.getMethod().getMethodName(), retryCount, MAX_RETRY_COUNT);
                return true; // Retry the test
            }
        }
        return false; // Don't retry
    }
    
    /**
     * Get the number of times this test was retried
     */
    public int getRetryCount() {
        return retryCount;
    }
}
