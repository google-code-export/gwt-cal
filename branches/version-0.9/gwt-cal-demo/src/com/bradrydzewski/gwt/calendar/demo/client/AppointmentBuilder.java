package com.bradrydzewski.gwt.calendar.demo.client;

import java.util.ArrayList;
import java.util.Date;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.Attendee;
import com.bradrydzewski.gwt.calendar.client.Attending;
import com.google.gwt.user.client.Random;

public class AppointmentBuilder {

	public static final String[] GOOGLE_STYLES = new String[] {"gwt-appointment-green","gwt-appointment-blue","gwt-appointment-lightgreen","gwt-appointment-yellow"};
	public static final String[] ICAL_STYLES = new String[] {"gwt-appointment-green","gwt-appointment-blue","gwt-appointment-red"};//,"gwt-appointment-purple","gwt-appointment-fuschia","gwt-appointment-orange"};
	protected static Integer[] hours = new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
	protected static Integer[] minutes = new Integer[] {0,15,30,45};
	protected static Integer[] durations = new Integer[] {15,30,45,60,90,120,180,240};
	protected static int appointmentsPerDay = 8;
	protected static final String[] LOCATIONS = new String[] { "Conference Room A","Conference Room B","Antarctica","Scottsdale"};
	protected static final String[] EMAIL = new String[]{ "john.smith@gmail.com","mike.anderson@gmail.com","jane.doe@gmail.com"};
	protected static final Attending[] ATTENDING = new Attending[]{Attending.Yes,Attending.No,Attending.Maybe};
	
	
	public static ArrayList<Appointment> build() {
		return build(GOOGLE_STYLES);
	}
	
	/**
	 * Generate random Appointments.
	 */
	public static ArrayList<Appointment> build(String[] styles) {
		
		ArrayList<Appointment> list = new ArrayList<Appointment>();
		

		
		
		Date tempDate = new Date();
		tempDate.setHours(0);
		tempDate.setMinutes(0);
		tempDate.setSeconds(0);
		tempDate.setDate(tempDate.getDate());//-5
		
		
		for(int day=0; day<14; day++) {
			
			for(int a=0; a<appointmentsPerDay; a++) {
				
				Date start = (Date)tempDate.clone();
				int hour = hours[Random.nextInt(hours.length)];
				int min = minutes[Random.nextInt(minutes.length)];
				int dur = durations[Random.nextInt(durations.length)];
				start.setHours(hour);
				start.setMinutes(min);

				Date end = (Date)start.clone();
				end.setMinutes(start.getMinutes() + dur);
				
				String style = styles[Random.nextInt(styles.length)];
				Appointment appt = new Appointment();
				appt.setStart(start);
				appt.setEnd(end);
				appt.setCreatedBy((EMAIL[Random.nextInt(EMAIL.length)]));
				appt.setTitle("day: " + day + " appt: " + a);
				appt.setDescription("Call: (555) 555-0441/(888) 555-7093, Passcode: 87987898 & WebEx");
//				appt.addStyleName(style);
				appt.setLocation(LOCATIONS[Random.nextInt(LOCATIONS.length)]);
				int attendees = Random.nextInt(EMAIL.length)+1;
				for(int i=0;i<attendees;i++) {
					Attendee attendee = new Attendee();
					attendee.setEmail(EMAIL[Random.nextInt(EMAIL.length)]);
					attendee.setAttending(ATTENDING[Random.nextInt(ATTENDING.length)]);
					appt.getAttendees().add(attendee);
				}
				list.add(appt);
				
			}
			
			//increment date by +1
			tempDate.setDate(tempDate.getDate()+1);
		}
		
		return list;
	}

}
