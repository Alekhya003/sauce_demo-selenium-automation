package tests;

import Driver.DriverManager;
import Pages.CartPage;
import Pages.LoginPage;
import Pages.ProductPage;
import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ConfigReader;

import java.time.Duration;

public class CartFunctionalityTest extends BaseTest {
    String userName = ConfigReader.getProperty("standardUserName");
    String password = ConfigReader.getProperty("passwordforAll");
    @Test
    public void CartFunctionality(){
        LoginPage login = new LoginPage();
        login.login(userName,password);
        ProductPage product = new ProductPage();
        String productName = product.getProductName();
        String productPrice = product.getProductPrice();
        product.clickAddtoCart();
        String cartCount = product.getCartCount();
        //Explicit wait is used to wait until the cart count is greater than 0
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
        wait.until(driver -> {
            return Integer.parseInt(cartCount)>0;
        });
        product.clickCart();
        CartPage cart = new CartPage();
        String cartProductName = cart.getCartProductName();
        String cartProductPrice = cart.getCartProductPrice();
        Assert.assertEquals(productName,cartProductName,"Product Name is not matching.");
        Assert.assertEquals(productPrice,cartProductPrice,"Product price is not matching.");
    }
}
