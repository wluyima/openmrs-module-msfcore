package org.openmrs.module.msfcore.api.util;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class DataUtilsTest {

    @Test
    public void addDays_shouldAddDaysCorrectly() throws ParseException {
        Date date = DateUtils.addDays(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-07-30 20:29:59"), 30);
        assertThat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-08-29 20:29:59"), is(date));
    }

    @Test
    public void addDays_shouldSubstractDaysCorrectly() throws ParseException {
        Date date = DateUtils.addDays(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-07-30 20:29:59"), -30);
        assertThat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-06-30 20:29:59"), is(date));
    }

    @Test
    public void parse_shouldReturnCorrectDateWhenTheParametersAreCorrect() throws ParseException {
        Date date = DateUtils.parse("2018-07-30 20:29:59", "yyyy-MM-dd HH:mm:ss");
        assertThat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-07-30 20:29:59"), is(date));
    }

    @Test
    public void parse_shouldReturnNullWhenTheParametersAreIncorrect() {
        Date date = DateUtils.parse("123xxx", "yyyy-MM");
        assertThat(date, is(nullValue()));
    }

    @Test
    public void getDaysBetweenDates_shouldEvaluateCorrectly() throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-07-10 20:29:59");
        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-07-20 20:29:59");
        int days = DateUtils.getDaysBetweenDates(date1, date2);
        assertThat(days, is(10));
    }
}
