package tests;

import Driver.DriverManager;
import Pages.CartPage;
import Pages.LoginPage;
import Pages.ProductPage;
import base.BaseTest;
import annotations.Severity;
import annotations.Category;
import listeners.RetryAnalyzer;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.time.Duration;

/**
 * CartFunctionalityTest - Tests for shopping cart functionality
 */
public class CartFunctionalityTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(CartFunctionalityTest.class);
    
    @Test(retryAnalyzer = RetryAnalyzer.class, groups = {"regression"})
    @Severity(Severity.SeverityLevel.HIGH)
    @Category(Category.Categories.REGRESSION)
    public void cartFunctionality() {
        try {
            log.info("Test: Cart Functionality");
            
            String userName = ConfigReader.getProperty("standardUserName");
            String password = ConfigReader.getProperty("passwordforAll");
            
            // Login
            LoginPage login = new LoginPage();
            login.login(userName, password);
            log.info("Login successful");
            
            // Get product details before adding to cart
            ProductPage product = new ProductPage();
            String productName = product.getProductName();
            String productPrice = product.getProductPrice();
            log.info("Product Name: {}, Price: {}", productName, productPrice);
            
            // Add product to cart
            product.clickAddToCart();
            log.info("Product added to cart");
            
            // Wait for cart count to update
            String cartCount = product.getCartCount();
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
            wait.until(driver -> {
                try {
                    return Integer.parseInt(cartCount) > 0;
                } catch (NumberFormatException e) {
                    return false;
                }
            });
            log.info("Cart count updated to: {}", cartCount);
            
            // Navigate to cart
            product.clickCart();
            log.info("Navigated to cart");
            
            // Verify cart contents
            CartPage cart = new CartPage();
            String cartProductName = cart.getCartProductName();
            String cartProductPrice = cart.getCartProductPrice();
            log.info("Cart Product Name: {}, Price: {}", cartProductName, cartProductPrice);
            
            Assert.assertEquals(productName, cartProductName, "Product Name mismatch in cart");
            Assert.assertEquals(productPrice, cartProductPrice, "Product price mismatch in cart");
            
            log.info("✅ Cart functionality test passed");
        } catch (Exception e) {
            log.error("Cart functionality test failed", e);
            throw e;
        }
    }
}
