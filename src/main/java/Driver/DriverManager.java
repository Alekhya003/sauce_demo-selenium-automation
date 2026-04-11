package Driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * DriverManager - Singleton pattern for WebDriver management
 * Thread-safe driver initialization for multi-browser support
 */
public class DriverManager {
    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    /**
     * Initialize WebDriver for specified browser
     * Uses WebDriverManager for automatic driver downloads
     */
    public static void initDriver(String browser) {
        try {
            if (driver.get() != null) {
                log.warn("Driver already initialized. Quitting previous instance.");
                quitDriver();
            }
            
            boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
            log.info("Initializing {} browser (headless: {})", browser.toLowerCase(), headless);
            
            switch (browser.toLowerCase()) {
                case "chrome":
                    initChromeDriver(headless);
                    break;
                case "firefox":
                    initFirefoxDriver(headless);
                    break;
                case "edge":
                    initEdgeDriver(headless);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
            
            log.info("WebDriver initialized successfully for: {}", browser);
        } catch (Exception e) {
            log.error("Failed to initialize WebDriver", e);
            throw new RuntimeException("Failed to initialize WebDriver: " + e.getMessage(), e);
        }
    }
    
    /**
     * Initialize Chrome Driver with enhanced options
     */
    private static void initChromeDriver(boolean headless) {
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            
            // Disable notifications and popups
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            
            // Hide automation indicators
            options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
            options.setExperimentalOption("useAutomationExtension", false);
            
            // Disable Chrome's password manager + autofill
            options.setExperimentalOption("prefs", Map.ofEntries(
                    Map.entry("credentials_enable_service", false),
                    Map.entry("profile.password_manager_enabled", false),
                    Map.entry("autofill.profile_enabled", false),
                    Map.entry("autofill.credit_card_enabled", false),
                    Map.entry("password_manager_leak_detection_enabled", false)
            ));
            
            if (headless) {
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");
                log.debug("Chrome running in headless mode");
            }
            
            driver.set(new ChromeDriver(options));
        } catch (Exception e) {
            log.error("Failed to initialize Chrome driver", e);
            throw new RuntimeException("Chrome driver initialization failed: " + e.getMessage(), e);
        }
    }
    
    /**
     * Initialize Firefox Driver with enhanced options
     */
    private static void initFirefoxDriver(boolean headless) {
        try {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            
            // Disable notifications
            options.addPreference("dom.webnotifications.enabled", false);
            options.addPreference("media.volume_scale", "0.0");
            
            if (headless) {
                options.addArguments("-headless");
                log.debug("Firefox running in headless mode");
            }
            
            driver.set(new FirefoxDriver(options));
        } catch (Exception e) {
            log.error("Failed to initialize Firefox driver", e);
            throw new RuntimeException("Firefox driver initialization failed: " + e.getMessage(), e);
        }
    }
    
    /**
     * Initialize Edge Driver with enhanced options
     */
    private static void initEdgeDriver(boolean headless) {
        try {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            
            // Disable notifications and popups
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            
            if (headless) {
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");
                log.debug("Edge running in headless mode");
            }
            
            driver.set(new EdgeDriver(options));
        } catch (Exception e) {
            log.error("Failed to initialize Edge driver", e);
            throw new RuntimeException("Edge driver initialization failed: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get current WebDriver instance
     * Initializes WebDriver with default browser if not already initialized
     */
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            log.warn("WebDriver not initialized. Initializing with default browser (chrome).");
            initDriver("chrome"); // Default to chrome if not initialized
        }
        return driver.get();
    }
    
    /**
     * Quit WebDriver and clean up resources
     */
    public static void quitDriver() {
        try {
            WebDriver webDriver = driver.get();
            if (webDriver != null) {
                webDriver.quit();
                driver.remove();
                log.info("WebDriver closed successfully");
            }
        } catch (Exception e) {
            log.error("Error while closing WebDriver", e);
        }
    }
    
    /**
     * Check if driver is initialized
     */
    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }
}
