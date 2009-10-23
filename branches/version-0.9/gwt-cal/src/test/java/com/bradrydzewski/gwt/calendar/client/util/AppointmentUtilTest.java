package com.bradrydzewski.gwt.calendar.client.util;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.junit.client.GWTTestCase;

import java.util.Date;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class AppointmentUtilTest extends GWTTestCase {

    private DateTimeFormat dateFormatter = null;
    private Date rangeStart = null;
    private Date rangeEnd = null;
    private Appointment appointment = null;

    @Override
    protected void gwtSetUp() throws Exception {
        super.gwtSetUp();        
        dateFormatter = DateTimeFormat.getShortDateFormat();
        rangeStart = dateFormatter.parse("10/20/2009");
        rangeEnd = dateFormatter.parse("10/21/2009");
        appointment = new Appointment();
    }

    /**
     * Returns the name of the Calendar module.
     */
    public String getModuleName() {
        return "com.bradrydzewski.gwt.calendar.Calendar";
    }

    /**
     * Tests that an appointment completely out of the range
     * is not contained in the range.
     */
    public void testRangeContains_AppointmentOutOfRange() {
        appointment.setStart(dateFormatter.parse("10/17/2009"));
        appointment.setEnd(dateFormatter.parse("10/19/2009"));
        assertFalse(
                AppointmentUtil.rangeContains(appointment, rangeStart, rangeEnd));
    }

    /**
     * Tests that an appointment whose start and end dates &quot;overlap&quot;
     * with a specified range on the left side is considered contained in the
     * range.
     */
    public void testRangeContains_AppointmentOverlapsOnLeft() {
        appointment.setStart(dateFormatter.parse("10/19/2009"));
        appointment.setEnd(dateFormatter.parse("10/25/2009"));
        assertTrue(
                AppointmentUtil.rangeContains(appointment, rangeStart, rangeEnd));
    }

    /**
     * Tests that an appointment completely contained in the range
     * is actually considered contained.
     */
    public void testRangeContains_AppointmentContained() {
        appointment.setStart(dateFormatter.parse("10/20/2009"));
        appointment.setEnd(dateFormatter.parse("10/20/2009"));
        assertTrue(AppointmentUtil.rangeContains(appointment, rangeStart, rangeEnd));
    }

    /**
     * Tests that an appointment whose start and end date exactly
     * overlap on the range is considered contained.
     */
    public void testRangeContains_AppointmenEqualsRange() {
        appointment.setStart(dateFormatter.parse("10/20/2009"));
        appointment.setEnd(dateFormatter.parse("10/21/2009"));
        assertTrue(AppointmentUtil.rangeContains(appointment, rangeStart, rangeEnd));
    }

}
