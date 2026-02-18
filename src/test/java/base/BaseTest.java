package base;

import Driver.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import utils.ConfigReader;


public class BaseTest {
    @Parameters("browser")
    @BeforeMethod
    public void setup(String browser) {
        DriverManager.initDriver(browser);
        DriverManager.getDriver().manage().window().maximize();
        String url = ConfigReader.getProperty("url");
        DriverManager.getDriver().get(url);
    }
    @AfterMethod(alwaysRun = true)
    public void quit(){
        DriverManager.quitDriver();
    }
}
