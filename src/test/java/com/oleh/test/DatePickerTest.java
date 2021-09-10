package com.oleh.test;


import com.oleh.page.BootstrapDatePickerPage;
import com.oleh.page.JqueryDatePickerPage;
import org.testng.annotations.Test;

import java.util.*;

public class DatePickerTest extends BaseTest {

    @Test
    public void sampleTest() {
//        JqueryDatePickerPage jqueryDatePickerPage = new JqueryDatePickerPage();
//        jqueryDatePickerPage.openJqueryDatePickerPage()
//                .setDateByName("from","16.01.2009");

        BootstrapDatePickerPage page = new BootstrapDatePickerPage();
        page.openBootstrapDatePickerPage()
                .setDateByClass("form-control","16.01.2009");

    }

//    public static void main(String[] args) {
//        Set<String> strings = new TreeSet<>();
//        strings.add("c");
//        strings.add("b");
//        strings.add("a");
//        strings.forEach(System.out::println);
//        int[] ints = new int[]{1,2,3};
////        Arrays.rev(ints,Collections.reverseOrder());
////        System.out.println(Arrays.toString(ints));
//    }
}
