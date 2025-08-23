package tests;

import locatorHelper.locatorHelper;
import Pages.loginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
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
        // Login
//        WebElement userName = driver.findElement(locatorHelper.getBy("loginpage", "userName"));
//        userName.sendKeys("standard_user");
//        WebElement password = driver.findElement(locatorHelper.getBy("loginpage", "password"));
//        password.sendKeys("secret_sauce");
//        WebElement loginButton = driver.findElement(locatorHelper.getBy("loginpage", "loginButton"));
//        loginButton.click();
        loginPage login = new loginPage(driver);
        login.enterUsername("standard_user");
        login.enterPassword("secret_sauce");
        login.clickSubmit();

        // Get product names
        List<WebElement> inventory = driver.findElements(locatorHelper.getBy("productPage", "inventory"));
        ArrayList<String> productNames = new ArrayList<>();
        for (WebElement productName : inventory) {
            productNames.add(productName.getText());
        }

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