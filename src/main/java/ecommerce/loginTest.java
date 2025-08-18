package ecommerce;

import locatorHelper.locatorHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import java.util.List;
import java.util.Map;

public class loginTest {
    WebDriver driver;
    @BeforeMethod
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        // Disable Chrome's password manager, autofill & leak detection
        options.setExperimentalOption("prefs", Map.ofEntries(
                Map.entry("credentials_enable_service", false),
                Map.entry("profile.password_manager_enabled", false),
                Map.entry("autofill.profile_enabled", false),
                Map.entry("autofill.credit_card_enabled", false),
                Map.entry("password_manager_leak_detection_enabled", false)
        ));

        // Launch Chrome with a fresh profile (no saved logins or sync)
        options.addArguments("user-data-dir=C:/Temp/chrome-new-profile");
        //WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");
    }
    @Test
    public void validLogin(){
        WebElement userName = driver.findElement(locatorHelper.getBy("loginpage", "userName"));
        userName.sendKeys("standard_user");
        WebElement password = driver.findElement(locatorHelper.getBy("loginpage","password"));
        password.sendKeys("secret_sauce");
        WebElement loginButton = driver.findElement(locatorHelper.getBy("loginpage","loginButton"));
        loginButton.click();
        String currenturl = driver.getCurrentUrl();
        org.testng.Assert.assertTrue(
                currenturl.contains("inventory.html"),
                "Login failed! Expected 'inventory.html' in URL but found: " + currenturl
        );
    }
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
