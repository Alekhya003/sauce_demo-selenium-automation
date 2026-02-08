package Pages;

import Driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class ProductPage {
    private By inventory = By.className("inventory_item_name");
    private By saucelabsBackpackAddtocart = By.id("add-to-cart-sauce-labs-backpack");
    private By saucelabsBackpack = By.xpath("(//div[@class='inventory_item_name '])[1]");
    private By saucelabsBackpackprice = By.xpath("(//div[@class='inventory_item_price'])[1]");
    private By shoppingCart = By.className("shopping_cart_link");
    private By shoppingcartCount = By.className("shopping_cart_badge");
    public List<String> allproductNames(){
        ArrayList<String> productNames = new ArrayList<>();
        List<WebElement> inventoryProduct = DriverManager.getDriver().findElements(inventory);
        for (WebElement productName : inventoryProduct) {
            productNames.add(productName.getText());
        }
        return productNames;
    }
    public String getProductName(){
        return DriverManager.getDriver().findElement(saucelabsBackpack).getText();
    }
    public String getProductPrice(){
        return DriverManager.getDriver().findElement(saucelabsBackpackprice).getText();
    }
    public void clickAddtoCart(){
        DriverManager.getDriver().findElement(saucelabsBackpackAddtocart).click();
    }
    public String getCartCount(){
        return DriverManager.getDriver().findElement(shoppingcartCount).getText();
    }
    public void clickCart(){
        DriverManager.getDriver().findElement(shoppingCart).click();
    }
}
