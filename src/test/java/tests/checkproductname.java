package tests;

import Pages.ProductPage;
import Pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.*;

public class checkproductname {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
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

        //WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        //driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @Test
    public void verifyProductNames() {
        LoginPage login = new LoginPage(driver);
        login.login("standard_user","secret_sauce");
        ProductPage inventory = new ProductPage(driver);
        ArrayList<String> productNames = new ArrayList<>(inventory.allproductNames());
        // Expected product names
        ArrayList<String> expectedProductNames = new ArrayList<>();
        Collections.addAll(expectedProductNames,
                "Sauce Labs Backpack",
                "Sauce Labs Bike Light",
                "Sauce Labs Bolt T-Shirt",
                "Sauce Labs Fleece Jacket"
        );

        // Assert all expected products are present
        Assert.assertTrue(
                productNames.containsAll(expectedProductNames),
                "❌ FAIL! Missing expected products.\nExpected: " + expectedProductNames + "\nFound: " + productNames
        );

        // Print additional products (not in expected list)
        for (String actual : productNames) {
            if (!expectedProductNames.contains(actual)) {
                System.out.println("➕ Extra product found: " + actual);
            }
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}