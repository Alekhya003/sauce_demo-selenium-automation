package ecommerce;

import io.github.bonigarcia.wdm.WebDriverManager;
import locatorHelper.locatorHelper;
import objectRepo.objectRepository_xml;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.*;

public class checkproductName {
    public static void main(String[] args){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.setExperimentalOption("excludeSwitches", List.of("enable-automation")); // hide "controlled by automation"
        options.setExperimentalOption("useAutomationExtension", false);                 // disable automation extension

// Disable Chrome's password manager prompts
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false
        ));
        //WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(options);
        try{
            driver.get("https://www.saucedemo.com/inventory.html");
            driver.manage().window().maximize();
            WebElement userName = driver.findElement(locatorHelper.getBy("loginpage", "userName"));
            userName.sendKeys("standard_user");
            WebElement password = driver.findElement(locatorHelper.getBy("loginpage","password"));
            password.sendKeys("secret_sauce");
            WebElement loginButton = driver.findElement(locatorHelper.getBy("loginpage","loginButton"));
            loginButton.click();
            List<WebElement> inventory = driver.findElements(locatorHelper.getBy("productPage","inventory")) ;
            ArrayList<String> productNames = new ArrayList<>();
            for(WebElement productName :inventory){
                productNames.add(productName.getText());
            }
//            System.out.println("The product names are :") ;
//            for(String Name : productNames){
//                System.out.println(Name);
//            }
            ArrayList<String> expectedproductNames = new ArrayList<>();
            Collections.addAll(expectedproductNames,
                    "Sauce Labs Backpack",
                    "Sauce Labs Bike Light",
                    "Sauce Labs Bolt T-Shirt",
                    "Sauce Labs Fleece Jacket");
            boolean allExpectedPresent = productNames.containsAll(expectedproductNames);

            if (allExpectedPresent) {
                System.out.println("✅ All expected products are present.");

                // Print extra/unexpected products
                System.out.println("🔎 Additional products (not in expected list):");
                for (String actual : productNames) {
                    if (!expectedproductNames.contains(actual)) {
                        System.out.println("➕ " + actual);
                    }
                }

            } else {
                System.out.println("❌ FAIL! Not all expected products are present.");

                // Show missing expected products
                System.out.println("❌ Missing expected products:");
                for (String expected : expectedproductNames) {
                    if (!productNames.contains(expected)) {
                        System.out.println("❌ " + expected);
                    }
                }

                // Show all actual products
                System.out.println("\n🔎 Retrieved product list:");
                for (String actual : productNames) {
                    System.out.println("✔️ " + actual);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            driver.quit();
        }

    }

}
