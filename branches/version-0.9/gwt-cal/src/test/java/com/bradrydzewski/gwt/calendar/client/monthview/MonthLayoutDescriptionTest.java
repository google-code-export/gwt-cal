package com.bradrydzewski.gwt.calendar.client.monthview;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.junit.Test;

import com.bradrydzewski.gwt.calendar.client.Appointment;

/**
 * Test cases for the {@link MonthLayoutDescription} class.
 *
 * @author Carlos D. Morales
 */
public class MonthLayoutDescriptionTest {

   private DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
   private WeekLayoutDescription[] weekDescriptions;

   private Appointment appointment(String title, String start, String end)
      throws Exception {
      return appointment(title, start, end, false);
   }

   private Appointment appointment(String title, String start, String end,
      boolean isAllDay) throws Exception {
      Appointment appointment = new Appointment();
      appointment.setTitle(title);
      appointment.setStart(dateFormatter.parse(start + "/2009"));
      appointment.setEnd(dateFormatter.parse(end + "/2009"));
      appointment.setAllDay(isAllDay);
      return appointment;
   }

   /**
    * Verifies that appointments with start and end dates not in the &quot;first
    * instant&quot; of their corresponding days are correctly laid on in the
    * month view. This particular test is for the month of February 2010 with an
    * appointment from 9 - 11 a.m. on the 13th.
    *
    * @throws Exception If an unexpected error occurs
    */
   @Test
   public void layoutTwoHourAppointment_issue28() throws Exception {

      Appointment twoHourAppointment = new Appointment();
      twoHourAppointment.setTitle("Issue 28, 2-hour");
      Date nineAM = dateFormatter.parse("02/13/2010");
      nineAM.setHours(9);
      nineAM.setMinutes(0);

      Date elevenAM = dateFormatter.parse("02/13/2010");
      elevenAM.setHours(11);
      elevenAM.setMinutes(0);

      twoHourAppointment.setStart(nineAM);
      twoHourAppointment.setEnd(elevenAM);

      ArrayList<Appointment> appointments = new ArrayList<Appointment>();
      appointments.add(twoHourAppointment);
      Collections.sort(appointments, MonthView.APPOINTMENT_COMPARATOR);

      MonthLayoutDescription monthDescription =
         new MonthLayoutDescription(dateFormatter.parse("01/31/2010"),
                                    appointments);

      weekDescriptions = monthDescription.getWeekDescriptions();

      assertNull(weekDescriptions[0]);
      assertNotNull("Appointment should be in the 2nd week.",
                    weekDescriptions[1]);
      assertNull(weekDescriptions[2]);
      assertNull(weekDescriptions[3]);
      assertNull(weekDescriptions[4]);
   }

   private void assertTopAppointmentTitle(String expectedTitle, int week,
      int day,
      int layer, int descIndex) {
      assertEquals("Top appointment for " + (week + 1) +
         "W/" + (day + 1) + "D title eror", expectedTitle,
                   weekDescriptions[week].getTopAppointmentsManager()
                      .getDescriptionsInLayer(layer).get(descIndex)
                      .getAppointment().getTitle());

   }

   private void assertDayAppointmentTitle(String expectedTitle, int week,
      int day, int apptIndex) {
      assertEquals("Title of " + (week + 1) + "W/" + (day + 1) +
         "D does not match", expectedTitle,
                   weekDescriptions[week].getDayLayoutDescription(day)
                      .getAppointments().get(apptIndex).getTitle());
   }
}

