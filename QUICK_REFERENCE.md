# Quick Reference Guide - Production Ready Framework

## 🚀 Quick Start

```bash
# Setup
mvn clean install

# Run all tests
mvn clean test

# Run headless (CI/CD)
mvn clean test -Dheadless=true

# View reports
mvn allure:serve
```

---

## 📌 Common Commands

### Test Execution
```bash
# Smoke tests only
mvn clean test -Dgroups=smoke

# Regression tests
mvn clean test -Dgroups=regression

# Specific test class
mvn clean test -Dtest=LoginTest

# Specific test method
mvn clean test -Dtest=LoginTest#validLogin

# Specific browser
mvn clean test -Dbrowser=chrome
```

### Logging & Debugging
```bash
# View all logs
tail -f target/logs/automation.log

# View errors only
cat target/logs/automation-error.log

# Check screenshots
ls -la target/screenshots/

# Count failed tests
grep "Test Failed" target/logs/automation.log | wc -l
```

### Reports
```bash
# Generate Allure reports
mvn allure:report

# Serve Allure reports (opens browser)
mvn allure:serve

# View test results
open target/surefire-reports/index.html
```

---

## 🔧 Configuration Quick Reference

### Application URL
```properties
# config.properties
url=https://www.saucedemo.com/
```

### Test Credentials
```properties
# Use these test users
standardUserName=standard_user
lockedOutUserName=locked_out_user
passwordforAll=secret_sauce
```

### Timeouts
```properties
# In seconds
browser.timeout=30
element.wait.timeout=10
page.load.timeout=30
```

### Environment Variables
```bash
# Email (optional)
export EMAIL_USERNAME=your-email@gmail.com
export EMAIL_PASSWORD=your-app-password

# WhatsApp (optional)
export TWILIO_ACCOUNT_SID=your-sid
export TWILIO_AUTH_TOKEN=your-token

# Browser mode
export headless=true  # or false
```

---

## 📝 Writing Tests

### Basic Test Template
```java
@Test(retryAnalyzer = RetryAnalyzer.class, groups = {"smoke"})
@Severity(Severity.SeverityLevel.HIGH)
@Category(Category.Categories.SMOKE)
public void myTest() {
    try {
        log.info("Test: My Test");
        
        // Arrange
        LoginPage login = new LoginPage();
        
        // Act
        login.login("username", "password");
        
        // Assert
        Assert.assertTrue(condition, "Error message");
        
        log.info("✅ Test passed");
    } catch (Exception e) {
        log.error("Test failed", e);
        throw e;
    }
}
```

### Page Object Template
```java
public class MyPage extends BasePage {
    private static final Logger log = LoggerFactory.getLogger(MyPage.class);
    
    private final By element = By.id("element-id");
    
    public void performAction() {
        clickWithWait(element);
        log.info("Action performed");
    }
    
    public String getElement() {
        return getTextWithWait(element);
    }
}
```

---

## 🎯 Test Annotations Reference

### Severity Levels
```java
@Severity(Severity.SeverityLevel.CRITICAL)   // Must pass
@Severity(Severity.SeverityLevel.HIGH)       // Important
@Severity(Severity.SeverityLevel.NORMAL)     // Standard
@Severity(Severity.SeverityLevel.LOW)        // Minor
@Severity(Severity.SeverityLevel.TRIVIAL)    // Cosmetic
```

### Test Categories
```java
@Category(Category.Categories.SMOKE)          // Quick tests
@Category(Category.Categories.REGRESSION)     // Full tests
@Category(Category.Categories.SANITY)         // Basic checks
@Category(Category.Categories.E2E)           // End-to-end
@Category(Category.Categories.PERFORMANCE)   // Performance
@Category(Category.Categories.SECURITY)      // Security
```

---

## 🛠️ Troubleshooting Quick Tips

