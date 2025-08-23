package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import locatorHelper.locatorHelper;
import java.util.List;

public class loginPage {
    private WebDriver driver ;
    public loginPage(WebDriver driver){
        this.driver =  driver;
    }
    public void enterUsername(String username){
        driver.findElement(locatorHelper.getBy("loginpage", "userName")).sendKeys(username);
    }
    public void enterPassword(String password){
        driver.findElement(locatorHelper.getBy("loginpage", "password")).sendKeys(password);
    }
    public void clickSubmit(){
        driver.findElement(locatorHelper.getBy("loginpage", "loginButton")).click();
    }
}
