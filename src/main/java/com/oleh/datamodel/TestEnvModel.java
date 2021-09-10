package com.oleh.datamodel;


import com.oleh.enums.Browser;
import lombok.Data;
import org.openqa.selenium.Platform;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Data model for Test environment.
 */

@Data
public class TestEnvModel {
    private String appUrl;
    private Browser browser;
    private boolean gridEnabled;
    private Platform platform;
    private String selenoidUrl;
    private String browserVersion;
    private boolean enableKnownIssue;
    private String dbURL;
    private String dbName;
    private String dbUser;
    private String dbPw;
    private String fileDirectory;
    private String connectApiURL;

    public String getHubUrl() {
        return selenoidUrl + "/wd/hub";
    }

    public String getDownloadUrl() {
        return selenoidUrl + "/download";
    }

    public String getDefaultDownloadDirectory() {
        if (!isGridEnabled()) {
            return System.getProperty("user.dir") + File.separator + "download";
        } else {
            return "/home/selenium/Downloads";
        }
    }

    public String getHostName() {
        try {
            return new URI(appUrl).getHost();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
