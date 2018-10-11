package org.openmrs.module.msfcore.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static Date addDays(Date date, Integer nDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, nDays);
        return calendar.getTime();
    }

    public static Date parse(String dateString, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static int getDaysBetweenDates(Date date1, Date date2) {
        long diffInMillies = Math.abs(date1.getTime() - date2.getTime());
        return (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
