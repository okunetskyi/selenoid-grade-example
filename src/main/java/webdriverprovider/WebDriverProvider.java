package webdriverprovider;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public final class WebDriverProvider {

    private static final Logger log = LogManager.getLogger();

    private WebDriverProvider() {
    }

    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }

    public static WebDriver get() {
        return webDriver.get();
    }

    public static void destroyAndClear() {
        WebDriver driver = WebDriverProvider.get();
        if (driver != null) {
            log.info("Closing webdriver session: " + Thread.currentThread().getId());
            try {
                driver.quit();
            } catch (Exception e) {
                log.warn("Failed to close web driver " + e.getMessage());
            }
            webDriver.remove();
        } else {
            log.warn("No webdriver found to close session: " + Thread.currentThread().getId());
        }
    }

}
