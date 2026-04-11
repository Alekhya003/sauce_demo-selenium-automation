package utils;

import Driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ScreenshotUtil - Utility for capturing and saving screenshots
 * Useful for debugging failed tests and creating test reports
 */
public class ScreenshotUtil {
    private static final Logger log = LoggerFactory.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_DIR = "target/screenshots";
    
    static {
        // Create screenshots directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(SCREENSHOT_DIR));
        } catch (IOException e) {
            log.warn("Failed to create screenshots directory", e);
        }
    }
    
    /**
     * Capture screenshot and save to file
     * Returns the file path where screenshot is saved
     */
    public static String captureScreenshot(String testName) {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver == null) {
                log.warn("WebDriver not initialized, cannot capture screenshot");
                return null;
            }
            
            // Generate filename with timestamp
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(new Date());
            String filename = testName + "_" + timestamp + ".png";
            String filepath = SCREENSHOT_DIR + File.separator + filename;
            
            // Take screenshot
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            // Copy to target location
            File destFile = new File(filepath);
            Files.copy(srcFile.toPath(), destFile.toPath());
            
            log.info("Screenshot saved: {}", filepath);
            return filepath;
        } catch (Exception e) {
            log.error("Failed to capture screenshot", e);
            return null;
        }
    }
    
    /**
     * Capture screenshot with automatic naming based on current time
     */
    public static String captureScreenshot() {
        return captureScreenshot("screenshot");
    }
    
    /**
     * Get screenshot as base64 string (useful for embedding in HTML reports)
     */
    public static String captureScreenshotAsBase64() {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver == null) {
                log.warn("WebDriver not initialized, cannot capture screenshot");
                return null;
            }
            
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            return screenshot.getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            log.error("Failed to capture screenshot as base64", e);
            return null;
        }
    }
}
