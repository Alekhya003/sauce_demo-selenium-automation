package Pages;

import Driver.DriverManager;
import org.openqa.selenium.By;

public class CartPage {
    private By cartProuctName = By.className("inventory_item_name");
    private By cartProductPrice = By.className("inventory_item_price");
    public String getCartProductPrice(){
        return DriverManager.getDriver().findElement(cartProductPrice).getText();
    }
    public String getCartProductName(){
        return DriverManager.getDriver().findElement(cartProuctName).getText();
    }
}
