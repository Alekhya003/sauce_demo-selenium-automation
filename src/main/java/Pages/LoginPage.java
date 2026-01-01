package Pages;

import Driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private By userName = By.id("user-name");
    private By passWord = By.id("password");
    private By submitButtom = By.id("login-button");
    private By errorMessage = By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3");
    public void login(String user , String password){
        DriverManager.getDriver().findElement(userName).sendKeys(user);
        DriverManager.getDriver().findElement(passWord).sendKeys(password);
        DriverManager.getDriver().findElement(submitButtom).click() ;
    }
    public String errormessage(){
        WebElement error = DriverManager.getDriver().findElement(errorMessage);
        String message = error.getText();
        return message;
    }
}
