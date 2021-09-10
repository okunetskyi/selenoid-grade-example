package com.oleh.page;

import static com.codeborne.selenide.Selenide.open;

public class JqueryDatePickerPage extends PageObject{

    public JqueryDatePickerPage openJqueryDatePickerPage() {
        open("https://www.seleniumeasy.com/test/jquery-date-picker-demo.html");
        return this;
    }
}