| Issue | Solution |
|-------|----------|
| WebDriver not found | Run: `mvn clean install` |
| Element not found | Check locator, increase timeout in config.properties |
| Test timeout | Increase `element.wait.timeout` |
| Email not sending | Check EMAIL_USERNAME and EMAIL_PASSWORD env vars |
| WhatsApp error | Verify TWILIO_ACCOUNT_SID and TWILIO_AUTH_TOKEN |
| Parallel issues | Ensure tests are independent, no static variables |
| Screenshot not captured | Check `target/screenshots/` directory permissions |
| Logs not visible | Check `target/logs/` and logback.xml configuration |

---

## 📂 Directory Structure Quick Reference

```
target/
├── logs/
│   ├── automation.log              ← All logs
│   └── automation-error.log        ← Errors only
├── screenshots/                    ← Failed test screenshots
├── reports/                        ← HTML reports
└── surefire-reports/               ← TestNG reports

src/
├── main/java/
│   ├── Driver/                     ← WebDriver management
│   ├── Pages/                      ← Page objects
│   ├── annotations/                ← Custom annotations
│   └── utils/                      ← Utility classes
└── test/
    ├── java/
    │   ├── base/                   ← BaseTest
    │   ├── listeners/              ← Listeners
    │   └── tests/                  ← Test classes
    └── resources/testdata/         ← JSON test data
```

---

## 📊 Logging Levels

```java
log.debug("Detailed information");      // Development/debugging
log.info("General information");        // Normal flow
log.warn("Warning message");            // Potential issue
log.error("Error message", exception);  // Error occurred
```

---

## ✨ Best Practices Checklist

- [ ] Use BasePage methods for element interaction
- [ ] Always add try-catch with logging
- [ ] Use @Severity and @Category annotations
- [ ] Add retry analyzer to flaky tests
- [ ] Externalize test data to JSON
- [ ] Use ConfigReader for properties
- [ ] Log test flow with log.info()
- [ ] Log errors with log.error()
- [ ] Never hardcode waits - use BasePage methods
- [ ] Keep tests independent
- [ ] Use meaningful assertion messages
- [ ] Clean up resources in @AfterMethod
- [ ] Use environment variables for secrets
- [ ] Review logs for debugging

---

## 🔗 Useful Links

- [BasePage Wait Methods](#) - All available wait utilities
- [Page Object Examples](#) - Sample implementations
- [Configuration Guide](#) - Detailed config options
- [Test Data Format](#) - JSON test data structure
- [Logging Setup](#) - Logback configuration details

---

## 📞 Frequently Asked Questions

### Q: How to add a new test?
A: Create a new test class extending BaseTest, use page objects, add @Severity and @Category annotations.

### Q: How to run only critical tests?
A: Filter by severity in test listener or use test groups in testng.xml.

### Q: How to disable notifications?
A: Leave EMAIL_USERNAME and TWILIO_ACCOUNT_SID environment variables unset.

### Q: How to increase wait timeout?
A: Update `element.wait.timeout` in config.properties.

### Q: Where are screenshots saved?
A: `target/screenshots/` with timestamp in filename.

### Q: How to view logs in real-time?
A: Use: `tail -f target/logs/automation.log`

### Q: How to parallelize tests?
A: Configured in testng.xml with `parallel="classes" thread-count="5"`

### Q: How to add custom annotations?
A: Create new annotation class in `annotations/` package, use @Target and @Retention.

---

## 🎓 Learning Resources

1. **Selenium** - [Official Docs](https://www.selenium.dev/documentation/)
2. **TestNG** - [Official Docs](https://testng.org/doc/)
3. **WebDriverManager** - [GitHub](https://github.com/bonigarcia/webdrivermanager)
4. **Logback** - [Official Docs](http://logback.qos.ch/)
5. **Maven** - [Official Guide](https://maven.apache.org/guides/)

---

**Last Updated:** March 29, 2026  
**Framework Version:** 1.0 - Production Ready

