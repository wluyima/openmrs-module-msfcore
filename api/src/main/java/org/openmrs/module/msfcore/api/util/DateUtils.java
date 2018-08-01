package org.openmrs.module.msfcore.api.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

  public static Date getDateAtNDaysFromDate(Date date, Integer nDays) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_YEAR, -nDays);
    return calendar.getTime();
  }
}
