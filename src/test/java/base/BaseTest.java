package base;

import Driver.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigReader;

/**
 * BaseTest - Base class for all test classes
 * Handles WebDriver initialization and cleanup
 * Provides common setup and teardown functionality
 */
public class BaseTest {
    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);
    
    /**
     * Setup method - runs before each test
     * Initializes WebDriver, maximizes window, navigates to base URL
     */
    @Parameters("browser")
    @BeforeMethod
    public void setup(String browser) {
        try {
            log.info("═══════════════════════════════════════════");
            log.info("Starting test with browser: {}", browser);
            log.info("═══════════════════════════════════════════");
            
            // Initialize WebDriver
            DriverManager.initDriver(browser);
            
            // Maximize browser window
            DriverManager.getDriver().manage().window().maximize();
            
            // Get base URL from config
            String url = ConfigReader.getProperty("url");
            if (url == null || url.isEmpty()) {
                throw new RuntimeException("Base URL not configured in config.properties");
            }
            
            // Navigate to application
            log.info("Navigating to: {}", url);
            DriverManager.getDriver().get(url);
            
            log.info("Browser setup completed successfully");
        } catch (Exception e) {
            log.error("Error during test setup", e);
            throw new RuntimeException("Setup failed: " + e.getMessage(), e);
        }
    }
    
    /**
     * Teardown method - runs after each test
     * Closes WebDriver and cleans up resources
     * Always runs even if test fails (alwaysRun = true)
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        try {
            log.info("═══════════════════════════════════════════");
            log.info("Test teardown started");
            
            // Quit WebDriver
            DriverManager.quitDriver();
            
            // Small delay to ensure WebDriver is fully cleaned up
            // This prevents race conditions in parallel execution
            Thread.sleep(500);
            
            log.info("Test teardown completed");
            log.info("═══════════════════════════════════════════\n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Teardown interrupted", e);
        } catch (Exception e) {
            log.error("Error during test teardown", e);
        }
    }
}
