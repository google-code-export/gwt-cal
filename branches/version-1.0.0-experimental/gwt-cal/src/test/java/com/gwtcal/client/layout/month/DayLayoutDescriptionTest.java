/*
 * This file is part of gwt-cal
 * Copyright (C) 2010  Scottsdale Software LLC
 *
 * gwt-cal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/
 */
package com.gwtcal.client.layout.month;

import com.gwtcal.client.Appointment;
import com.gwtcal.client.AppointmentProvider;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Test cases for the logic in the {@link DayLayoutDescription} class.
 *
 * @author Carlos D. Morales
 */
public class DayLayoutDescriptionTest {

    private DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");

    private DayLayoutDescription<Appointment> dayDescription = null;
    private WeekLayoutDescription<Appointment> weekDescription = null;
    private AppointmentProvider<Appointment> appointmentProvider = null;

    @Before
    public void initDayAndWeek() throws Exception {
        appointmentProvider = new SimpleAppointmentProvider();
        dayDescription = new DayLayoutDescription<Appointment>(appointmentProvider, 0);
        weekDescription = new WeekLayoutDescription<Appointment>(appointmentProvider, dateFormatter.parse("11/01/2009"), dateFormatter.parse("12/05/2009"));
    }

    @Test
    public void weekMultidaysNotOverlappintDontAffectDayStackOrder() throws Exception {
        Appointment appointment = new Appointment();
        appointment.start = dateFormatter.parse("11/02/2009");
        appointment.end =  dateFormatter.parse("11/03/2009");
        weekDescription.addMultiDayAppointment(appointment);
        assertEquals("Start stack order for week should be 1", 1, weekDescription.currentStackOrderInDay(1));
        assertTrue("Added appointment should not be on the Day Description", dayDescription.getAppointments().isEmpty());
    }

}
