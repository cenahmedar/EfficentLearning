package com.cenah.efficentlearning.helpers;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
}
