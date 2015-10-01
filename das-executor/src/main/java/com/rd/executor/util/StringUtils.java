package com.rd.executor.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;


public class StringUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String EMPTY_STRING = "";


    public static String convertDateToHiveDateType(Date date) {
        return formatDate(date, DATE_FORMAT);
    }

    /**
     * Convenient method to convert date to hive date time type
     * @param date
     * @return
     */
    public static String convertDateTimeToHiveDateType(Date date) {
        return formatDate(date, DATE_TIME_FORMAT);
    }

    private static String formatDate(Date date, String format) {
        if (date == null) {
            return EMPTY_STRING;
        }
        return new SimpleDateFormat(format).format(date);
    }
}
