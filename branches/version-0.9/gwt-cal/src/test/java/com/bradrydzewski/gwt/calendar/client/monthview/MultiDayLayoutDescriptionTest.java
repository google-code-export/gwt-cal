package com.bradrydzewski.gwt.calendar.client.monthview;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bradrydzewski.gwt.calendar.client.Appointment;

/**
 * Test cases for the {@link com.bradrydzewski.gwt.calendar.client.monthview.MultiDayLayoutDescription}
 * class. The only interesting logic in this class is the {@link
 * com.bradrydzewski.gwt.calendar.client.monthview.MultiDayLayoutDescription#overlapsWithRange(int,
 * int)} method.
 *
 * @author Carlos D. Morales
 */
public class MultiDayLayoutDescriptionTest {

    private AppointmentLayoutDescription multidayDescription = null;

    @Test
    public void singleDayDescriptionOverlap() {
        multidayDescription = new AppointmentLayoutDescription(0, 0, null);
        assertTrue("Overlap of single day expected to be true",
                multidayDescription.overlapsWithRange(0, 0));
    }

    @Test
    public void singleDayDescriptionWithOverlappingTwoDays() {
        multidayDescription = new AppointmentLayoutDescription(0, 0, null);
        assertTrue(
                "Overlap of description on index 0 with description 0-1 expected to be true",
                multidayDescription.overlapsWithRange(0, 1));
    }

    @Test
    public void singleDayDescriptionWithOverlappingEnclosingTotally() {
        multidayDescription = new AppointmentLayoutDescription(1, 1, null);
        assertTrue(
                "Overlap of description on index 1 with description 0-2 expected to be true",
                multidayDescription.overlapsWithRange(0, 2));
    }

    @Test
    public void twoDayDescriptionCompleteOverlap() {
        multidayDescription = new AppointmentLayoutDescription(1, 2, null);
        assertTrue(
                "Overlap of description on index 1-2 with description 1-2 expected to be true",
                multidayDescription.overlapsWithRange(1, 2));
    }

    @Test
    public void twoDayDescriptionOtherOverlappingOnLeft() {
        multidayDescription = new AppointmentLayoutDescription(1, 2, null);
        assertTrue(
                "Overlap of description on index 1-2 with description 0-1 expected to be true",
                multidayDescription.overlapsWithRange(0, 1));
    }

    @Test
    public void twoDayDescriptionOtherOverlappingOnRight() {
        multidayDescription = new AppointmentLayoutDescription(1, 2, null);
        assertTrue(
                "Overlap of description on index 1-2 with description 2-3 expected to be true",
                multidayDescription.overlapsWithRange(2, 3));
    }
}
