package com.maybanktest;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.ParseException;
import java.util.Date;

class Helper {

    //All simple method that possibly being used at many part of code are being store here for easy call

    @RequiresApi(api = Build.VERSION_CODES.N)
    static String CalculateTimeLeft(String startDate, String endDate) throws ParseException {

        Date start = new SimpleDateFormat("dd MMM yyyy").parse(startDate);
        Date end = new SimpleDateFormat("dd MMM yyyy").parse(endDate);
        long diff = start.getTime() - end.getTime();
        int hours = (int) (diff / (1000 * 60 * 60));

        return hours + "";
    }

    static String getMonth(int montInNumber) {
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMM");
        calendar.set(Calendar.MONTH, montInNumber);
        return format.format(calendar.getTime());
    }
}
