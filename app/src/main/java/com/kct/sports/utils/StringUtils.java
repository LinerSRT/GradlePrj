package com.kct.sports.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static boolean isEmpty(String str) {
        if (str == null || str == "" || str.trim().equals("") || str.trim().equals("null")) {
            return true;
        }
        return false;
    }

    public static String getStrDateTime(long time) {
        return SIMPLE_DATE_FORMAT.format(new Date(time));
    }
}
