package org.openmrs.module.msfcore.summary;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.msfcore.summary.Age;

public class AgeTest {
    private SimpleDateFormat dateFormat;

    @Before
    public void init() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    public void getAge_days() {
        assertEquals("1 day", new Age(new GregorianCalendar(2018, 10, 9).getTime(), new GregorianCalendar(2018, 10, 10).getTime(),
                        dateFormat).getAge());
        assertEquals("10 days", new Age(new GregorianCalendar(2018, 10, 1).getTime(), new GregorianCalendar(2018, 10, 11).getTime(),
                        dateFormat).getAge());
    }

    @Test
    public void getAge_months() {
        assertEquals("1 month", new Age(new GregorianCalendar(2018, 9, 8).getTime(), new GregorianCalendar(2018, 10, 10).getTime(),
                        dateFormat).getAge());
        assertEquals("5 months", new Age(new GregorianCalendar(2018, 01, 8).getTime(), new GregorianCalendar(2018, 06, 10).getTime(),
                        dateFormat).getAge());
    }

    @Test
    public void getAge_years() {
        assertEquals("1 year", new Age(new GregorianCalendar(2017, 01, 8).getTime(), new GregorianCalendar(2018, 06, 10).getTime(),
                        dateFormat).getAge());
        assertEquals("26 years", new Age(new GregorianCalendar(1991, 12, 8).getTime(), new GregorianCalendar(2018, 06, 10).getTime(),
                        dateFormat).getAge());
    }

    @Test
    public void getFormattedBirthDate() {
        // month is zero based
        assertEquals("2018-01-01", new Age(new GregorianCalendar(2018, 00, 01).getTime(), new GregorianCalendar(2018, 06, 10).getTime(),
                        dateFormat).getFormattedBirthDate());
    }
}
