package com.bradrydzewski.gwt.calendar.client.monthview;

import com.bradrydzewski.gwt.calendar.client.Appointment;

/**
 * Defines the single operation to associate a layout description to a single
 * <code>Appointment</code>.
 *
 * @author Carlos D. Morales
 */
public interface AppointmentLayoutDescription {

    /**
     * Returns the described appointment.
     *
     * @return The appointment whose this layout description represents
     */
    public Appointment getAppointment();

    /**
     * Sets the described appointment of this description.
     *
     * @param appointment The appointment described
     */
    public void setAppointment(Appointment appointment);
}
