package webdriverprovider;

import com.oleh.datamodel.TestEnvModel;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class FirefoxDriverManager implements DriverManager {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public void init(TestEnvModel env) {
        LOG.info("Init Firefox Driver Manager");
        WebDriverManager.firefoxdriver().arch32().setup();
    }

    @Override
    public WebDriver createDriver(TestEnvModel env, String testName, boolean headlessMode) {
        final FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("security.sandbox.content.level", 5);
        FirefoxOptions options = new FirefoxOptions().setProfile(profile);
        try {
            return new FirefoxDriver(options);
        } catch (Exception e) {
            LOG.error("Unable to init FirefoxDriver: " + e.getMessage());
            throw e;
        }
    }
}
