package com.gwtcal.client.layout.month;

import com.gwtcal.client.AppointmentProvider;
import com.gwtcal.client.Appointment;

import java.util.Date;

/**
 * Simple appointment provider for testing purposes.
 *
 * @author Carlos D. Morales
 */
public class SimpleAppointmentProvider implements AppointmentProvider<Appointment> {
    @Override
    public String getDescription(Appointment value) {
        return value.description;
    }

    @Override
    public Date getStart(Appointment value) {
        return value.start;
    }

    @Override
    public Date getEnd(Appointment value) {
        return value.end;
    }

    @Override
    public String getTitle(Appointment value) {
        return value.title;
    }

    @Override
    public boolean isAllDay(Appointment value) {
        return value.allDay;
    }

    @Override
    public boolean isMultiDay(Appointment value) {
        return value.multiDay;
    }
}
