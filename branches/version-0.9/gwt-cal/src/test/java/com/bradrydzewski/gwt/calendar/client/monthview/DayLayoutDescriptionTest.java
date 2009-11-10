package com.bradrydzewski.gwt.calendar.client.monthview;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.bradrydzewski.gwt.calendar.client.Appointment;

/**
 * Test cases for the logic in the {@link DayLayoutDescription} class.
 *
 * @author Carlos D. Morales
 */
public class DayLayoutDescriptionTest {
    private DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
    private DayLayoutDescription dayDescription = null;
    private WeekLayoutDescription weekDescription = null;
    private Appointment appointment = null;

    @Before
    public void initDayAndWeek() throws Exception {
        weekDescription = new WeekLayoutDescription(dateFormatter.parse("11/01/2009"));
        dayDescription = new DayLayoutDescription(0);
        appointment = new Appointment();
    }

    @Test
    public void weekMultidaysNotOverlappintDontAffectDayStackOrder() throws Exception {
        appointment.setStart(dateFormatter.parse("11/02/2009"));
        appointment.setEnd(dateFormatter.parse("11/03/2009"));
        weekDescription.addMultiDayAppointment(appointment);
        assertEquals("Start stack order for week should be 1", 1, weekDescription.currentStackOrderInDay(1));
        assertTrue("Added appointment should not be on the Day Description", dayDescription.getAppointments().isEmpty());
    }
}
