package webdriverprovider;


import com.oleh.datamodel.TestEnvModel;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.HashMap;

public class ChromeDriverManager implements DriverManager {

    private static final Logger LOG = LogManager.getLogger(ChromeDriverManager.class);

    @Override
    public void init(TestEnvModel env) {
        LOG.info("Starting Chrome driver.");
        WebDriverManager.chromedriver().arch32().setup();
    }

    @Override
    public WebDriver createDriver(TestEnvModel env, String testName, boolean headlessMode) {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized", "--no-sandbox", "--disable-dev-shm-usage");
            HashMap<String, Object> chromePrefs = new HashMap<>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", System.getProperty("user.dir") + File.separator + "download");
            options.setExperimentalOption("prefs", chromePrefs);
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            WebDriver driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            return driver;
        } catch (Exception e) {
            LOG.error("Unable to create Chrome driver.");
            throw e;
        }
    }
}