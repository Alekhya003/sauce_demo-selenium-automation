package Pages;

import Driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Base class for all page objects
 * Provides common wait utilities and element interaction methods
 * Eliminates code duplication and ensures consistent waits across all pages
 */
public class BasePage {
    protected static final Logger log = LoggerFactory.getLogger(BasePage.class);
    protected static final int DEFAULT_WAIT_TIME = 10;
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));
    }
    
    /**
     * Wait for element to be visible and return it
     */
    protected WebElement waitForElementToBeVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            log.error("Element not visible: {}", locator, e);
            throw new RuntimeException("Element not visible: " + locator, e);
        }
    }
    
    /**
     * Wait for element to be clickable
     */
    protected WebElement waitForElementToBeClickable(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            log.error("Element not clickable: {}", locator, e);
            throw new RuntimeException("Element not clickable: " + locator, e);
        }
    }
    
    /**
     * Wait for element to be present in DOM
     */
    protected WebElement waitForElementToBePresent(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            log.error("Element not present: {}", locator, e);
            throw new RuntimeException("Element not present: " + locator, e);
        }
    }
    
    /**
     * Send keys to an element with wait
     */
    protected void sendKeysWithWait(By locator, String text) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            element.clear();
            element.sendKeys(text);
            log.info("Sent keys to element: {}, Text: {}", locator, text);
        } catch (Exception e) {
            log.error("Failed to send keys to element: {}", locator, e);
            throw new RuntimeException("Failed to send keys: " + locator, e);
        }
    }
    
    /**
     * Click an element with wait
     */
    protected void clickWithWait(By locator) {
        try {
            WebElement element = waitForElementToBeClickable(locator);
            element.click();
            log.info("Clicked element: {}", locator);
        } catch (Exception e) {
            log.error("Failed to click element: {}", locator, e);
            throw new RuntimeException("Failed to click: " + locator, e);
        }
    }
    
    /**
     * Get text from element with wait
     */
    protected String getTextWithWait(By locator) {
        try {
            WebElement element = waitForElementToBeVisible(locator);
            String text = element.getText();
            log.info("Got text from element {}: {}", locator, text);
            return text;
        } catch (Exception e) {
            log.error("Failed to get text from element: {}", locator, e);
            throw new RuntimeException("Failed to get text: " + locator, e);
        }
    }
    
    /**
     * Wait for element to be invisible
     */
    protected boolean waitForElementToBeInvisible(By locator) {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception e) {
            log.error("Element still visible: {}", locator, e);
            return false;
        }
    }
    
    /**
     * Check if element exists without throwing exception
     */
    protected boolean isElementPresent(By locator) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            shortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            log.debug("Element not present: {}", locator);
            return false;
        }
    }
    
    /**
     * Get all elements with wait
     */
    protected List<WebElement> getElementsWithWait(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            return driver.findElements(locator);
        } catch (Exception e) {
            log.error("Failed to get elements: {}", locator, e);
            throw new RuntimeException("Failed to get elements: " + locator, e);
        }
    }
    
    /**
     * Wait for custom condition
     */
    protected void waitForCondition(String message, java.util.function.Predicate<WebDriver> condition, int timeoutInSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            customWait.until(d -> condition.test(d));
            log.info("Condition met: {}", message);
        } catch (Exception e) {
            log.error("Condition not met: {}", message, e);
            throw new RuntimeException("Condition not met: " + message, e);
        }
    }
}
