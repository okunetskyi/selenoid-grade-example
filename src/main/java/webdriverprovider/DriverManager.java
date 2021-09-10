package webdriverprovider;

import com.oleh.datamodel.TestEnvModel;
import org.openqa.selenium.WebDriver;

public interface DriverManager {

    void init(TestEnvModel env);

    WebDriver createDriver(TestEnvModel env, String testName, boolean headlessMode) throws Exception;
}
