package Pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LoginPage - Page Object for login functionality
 * Uses explicit waits and proper error handling
 */
public class LoginPage extends BasePage {
    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);
    
    // Locators
    private final By userName = By.id("user-name");
    private final By password = By.id("password");
    private final By submitButton = By.id("login-button");
    private final By errorMessage = By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3");
    
    /**
     * Perform login with given credentials
     * Waits for all elements to be ready before interaction
     */
    public void login(String user, String pass) {
        try {
            log.info("Attempting login with username: {}", user);
            sendKeysWithWait(userName, user);
            sendKeysWithWait(password, pass);
            clickWithWait(submitButton);
            log.info("Login successful");
        } catch (Exception e) {
            log.error("Login failed", e);
            throw new RuntimeException("Login failed: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get error message if login fails
     * Returns empty string if no error message is present
     */
    public String getErrorMessage() {
        try {
            if (isElementPresent(errorMessage)) {
                String message = getTextWithWait(errorMessage);
                log.warn("Error message found: {}", message);
                return message;
            }
            log.debug("No error message present");
            return "";
        } catch (Exception e) {
            log.error("Failed to retrieve error message", e);
            return "";
        }
    }
    
    /**
     * Deprecated: Use getErrorMessage() instead
     */
    @Deprecated
    public String errormessage() {
        return getErrorMessage();
    }
}
