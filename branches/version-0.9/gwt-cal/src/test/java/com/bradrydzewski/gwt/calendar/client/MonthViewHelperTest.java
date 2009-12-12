package com.bradrydzewski.gwt.calendar.client;

import org.junit.Test;

import com.bradrydzewski.gwt.calendar.client.monthview.MonthViewHelper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Test cases for the {@link MonthViewHelper} utilities. This test case checks
 * with the whole 2009 year months.
 *
 * @author Carlos D. Morales
 */
public class MonthViewHelperTest {
    private DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");

    @Test
    public void testMonthViewRequires5Rows() throws Exception {
        assertMonthRequiredRows(5, "01/01/2009", "Jan 09");
        assertMonthRequiredRows(5, "02/01/2009", "Feb 09");
        assertMonthRequiredRows(5, "03/01/2009", "Mar 09");
        assertMonthRequiredRows(5, "04/01/2009", "Apr 09");
        assertMonthRequiredRows(5, "06/01/2009", "Jun 09");
        assertMonthRequiredRows(5, "07/01/2009", "Jul 09");
        assertMonthRequiredRows(5, "09/01/2009", "Sep 09");
        assertMonthRequiredRows(5, "10/01/2009", "Oct 09");
        assertMonthRequiredRows(5, "11/01/2009", "Nov 09");
        assertMonthRequiredRows(5, "12/01/2009", "Dec 09");
    }

    @Test
    public void testMonthViewRequires6Rows_May09AndAug09() throws Exception {
        assertMonthRequiredRows(6, "05/01/2009", "May 09");
        assertMonthRequiredRows(6, "08/01/2009", "Aug 09");
    }

    @Test
    public void testFirstOfNextMonthChangeYear() throws Exception {
        Date dec012009 = dateFormatter.parse("12/01/2009");
        Date jan012010 = MonthViewHelper.firstOfNextMonth(dec012009);
        assertEquals("Day should be the first", 1, jan012010.getDate());
        assertEquals("Year should have changed", 0, jan012010.getMonth());
    }

    @Test
    public void testFirstOfNextMonthFromFebOnLeapYear() throws Exception {
        Date feb012009 = dateFormatter.parse("02/01/2009");
        Date mar012009 = MonthViewHelper.firstOfNextMonth(feb012009);
        assertEquals("Day should be the first", 1, mar012009.getDate());
        assertEquals("Month should be march", 2, mar012009.getMonth());
        assertEquals("Year should be the same", 2009 - 1900, mar012009.getYear());
    }

    private void assertMonthRequiredRows(int expectedRows, String dateString,
                                         String monthYear)
            throws Exception {
        Date date = dateFormatter.parse(dateString);
        assertEquals(String.format("%s required %d rows", monthYear,
                expectedRows), expectedRows,
                MonthViewHelper.monthViewRequiredRows(date));
    }
}
