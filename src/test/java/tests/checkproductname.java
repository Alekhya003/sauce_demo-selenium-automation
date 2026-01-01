package tests;

import Pages.ProductPage;
import Pages.LoginPage;
import base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.*;

public class checkproductname  extends BaseTest {

    @Test
    public void verifyProductNames() {
        LoginPage login = new LoginPage();
        login.login("standard_user","secret_sauce");
        ProductPage inventory = new ProductPage();
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

}