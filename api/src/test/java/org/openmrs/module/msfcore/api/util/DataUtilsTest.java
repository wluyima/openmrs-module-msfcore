package org.openmrs.module.msfcore.api.util;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class DataUtilsTest {

  @Test
  public void getDateAtNDaysFromData_shouldEvalueteCorrectly() throws ParseException {
    Date date = DateUtils.getDateAtNDaysFromDate(
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-07-30 20:29:59"), 30);
    assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-06-30 20:29:59"), date);
  }
}
