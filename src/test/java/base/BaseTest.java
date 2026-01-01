package base;

import Driver.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class BaseTest {
    @BeforeMethod
    public void setup() {
        DriverManager.Driver();
        DriverManager.getDriver().manage().window().maximize();
        String url = System.getProperty("url","https://www.saucedemo.com/");
        DriverManager.getDriver().get(url);
    }
    @AfterMethod
    public void quite(){
        DriverManager.quitDriver();
    }
}
