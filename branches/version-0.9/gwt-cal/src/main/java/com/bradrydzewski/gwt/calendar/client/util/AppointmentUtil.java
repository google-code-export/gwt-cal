package com.bradrydzewski.gwt.calendar.client.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.dayview.AppointmentWidget;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 *
 * @author Brad Rydzewski
 */
public class AppointmentUtil {

    
    public static List<Date> findDatesOccupied(Date from, Date to, Date in) {
        
        return null;
    }
    
    public static boolean isWithinRange(Date from, Date to, Date in) {
        
        Long apptStartLong = in.getTime();
        
        Long rangeStartLong = from.getTime();
        Long rangeEndLong = to.getTime();
        
        if(apptStartLong >= rangeStartLong) {
            if(apptStartLong <= rangeEndLong) {
                return true;
            }
        }
        
        if(apptStartLong <= rangeStartLong) {
            if(apptStartLong >= rangeStartLong) {
                return true;
            } 
        }
        
        return false;
    }
    
    public static boolean isMultiDay(Appointment appt) {
        
        if(appt.getStart().getDate() == appt.getEnd().getDay()) {
            if(appt.getStart().getMonth() == appt.getEnd().getMonth()){
                if(appt.getStart().getYear() == appt.getEnd().getYear()){
                    return false;
                }
            }
        }
        
        return false;
    }
    
    public static ArrayList filterListByDateRange(ArrayList fullList, Date date, int days) {
        
        ArrayList<Appointment> group = new ArrayList<Appointment>();
        Date startDate = (Date)date.clone();
        resetTime(startDate);
        Date endDate = (Date)date.clone();
        resetTime(endDate);
        endDate.setDate(endDate.getDate() + days);
        
        for (int i = 0; i < fullList.size(); i++) {

            Appointment tmpAppt = (Appointment) fullList.get(i);
            if(tmpAppt.isMultiDay() && rangeContains(tmpAppt,startDate,endDate)) {
                group.add(tmpAppt);
            }
        }
        
        return group;
    }
    
    public static boolean rangeContains(Appointment appt, Date date) {
        Date rangeStart = date;
        Date rangeEnd = (Date)date.clone();
        rangeEnd.setDate(rangeEnd.getDate()+1);
        resetTime(rangeEnd);
        return rangeContains(appt, rangeStart, rangeEnd);
    }
    
    /**
     * Determines if the given appointment intersects with the provided
     * date range.
     * @param appt
     * @param rangeStart
     * @param rangeEnd
     * @return true false indicating appointment intersects with date range
     */
    public static boolean rangeContains(Appointment appt, Date rangeStart, Date rangeEnd) {
        
        Long apptStartLong = appt.getStart().getTime();
        Long apptEndLong = appt.getEnd().getTime();
        Long rangeStartLong = rangeStart.getTime();
        Long rangeEndLong = rangeEnd.getTime();
        
        if(apptStartLong >= rangeStartLong) {
            if(apptStartLong < rangeEndLong) {
                return true;
            }
        }
        
        if(apptStartLong <= rangeStartLong) {
            if(apptEndLong >= rangeStartLong) {
                return true;
            } 
        }
        
        return false;
    }
    

    /**
     * filters a list of appointments and returns only appointments with a start
     * date equal to the date provided. FYI - I hate everything about this
     * method and am pissed off I have to use it. May be able to avoid it in the
     * future
     * 
     * @param fullList
     * @param startDate
     * @return
     */
    public static ArrayList filterListByDate(List fullList, Date startDate) {

        ArrayList<Appointment> group = new ArrayList<Appointment>();
        startDate = new Date(startDate.getYear(), startDate.getMonth(),
                startDate.getDate(), 0, 0, 0);
        Date endDate = new Date(startDate.getYear(), startDate.getMonth(),
                startDate.getDate(), 0, 0, 0);
        endDate.setDate(endDate.getDate() + 1);

        for (int i = 0; i < fullList.size(); i++) {

            Appointment tmpAppt = (Appointment) fullList.get(i);

            if (tmpAppt.isMultiDay()==false && tmpAppt.getEnd().before(endDate)) {

                //TODO: probably can shorten this by using the compareTo method
                
                
                if (tmpAppt.getStart().after(startDate) || tmpAppt.getStart().equals(startDate)) {
                    group.add(tmpAppt);
                } else {
                    // System.out.println(" exiting filter at index " +i+ " ,"
                    // +group.size()+ " found");
                    // return group;
                }
            }
        }

        return group;
    }

    public static AppointmentWidget checkAppointmentElementClicked(Element element,List<AppointmentWidget> appointments) {
        for (AppointmentWidget appt : appointments) {
            //throw new RuntimeException("checkAppointmentElementClicked is not implemented");
            
            if ( DOM.isOrHasChild(appt.getElement(), element) ) {
                
                return appt;
            }
        }
        
        return null;
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

