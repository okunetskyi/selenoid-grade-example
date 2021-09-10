package com.oleh.page;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.refresh;

/**
 * Initialise WebElements of child class of PageObject.class
 *
 * @author optimus
 */
public class PageObject {

//    protected Logger log = LogManager.getLogger(PageObject.class);
//    private WebDriver driver;

//    public PageObject(WebDriver driver) {
//        PageFactory.initElements(driver, this);
//        this.driver = driver;
//    }

    public PageObject refreshPage() {
        refresh();
        return this;
    }

//    public WebDriver getDriver() {
//        return driver;
//    }

    public PageObject setDateByName(String name, String date) {

        executeJavaScript(
                String.format("$('[name=\"%s\"]').val('%s')",
                        name, date)
        );
        return this;
    }

    public PageObject setDateByClass(String name, String date) {
        executeJavaScript(
                String.format("$('[class=\"%s\"]').val('%s')",
                        name, date)
        );
        return this;
    }
}
