package com.oleh.test;

import com.codeborne.selenide.Configuration;
import com.oleh.datamodel.TestEnvModel;
import com.oleh.enums.Browser;
import com.oleh.utils.JsonUtilities;
import com.oleh.utils.Utilities;
import lombok.Getter;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.nio.file.Paths;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import webdriverprovider.*;

import static com.codeborne.selenide.Selenide.open;

public class BaseTest {
    private static final String CONFIG_FILE_NAME_PROPERTY = "configFileName";
    private static final Logger LOG = LogManager.getLogger();
    private static Properties prop = Utilities.readFromPropertyFile("project.properties");
    private static final String BROWSER_VERSION_PROP = "browser.version";
    private static final String BLUEV_VERSION_PROP = "bluev.version";
    private static final String BROWSER_NAME_PROP = "browser.name";

    @Getter
    protected static TestEnvModel testEnv;

    @BeforeClass
    public static void initTestSuiteParameters() throws ConfigurationException {
        String testConfigFileName = System.getProperty(CONFIG_FILE_NAME_PROPERTY);
        LOG.info("Load config file: " + testConfigFileName);
        prop = Utilities.readFromPropertyFile("project.properties");
        if (testConfigFileName == null) {
            throw new IllegalArgumentException("Parameter 'testConfigFileName' is missed in testng config file");
        }
        Configurations configs = new Configurations();
        org.apache.commons.configuration2.Configuration config = configs.properties(new File("project.properties"));

        testEnv = JsonUtilities.parseJsonFromFile(Paths.get(config.getString("testenv.configdir"),
                testConfigFileName).toString(), TestEnvModel.class);
        if (testEnv.getPlatform() == Platform.WINDOWS) {
            String localFileDirectory = Paths.get(System.getProperty("user.dir"), testEnv.getFileDirectory())
                    .toString() + File.separator;
            testEnv.setFileDirectory(localFileDirectory);
        }
//        TestConfigurationManager.ensureTestConfigurations(testEnv);
        LOG.info("Config file initialization is finished.");
    }

    @AfterClass
    public static void writeAllureEnvFile() {
        Properties allureEnv = new Properties();
        allureEnv.setProperty("BLUE Site URL", testEnv.getAppUrl());
        allureEnv.setProperty("Browser Name", prop.getProperty("browser.name"));
        allureEnv.setProperty("Browser Version", prop.getProperty("browser.version"));
        allureEnv.setProperty("OS Name", System.getProperty("os.name"));
        allureEnv.setProperty("OS Version", System.getProperty("os.version"));
        allureEnv.setProperty("BLUE (back) version", prop.getProperty(BLUEV_VERSION_PROP));
        Utilities.writeToPropertyFile(allureEnv, Paths.get(prop.getProperty("allure.resultdir"), "environment" +
                ".properties").toString(), false);
    }

    @BeforeMethod
    public void setUpTest() throws Exception {
        setupWebDriver();
    }

    /**
     * Set up web driver environment.
     */
    private void setupWebDriver() throws Exception {
        initializeWebDriver(testEnv);
        WebDriver webDriver = WebDriverProvider.get();
        webDriver.manage().deleteAllCookies();
        LOG.info(String.format("Navigating to %s", testEnv.getAppUrl()));
        if (prop.getProperty(BROWSER_NAME_PROP) == null || prop.getProperty(BROWSER_VERSION_PROP) == null) {
            prop.setProperty(BROWSER_NAME_PROP, ((RemoteWebDriver) WebDriverProvider.get()).getCapabilities().getBrowserName());
            prop.setProperty(BROWSER_VERSION_PROP, ((RemoteWebDriver) WebDriverProvider.get()).getCapabilities().getVersion());
        }

        prop.setProperty(BLUEV_VERSION_PROP, "1.0");
//        System.setProperty("selenide.browser", "edge");
//        Configuration.browser="edge";
        open("https://www.seleniumeasy.com/test/bootstrap-date-picker-demo.html");
        System.out.println();
    }

    /**
     * Initialize WebDriver and hold it as ThreadLocal variable.
     *
     * @param testEnv test parameters from testng.xml
     */
    private WebDriver initializeWebDriver(TestEnvModel testEnv) throws Exception {
        DriverManager driverManager = getWebDriverManager(testEnv);
        driverManager.init(testEnv);
        WebDriver driver = driverManager.createDriver(testEnv, this.getClass().getName(), isHeadlessModeEnabled());
        LOG.info("Initializing webdriver session --> Thread ID: " + Thread.currentThread().getId());
        WebDriverProvider.setWebDriver(driver);
        return driver;
    }

    /**
     * Gets WebDriverManager depending on running mode.
     *
     * @param testEnv test parameters from testng.xml
     * @return WebDriverManager
     */
    private DriverManager getWebDriverManager(TestEnvModel testEnv) {
        if (testEnv.isGridEnabled()) {
            return new RemoteDriverManager();
        } else if (Browser.CHROME == testEnv.getBrowser()) {
            return new ChromeDriverManager();
        } else if (Browser.FIREFOX == testEnv.getBrowser()) {
            return new FirefoxDriverManager();
        }
        throw new UnsupportedOperationException("Browser is not supported: " + testEnv.getBrowser());
    }

    protected boolean isHeadlessModeEnabled() {
        return false;
    }
}
