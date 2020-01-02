package com.cenah.efficentlearning.helpers;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {


    @SuppressLint("SimpleDateFormat")
    public static String dateToString(String dtStart) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatOut = new SimpleDateFormat("yyy-MM-dd HH:mm");
        try {
            return formatOut.format(format.parse(dtStart));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressLint("SimpleDateFormat")
    public static String calendarToString(Calendar calendar) {
        DateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm");
        return sdf.format(calendar.getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static String calendarToJsonString(Calendar calendar) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return sdf.format(calendar.getTime());
    }

}
