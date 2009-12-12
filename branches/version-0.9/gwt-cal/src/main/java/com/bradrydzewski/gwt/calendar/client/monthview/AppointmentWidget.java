package com.bradrydzewski.gwt.calendar.client.monthview;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;

public class AppointmentWidget extends FocusPanel {

    private Appointment appointment;

    public AppointmentWidget(Appointment appointment) {
        this.appointment = appointment;
        this.add(new Label(this.appointment.getTitle()));
    }

    public Appointment getAppointment() {
        return appointment;
    }
}
