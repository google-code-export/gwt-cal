package com.gwtcal.client.layout.month;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Test cases to verify logic in the <code>AppointmentLayoutDescription</code>.
 *
 * @author Carlos D. Morales
 */
public class AppointmentLayoutDescriptionTest {

   private AppointmentLayoutDescription appointmentDescription = null;

   @Test
   @SuppressWarnings(value="unchecked")
   public void spansMoreThanADay_SameStartAndEndDay() {
      appointmentDescription =  new AppointmentLayoutDescription(0, 0, null);
      assertFalse(appointmentDescription.spansMoreThanADay());
   }

   @Test
   @SuppressWarnings(value="unchecked")
   public void spansMoreThanADay_DifferentStartAndEndDay() {
      appointmentDescription = new AppointmentLayoutDescription(0, 1, null);
      assertTrue(appointmentDescription.spansMoreThanADay());
   }

   @Test
   @SuppressWarnings(value="unchecked")
   public void splitSingleDayDescription() {
      appointmentDescription = new AppointmentLayoutDescription(0, 0, null);
      assertSame(appointmentDescription, appointmentDescription.split());
   }

   @Test
   @SuppressWarnings(value="unchecked")
   public void overlapsWithRange() {
       appointmentDescription = new AppointmentLayoutDescription(0, 0, null);
       assertTrue(appointmentDescription.overlapsWithRange(0,1));
       assertFalse(appointmentDescription.overlapsWithRange(1,1));
   }

    @Test
    @SuppressWarnings(value="unchecked")
    public void passedValueBecomes() {
        Integer number = Integer.valueOf("300");
        appointmentDescription = new AppointmentLayoutDescription<Integer>(0, 0, number);
        assertSame(number, appointmentDescription.getAppointment());

        Integer overriddenNumber = Integer.valueOf("400");
        appointmentDescription.setAppointment(overriddenNumber);
        assertSame(overriddenNumber, appointmentDescription.getAppointment());
    }


   @Test
   @SuppressWarnings(value="unchecked")
   public void splitTwoDayDescription() {
      appointmentDescription = new AppointmentLayoutDescription(0, 1, null);
      assertTrue(appointmentDescription.spansMoreThanADay());

      AppointmentLayoutDescription secondDay = appointmentDescription.split();

      assertFalse(appointmentDescription.spansMoreThanADay());
      assertStartEndDay(0,0, appointmentDescription);
      assertSame(appointmentDescription, appointmentDescription.split());
      assertStartEndDay(1, 1, secondDay);
   }

   private void assertStartEndDay(int start, int end, AppointmentLayoutDescription description) {
      assertEquals("Start day", start, description.getWeekStartDay());
      assertEquals("End day", end, description.getWeekEndDay());
   }

}
