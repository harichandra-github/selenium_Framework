package com.qa.opencart.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtilFunctions {
    public static String getTimeStamp() {

        return new SimpleDateFormat("dd_MMMM_yyyy_HHmmss").format(new Date());
    }
}
