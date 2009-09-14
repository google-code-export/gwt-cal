package com.bradrydzewski.gwt.calendar.client.dayview;

import java.util.Date;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.AppointmentWidget;

public class AppointmentBuilder {

	public static AppointmentWidget getAppointmentWidget(AppointmentAdapter appt) {
		
    	AppointmentWidget panel = new AppointmentWidget();
    	panel.addStyleName("gwt-appointment-green");
    	panel.setWidth(appt.getWidth());
    	panel.setHeight(appt.getHeight());
    	panel.setTitle(appt.getAppointment().getTitle());
    	panel.setTop(appt.getTop());
    	panel.setLeft(appt.getLeft());
		return panel;
	}
	
	public static AppointmentAdapter[] getAppointmentAdapters(Appointment appt) {
		
		Date tmpStart = (Date) appt.getStart().clone();
		Date tmpEnd = (Date) appt.getEnd().clone();
		resetTime(tmpStart);
		resetTime(tmpEnd);
		
		//ArrayList<AppointmentAdapter> adapters = new ArrayList<AppointmentAdapters>();
		//adapters
		
//		while (!tmpStart.equals(tmpEnd)) {
//			
//			//increment date
//			tmpStart.setDate(tmpStart.getDate()+1);
//		}
		
		
		AppointmentAdapter adapter = new AppointmentAdapter(appt);
		
		
		return new AppointmentAdapter[] {adapter};
	}

    /**
     * Resets the date to have no time modifiers.
     * 
     * @param date the date
     */
    public static void resetTime(Date date) {
    	
      long msec = date.getTime();
      msec = (msec / 1000) * 1000;
      date.setTime(msec);

      date.setHours(0);
      date.setMinutes(0);
      date.setSeconds(0);
    }
}
