package tests;

import Driver.DriverManager;
import base.BaseTest;
import Pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import utils.ConfigReader;

import java.util.List;
import java.util.Map;

public class loginTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(loginTest.class);

    @Test(priority = 1)
    public void validLogin(){
        String userName = ConfigReader.getProperty("standardUserName");
        String password = ConfigReader.getProperty("passwordforAll");
        DriverManager driver = new DriverManager();
        LoginPage login = new LoginPage();
        login.login(userName,password);
        String currenturl = driver.getDriver().getCurrentUrl();
        org.testng.Assert.assertTrue(
                currenturl.contains("inventory.html"),
                "Login failed! Expected 'inventory.html' in URL but found: " + currenturl
        );
    }
    @Test(priority = 2)
    public void blockeduserLogin(){
        String userName = ConfigReader.getProperty("lockedOutUserName");
        String password = ConfigReader.getProperty("passwordforAll");
        LoginPage login = new LoginPage();
        login.login(userName,password);

        String errorMessage = login.errormessage();
        org.testng.Assert.assertTrue(errorMessage.contains("Sorry, this user has been locked out."),"The User is blocked");
        log.info("Log in failed for user");
    }
}
