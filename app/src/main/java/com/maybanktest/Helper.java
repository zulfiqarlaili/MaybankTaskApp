package com.maybanktest;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.util.Date;

public class Helper {

    //All simple method that possibly being used at many part of code are being store here for easy call

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String CalculateTimeLeft(String startDate , String endDate) throws ParseException {

        Date start = new SimpleDateFormat("dd MMM yyyy").parse(startDate);
        Date end = new SimpleDateFormat("dd MMM yyyy").parse(endDate);
        long diff =  start.getTime() - end.getTime();
        int hours = (int) (diff / (1000 * 60 * 60));

        return hours+"";
    }

    public static String getMonth(int montInNumber) {
        String month = "";
        if (montInNumber == 1) month = "Jan";
        if (montInNumber == 2) month = "Feb";
        if (montInNumber == 3) month = "Mac";
        if (montInNumber == 4) month = "Apr";
        if (montInNumber == 5) month = "May";
        if (montInNumber == 6) month = "Jun";
        if (montInNumber == 7) month = "July";
        if (montInNumber == 8) month = "Aug";
        if (montInNumber == 9) month = "Sep";
        if (montInNumber == 10) month = "Oct";
        if (montInNumber == 11) month = "Nov";
        if (montInNumber == 12) month = "Dec";
        return month;
    }
}
