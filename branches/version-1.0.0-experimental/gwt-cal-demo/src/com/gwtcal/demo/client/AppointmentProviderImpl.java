package com.gwtcal.demo.client;

import java.util.Date;

import com.gwtcal.client.AppointmentProvider;

public class AppointmentProviderImpl
	implements AppointmentProvider<Appointment> {

	@Override
	public Date getStart(Appointment value) {
		return value.getStart();
	}

	@Override
	public Date getEnd(Appointment value) {
		return value.getEnd();
	}

	@Override
	public String getTitle(Appointment value) {
		return value.getTitle();
	}

	@Override
	public String getDescription(Appointment value) {
		return value.getDescription();
	}

	@Override
	public boolean isAllDay(Appointment value) {
		return false;
	}

}
