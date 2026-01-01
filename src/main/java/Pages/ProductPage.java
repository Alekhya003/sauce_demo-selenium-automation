package Pages;

import Driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ProductPage {
    private By inventory = By.className("inventory_item_name");
    public List<String> allproductNames(){
        ArrayList<String> productNames = new ArrayList<>();
        List<WebElement> inventoryProduct = DriverManager.getDriver().findElements(inventory);
        for (WebElement productName : inventoryProduct) {
            productNames.add(productName.getText());
        }
        return productNames;
    }
}
