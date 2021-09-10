package com.oleh.page;

import static com.codeborne.selenide.Selenide.open;

public class BootstrapDatePickerPage extends PageObject {

    public BootstrapDatePickerPage openBootstrapDatePickerPage() {
        open("https://www.seleniumeasy.com/test/bootstrap-date-picker-demo.html");
        return this;
    }
}
