package com.bradrydzewski.gwt.calendar.client;

import java.util.List;

public interface HasAppointments {

	void removeAppointment(Appointment appointment);
	void removeAppointment(Appointment appointment, boolean fireEvents);
	void addAppointment(Appointment appointment);
	void addAppointments(List<Appointment> appointments);
	void clearAppointments();
	Appointment getSelectedAppointment();
	void setSelectedAppointment(Appointment appointment);
	void setSelectedAppointment(Appointment appointment,
		      boolean fireEvents);
	boolean hasAppointmentSelected();
}
