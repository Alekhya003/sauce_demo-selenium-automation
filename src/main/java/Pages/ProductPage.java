package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * ProductPage - Page Object for product listing and selection
 * Handles product interactions with explicit waits
 */
public class ProductPage extends BasePage {
    private static final Logger log = LoggerFactory.getLogger(ProductPage.class);
    
    // Locators
    private final By inventoryItems = By.className("inventory_item_name");
    private final By addToCartButton = By.id("add-to-cart-sauce-labs-backpack");
    private final By firstProductName = By.xpath("(//div[@class='inventory_item_name '])[1]");
    private final By firstProductPrice = By.xpath("(//div[@class='inventory_item_price'])[1]");
    private final By shoppingCart = By.className("shopping_cart_link");
    private final By shoppingCartBadge = By.className("shopping_cart_badge");
    
    /**
     * Get all product names from inventory
     */
    public List<String> getAllProductNames() {
        try {
            ArrayList<String> productNames = new ArrayList<>();
            List<WebElement> products = getElementsWithWait(inventoryItems);
            for (WebElement product : products) {
                productNames.add(product.getText());
            }
            log.info("Retrieved {} product names", productNames.size());
            return productNames;
        } catch (Exception e) {
            log.error("Failed to get product names", e);
            throw new RuntimeException("Failed to get product names: " + e.getMessage(), e);
        }
    }
    
    /**
     * Deprecated: Use getAllProductNames() instead
     */
    @Deprecated
    public List<String> allproductNames() {
        return getAllProductNames();
    }
    
    /**
     * Get first product name
     */
    public String getProductName() {
        try {
            String name = getTextWithWait(firstProductName);
            log.info("Retrieved product name: {}", name);
            return name;
        } catch (Exception e) {
            log.error("Failed to get product name", e);
            throw new RuntimeException("Failed to get product name: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get first product price
     */
    public String getProductPrice() {
        try {
            String price = getTextWithWait(firstProductPrice);
            log.info("Retrieved product price: {}", price);
            return price;
        } catch (Exception e) {
            log.error("Failed to get product price", e);
            throw new RuntimeException("Failed to get product price: " + e.getMessage(), e);
        }
    }
    
    /**
     * Click add to cart button
     */
    public void clickAddToCart() {
        try {
            clickWithWait(addToCartButton);
            log.info("Clicked add to cart button");
        } catch (Exception e) {
            log.error("Failed to click add to cart button", e);
            throw new RuntimeException("Failed to click add to cart: " + e.getMessage(), e);
        }
    }
    
    /**
     * Deprecated: Use clickAddToCart() instead
     */
    @Deprecated
    public void clickAddtoCart() {
        clickAddToCart();
    }
    
    /**
     * Get shopping cart item count
     */
    public String getCartCount() {
        try {
            String count = getTextWithWait(shoppingCartBadge);
            log.info("Cart item count: {}", count);
            return count;
        } catch (Exception e) {
            log.debug("Failed to get cart count (badge may not be visible if empty)", e);
            return "0";
        }
    }
    
    /**
     * Click on shopping cart
     */
    public void clickCart() {
        try {
            clickWithWait(shoppingCart);
            log.info("Clicked shopping cart");
        } catch (Exception e) {
            log.error("Failed to click shopping cart", e);
            throw new RuntimeException("Failed to click shopping cart: " + e.getMessage(), e);
        }
    }
}
