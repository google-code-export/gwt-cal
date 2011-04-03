package com.gwtcal.client.layout.month;


import com.gwtcal.client.AppointmentProvider;

import java.util.ArrayList;

/**
 * Contains the calculated layout description of all <code>Appointment</code>s
 * in single day part of a week row in a <code>MonthView</code>.
 * <p></p><strong>Note:</strong> A <code>DayLayoutDescription</code> is not
 * aware of <em>multi-day</em> <code>Appointment</code>s that might span the day
 * represented by this description.
 *
 * @author Carlos D. Morales
 */
public class DayLayoutDescription<T> {
    /**
     * The list of <em>simple</em> appointments (not multi-day, not all-day) in
     * this single day.
     */
    private ArrayList<T> appointments = new java.util.ArrayList<T>();

    private AppointmentProvider<T> appointmentProvider = null;

    /**
     * The index of the represented day in the corresponding parent week.
     */
    private int dayIndex = -1;

    public DayLayoutDescription(AppointmentProvider<T> appointmentProvider, int dayIndex) {
        this.dayIndex = dayIndex;
        this.appointmentProvider = appointmentProvider;
    }

    public ArrayList<T> getAppointments() {
        return appointments;
    }

    public int getTotalAppointmentCount() {
        return appointments.size();
    }

    public void addAppointment(T appointment) {
        if (!appointmentProvider.isMultiDay(appointment)) {
            appointments.add(appointment);
        } else {
            throw new IllegalArgumentException(
                    "Attempted to add a multiday appointment to a single day description");
        }
    }

    public int getDayIndex() {
        return dayIndex;
    }
}