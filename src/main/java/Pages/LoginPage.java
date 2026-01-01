package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver ;
    private By userName = By.id("user-name");
    private By passWord = By.id("password");
    private By submitButtom = By.id("login-button");
    public LoginPage(WebDriver driver){
        this.driver =  driver;
    }
    public void login(String user , String password){
        driver.findElement(userName).sendKeys(user);
        driver.findElement(passWord).sendKeys(password);
        driver.findElement(submitButtom).click() ;
    }
}
