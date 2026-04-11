package tests;

import Pages.ProductPage;
import Pages.LoginPage;
import base.BaseTest;
import annotations.Severity;
import annotations.Category;
import listeners.RetryAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.util.*;

/**
 * CheckProductName - Tests for product listing
 */
public class CheckProductName extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(CheckProductName.class);

    @Test(retryAnalyzer = RetryAnalyzer.class, groups = {"smoke", "regression"})
    @Severity(Severity.SeverityLevel.HIGH)
    @Category(Category.Categories.SMOKE)
    public void verifyProductNames() {
        try {
            log.info("Test: Verify Product Names");
            
            String userName = ConfigReader.getProperty("standardUserName");
            String password = ConfigReader.getProperty("passwordforAll");
            
            // Login
            LoginPage login = new LoginPage();
            login.login(userName, password);
            log.info("Login successful");
            
            // Get product list
            ProductPage inventory = new ProductPage();
            ArrayList<String> productNames = new ArrayList<>(inventory.getAllProductNames());
            log.info("Retrieved {} products", productNames.size());
            
            // Expected product names
            ArrayList<String> expectedProductNames = new ArrayList<>();
            Collections.addAll(expectedProductNames,
                    "Sauce Labs Backpack",
                    "Sauce Labs Bike Light",
                    "Sauce Labs Bolt T-Shirt",
                    "Sauce Labs Fleece Jacket"
            );

            log.info("Expected products: {}", expectedProductNames);
            log.info("Found products: {}", productNames);

            // Assert all expected products are present
            Assert.assertTrue(
                    productNames.containsAll(expectedProductNames),
                    "❌ FAIL! Missing expected products.\nExpected: " + expectedProductNames + "\nFound: " + productNames
            );

            // Log additional products (not in expected list)
            for (String actual : productNames) {
                if (!expectedProductNames.contains(actual)) {
                    log.info("➕ Extra product found: {}", actual);
                }
            }
            
            log.info("✅ Product verification test passed");
        } catch (Exception e) {
            log.error("Product verification test failed", e);
            throw e;
        }
    }

}