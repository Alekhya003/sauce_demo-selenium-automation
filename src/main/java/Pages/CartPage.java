package Pages;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CartPage - Page Object for shopping cart
 * Handles cart interactions with explicit waits
 */
public class CartPage extends BasePage {
    private static final Logger log = LoggerFactory.getLogger(CartPage.class);
    
    // Locators
    private final By cartProductName = By.className("inventory_item_name");
    private final By cartProductPrice = By.className("inventory_item_price");
    
    /**
     * Get product price from cart
     */
    public String getCartProductPrice() {
        try {
            String price = getTextWithWait(cartProductPrice);
            log.info("Retrieved cart product price: {}", price);
            return price;
        } catch (Exception e) {
            log.error("Failed to get cart product price", e);
            throw new RuntimeException("Failed to get cart product price: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get product name from cart
     */
    public String getCartProductName() {
        try {
            String name = getTextWithWait(cartProductName);
            log.info("Retrieved cart product name: {}", name);
            return name;
        } catch (Exception e) {
            log.error("Failed to get cart product name", e);
            throw new RuntimeException("Failed to get cart product name: " + e.getMessage(), e);
        }
    }
}
