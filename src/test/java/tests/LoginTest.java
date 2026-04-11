package tests;

import Driver.DriverManager;
import base.BaseTest;
import Pages.LoginPage;
import annotations.Severity;
import annotations.Category;
import listeners.RetryAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

/**
 * LoginTest - Tests for login functionality
 * Includes valid login and locked user scenarios
 */
public class LoginTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(LoginTest.class);

    @Test(priority = 1, retryAnalyzer = RetryAnalyzer.class, groups = {"smoke", "regression"})
    @Severity(Severity.SeverityLevel.CRITICAL)
    @Category(Category.Categories.SMOKE)
    public void validLogin() {
        try {
            log.info("Test: Valid Login");
            
            String userName = ConfigReader.getProperty("standardUserName");
            String password = ConfigReader.getProperty("passwordforAll");
            
            LoginPage login = new LoginPage();
            login.login(userName, password);
            
            // Wait a moment for navigation to complete
            Thread.sleep(2000);
            
            String currentUrl = DriverManager.getDriver().getCurrentUrl();
            log.info("Current URL after login: {}", currentUrl);
            
            Assert.assertTrue(
                    currentUrl.contains("inventory.html"),
                    "Login failed! Expected 'inventory.html' in URL but found: " + currentUrl
            );
            
            log.info("Valid login test passed");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrupted", e);
        } catch (Exception e) {
            log.error("Valid login test failed", e);
            throw e;
        }
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class, groups = {"regression"})
    @Severity(Severity.SeverityLevel.HIGH)
    @Category(Category.Categories.REGRESSION)
    public void blockedUserLogin() {
        try {
            log.info("Test: Blocked User Login");
            
            String userName = ConfigReader.getProperty("lockedOutUserName");
            String password = ConfigReader.getProperty("passwordforAll");
            
            LoginPage login = new LoginPage();
            login.login(userName, password);
            
            String errorMessage = login.getErrorMessage();
            log.info("Error message: {}", errorMessage);
            
            Assert.assertFalse(errorMessage.isEmpty(), 
                    "Expected error message for locked user but got none");
            
            Assert.assertTrue(
                    errorMessage.contains("Sorry, this user has been locked out."),
                    "Expected locked out message but got: " + errorMessage
            );
            
            log.info("Blocked user login test passed");
        } catch (Exception e) {
            log.error("Blocked user login test failed", e);
            throw e;
        }
    }
}
