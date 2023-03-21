package com.example.taxibooking.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatter {

    public Date stringToDateFormatter(String dateString)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss", Locale.FRENCH);
        formatter.setTimeZone(TimeZone.getTimeZone("France/Paris"));
        Date date = new Date();
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error in date string format");
        }
        return date;
    }
}
