package org.openmrs.module.msfcore.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

  public static Date getDateAtNDaysFromDate(Date date, Integer nDays) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_YEAR, -nDays);
    return calendar.getTime();
  }

  public static Date parse(String dateString, String format) {
    try {
      return new SimpleDateFormat(format).parse(dateString);
    } catch (ParseException e) {
      return null;
    }
  }
}
