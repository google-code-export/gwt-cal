package com.bradrydzewski.gwt.calendar.client.monthview;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Describes the layout for all appointments in all the weeks displayed in a
 * <code>MonthView</code>. This class is responsible for the distribution of the
 * appointments over the multiple weeks they possibly span.
 *
 * @author Carlos D. Morales
 */
public class MonthLayoutDescription {

    private Date calendarFirstDay = null;

    private WeekLayoutDescription[] weeks = new WeekLayoutDescription[6];

    public MonthLayoutDescription(Date calendarFirstDay,
                                  ArrayList<Appointment> appointments) {
        this.calendarFirstDay = calendarFirstDay;
        placeAppointments(appointments);
    }

    private void initWeek(int weekIndex) {
        if (weeks[weekIndex] == null) {
            weeks[weekIndex] = new WeekLayoutDescription(calendarFirstDay);
        }
    }

    private void placeAppointments(ArrayList<Appointment> appointments) {
        for (Appointment appointment : appointments) {
            
        	int startWeek =
                    calculateWeekFor(appointment.getStart(), calendarFirstDay);
            
        	//needed to put this in because if appointment appears
        	// in prior month, we get a negative number, which
        	// causes an index out of bounds exception
        	if(startWeek>=0 && startWeek < weeks.length) {
        	
//	            System.out.println(appointment.getTitle() + " ---  multi " + appointment.isMultiDayAppointment());
//	            System.out.println(appointment.getTitle() + " ---  allday " + appointment.isAllDay());
//	            System.out.println(appointment.getTitle() + " ---  start " + appointment.getStart());
//	            System.out.println(appointment.getTitle() + " ---  startweek " + startWeek);
	            initWeek(startWeek);
	            
	            if (appointment.isMultiDayAppointment() || appointment.isAllDay()) {                
	                positionMultidayAppointment(startWeek, appointment);
	            } else {
	                weeks[startWeek].addAppointment(appointment);
	            }
        	}
        }
    }

    private boolean isMultiWeekAppointment(int startWeek, int endWeek) {
        return startWeek != endWeek;
    }

    private void positionMultidayAppointment(int startWeek,
                                             Appointment appointment) {
        int endWeek = calculateWeekFor(appointment.getEnd(), calendarFirstDay);
        
        //need to account for an appointment that spans
        // multiple months
        if(endWeek >= weeks.length)
        	endWeek = weeks.length-1;
        
        initWeek(endWeek);
        if (isMultiWeekAppointment(startWeek, endWeek)) {
            distributeOverWeeks(startWeek, endWeek, appointment);
        } else {
            //System.out.println("Se agrega como multiday " + appointment.getTitle());
            weeks[startWeek].addMultiDayAppointment(appointment);
        }
    }

    private void distributeOverWeeks(int startWeek, int endWeek,
                                     Appointment appointment) {
        weeks[startWeek].addMultiWeekAppointment(appointment,
                AppointmentWeekPresenceInMonth.FIRST_WEEK);
        for (int week = startWeek + 1; week < endWeek; week++) {
            initWeek(week);
            weeks[week].addMultiWeekAppointment(appointment,
                    AppointmentWeekPresenceInMonth.IN_BETWEEN);
        }
        if (startWeek < endWeek) {
            initWeek(endWeek);
            weeks[endWeek].addMultiWeekAppointment(appointment,
                    AppointmentWeekPresenceInMonth.LAST_WEEK);
        }
    }

    private int calculateWeekFor(Date testDate, Date calendarFirstDate) {
        return (int) Math
                .floor(DateUtils.differenceInDays(testDate, calendarFirstDate) /
                        7d);
    }

    public WeekLayoutDescription[] getWeekDescriptions() {
        return weeks;
    }

}
