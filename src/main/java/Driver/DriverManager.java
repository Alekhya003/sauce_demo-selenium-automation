package Driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;
import java.util.Map;

public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static void initDriver(String browser){
//        String browser = System.getProperty("browser","chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless","false"));
        switch (browser){
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-popup-blocking");
                options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
                options.setExperimentalOption("useAutomationExtension", false);

                // Disable Chrome's password manager + autofill
                options.setExperimentalOption("prefs", Map.ofEntries(
                        Map.entry("credentials_enable_service", false),
                        Map.entry("profile.password_manager_enabled", false),
                        Map.entry("autofill.profile_enabled", false),
                        Map.entry("autofill.credit_card_enabled", false),
                        Map.entry("password_manager_leak_detection_enabled", false)
                ));
                if (headless){
                    options.addArguments("--headless=new");
                    options.addArguments("--windows-size=1920,1080");
                }
                driver.set(new ChromeDriver(options));
                break;
            case "firefox":
                FirefoxOptions firefoxoption = new FirefoxOptions();
                if(headless){
                    firefoxoption.addArguments("-headless");
                }
                firefoxoption.addPreference("dom.webnotifications.enabled",false);
                firefoxoption.addPreference("media.volume_scale","0.0");
                driver.set(new FirefoxDriver(firefoxoption));
                break;
            case "edge":
                EdgeOptions edgeOption = new EdgeOptions();
                edgeOption.addArguments("--disable-notifications");
                edgeOption.addArguments("--disable-popup-blocking");
                if (headless){
                    edgeOption.addArguments("--headless=new");
                    edgeOption.addArguments("windows-size=1920,1080");
                }
                driver.set(new EdgeDriver(edgeOption));
                break;
            default:
                throw new RuntimeException("Invalid Browser : "+ browser);
        }
    }
    public static WebDriver getDriver(){
        return driver.get();
    }
    public static void quitDriver(){
        if (driver.get() != null){
            driver.get().quit();
            driver.remove();
        }
    }
}
