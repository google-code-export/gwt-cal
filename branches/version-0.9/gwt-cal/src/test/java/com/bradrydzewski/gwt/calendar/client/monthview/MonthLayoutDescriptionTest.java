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

    @Test
    public void buildNovemberSampleMonthLayout() throws Exception {
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();

        // Second Week: from 11/08 to 11/14
        appointments.add(appointment("Multi Day 1", "11/08", "11/22"));
        appointments.add(appointment("Takeout...", "11/08", "11/09"));
        appointments.add(appointment("Visit the doctor", "11/08", "11/08"));
        appointments.add(appointment("Meal @ Planet Pizza", "11/09", "11/09",
                true));
        appointments.add(appointment("Walk The Dog", "11/10", "11/11", true));
        appointments.add(appointment("Dinner Party...", "11/10", "11/10"));
        appointments.add(appointment("Walk The Dog 2", "11/10", "11/11", true));
        appointments.add(appointment("Jimbob's", "11/12", "11/12", true));
        appointments.add(appointment("Meal @ Planet Pizza 2", "11/12", "11/12",
                true));
        appointments.add(appointment("Jimbob's 2", "11/13", "11/14", true));
        appointments
                .add(appointment("Meal @ Planet Pizza 3", "11/13", "11/13"));

        // Falls on both weeks!
        appointments.add(appointment("Watch Matlock", "11/14", "11/15"));

        // Third Week: from 11/15 to 11/21
        appointments.add(appointment("Visit the Doctor 2", "11/15", "11/15"));
        appointments.add(appointment("Dinner Party...2", "11/16", "11/16"));
        appointments
                .add(appointment("Meal @ Planet Pizza 4", "11/16", "11/16"));
        appointments.add(appointment("Takeout...2", "11/17", "11/17", true));
        appointments.add(appointment("Takeout...3", "11/17", "11/17"));
        appointments.add(appointment("Office Happy Hour", "11/18", "11/18"));
        appointments.add(appointment("Takeout...4", "11/18", "11/18"));
        appointments.add(appointment("Bowling Night!", "11/19", "11/19", true));
        appointments.add(appointment("Jimbob's 3", "11/19", "11/19"));
        appointments.add(appointment("Get Oil Changed", "11/20", "11/20"));
        appointments.add(appointment("Visith the Doctor 3", "11/20", "11/20"));
        appointments.add(appointment("Office Happy Hour 2", "11/21", "11/21"));
        appointments.add(appointment("Mow The Lawn", "11/21", "11/21"));

        //Week from 11/22 to 11/28
        appointments.add(appointment("Mow The Lawn", "11/22", "11/22", true));
        appointments.add(appointment("Multi Day 2", "11/22", "11/23"));

        Collections.sort(appointments, MonthView.APPOINTMENT_COMPARATOR);

        MonthLayoutDescription monthDescription =
                new MonthLayoutDescription(dateFormatter.parse("11/01/2009"),
                        appointments);
        weekDescriptions = monthDescription.getWeekDescriptions();

        // First Week
        assertNotNull(weekDescriptions);
        assertNull("First Week should get no descriptions at all",
                weekDescriptions[0]);

        // Second Week
        assertNotNull("Second Week should HAVE descriptions",
                weekDescriptions[1]);

        // 1st Day

        assertEquals("Order for 2W/1D expected to be 2", 2, weekDescriptions[1]
                .getTopAppointmentsManager().lowestLayerIndex(0));
        assertTopAppointmentTitle("Multi Day 1", 1, 0, 0, 0);
        assertTopAppointmentTitle("Takeout...", 1, 0, 1, 0);

        assertEquals("One simple appointment expected 2nd W/1st D", 1,
                weekDescriptions[1]
                        .getDayLayoutDescription(0).getTotalAppointmentCount());
        assertDayAppointmentTitle("Visit the doctor", 1, 0, 0);

        // 2nd Day
        assertEquals("Order for 2W/2D expected to be 3", 3, weekDescriptions[1]
                .getTopAppointmentsManager().lowestLayerIndex(1));
        assertTopAppointmentTitle("Multi Day 1", 1, 1, 0, 0);
        assertTopAppointmentTitle("Takeout...", 1, 1, 1, 0);
        assertTopAppointmentTitle("Meal @ Planet Pizza", 1, 1, 2, 0);

        //6th Day
        assertEquals("Order for 2W/6D expected to be 2", 2, weekDescriptions[1]
                .getTopAppointmentsManager().lowestLayerIndex(5));
        assertTopAppointmentTitle("Multi Day 1", 1, 5, 0, 0);
        assertTopAppointmentTitle("Jimbob's 2", 1, 5, 1, 3);

        //7th Day
        assertEquals("Order for 2W/7D expected to be 3", 3, weekDescriptions[1]
                .getTopAppointmentsManager().lowestLayerIndex(6));
        assertTopAppointmentTitle("Multi Day 1", 1, 6, 0, 0);
        assertTopAppointmentTitle("Watch Matlock", 1, 6, 2, 3);

        // Third Week
        assertNotNull("Third Week should HAVE descriptions at all",
                weekDescriptions[2]);

        System.out.println("==========================");
        for (AppointmentLayoutDescription weekTopStackableDescription : weekDescriptions[2]
                .getTopAppointmentsManager().getDescriptionsInLayer(0)) {
            System.out.println(weekTopStackableDescription.getAppointment().getTitle());
        }
        System.out.println("--------------------------");

        assertEquals("Order for 3W/1D expected to be 2", 2, weekDescriptions[2]
                .getTopAppointmentsManager().lowestLayerIndex(0));

        // First Day
        assertTopAppointmentTitle("Multi Day 1", 2, 0, 0, 0);
        assertTopAppointmentTitle("Watch Matlock", 2, 0, 1, 0);

        assertEquals("One simple appointment expected 3nd W/1st D", 1,
                weekDescriptions[2]
                        .getDayLayoutDescription(0).getTotalAppointmentCount());
        assertDayAppointmentTitle("Visit the Doctor 2", 2, 0, 0);

        // Fifth Day
        assertTopAppointmentTitle("Multi Day 1", 2, 4, 0, 0);
        assertTopAppointmentTitle("Bowling Night!", 2, 4, 1, 2);

        assertEquals("One simple appointment expected 3nd W/5st D", 1,
                weekDescriptions[2]
                        .getDayLayoutDescription(4).getTotalAppointmentCount());
        assertDayAppointmentTitle("Jimbob's 3", 2, 4, 0);


        // Fourth week
        assertNotNull("Fourth Week should get no descriptions at all",
                weekDescriptions[3]);

        assertTrue("Forth week should have appointments on first day",
                weekDescriptions[3].areThereAppointmentsOnDay(0));
        assertTrue("Forth week should have appointments on second day",
                weekDescriptions[3].areThereAppointmentsOnDay(1));
        assertFalse("Forth week should HAVE NOT appointments on third day",
                weekDescriptions[3].areThereAppointmentsOnDay(2));
        assertFalse("Forth week should HAVE NOT appointments on fourth day",
                weekDescriptions[3].areThereAppointmentsOnDay(3));
        assertFalse("Forth week should HAVE NOT appointments on fifth day",
                weekDescriptions[3].areThereAppointmentsOnDay(4));
        assertFalse("Forth week should HAVE NOT appointments on seventh day",
                weekDescriptions[3].areThereAppointmentsOnDay(6));

        assertNull("Fifth Week should get no descriptions at all",
                weekDescriptions[4]);
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

