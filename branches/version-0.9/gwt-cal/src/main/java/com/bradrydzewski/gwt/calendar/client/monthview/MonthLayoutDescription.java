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
        ArrayList<Appointment> appointments, int maxLayer) {
        this.calendarFirstDay = calendarFirstDay;
        placeAppointments(appointments, maxLayer);
    }

    public MonthLayoutDescription(Date calendarFirstDay,
        ArrayList<Appointment> appointments) {
        this(calendarFirstDay, appointments, Integer.MAX_VALUE);
    }

    private void initWeek(int weekIndex, int maxLayer) {
        if (weeks[weekIndex] == null) {
            weeks[weekIndex] = new WeekLayoutDescription(calendarFirstDay, maxLayer);
        }
    }

    private void placeAppointments(ArrayList<Appointment> appointments,
        int maxLayer) {
        for (Appointment appointment : appointments) {
            int startWeek =
                calculateWeekFor(appointment.getStart(), calendarFirstDay);

            /* Place appointments only in this month */
            if (startWeek >= 0 && startWeek < weeks.length) {
                initWeek(startWeek, maxLayer);
                if (appointment.isMultiDay() || appointment.isAllDay()) {
                    positionMultidayAppointment(startWeek, appointment, maxLayer);
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
        Appointment appointment, int maxLayer) {
        int endWeek = calculateWeekFor(appointment.getEnd(), calendarFirstDay);
        initWeek(endWeek, maxLayer);
        if (isMultiWeekAppointment(startWeek, endWeek)) {
            distributeOverWeeks(startWeek, endWeek, appointment, maxLayer);
        } else {
            weeks[startWeek].addMultiDayAppointment(appointment);
        }
    }

    private void distributeOverWeeks(int startWeek, int endWeek,
        Appointment appointment, int maxLayer) {
        weeks[startWeek].addMultiWeekAppointment(appointment,
                                                 AppointmentWidgetParts.FIRST_WEEK);
        for (int week = startWeek + 1; week < endWeek; week++) {
            initWeek(week, maxLayer);
            weeks[week].addMultiWeekAppointment(appointment,
                                                AppointmentWidgetParts.IN_BETWEEN);
        }
        if (startWeek < endWeek) {
            initWeek(endWeek, maxLayer);
            weeks[endWeek].addMultiWeekAppointment(appointment,
                                                   AppointmentWidgetParts.LAST_WEEK);
        }
    }

    private int calculateWeekFor(Date testDate, Date calendarFirstDate) {
        int endWeek = (int) Math
            .floor(DateUtils.differenceInDays(testDate, calendarFirstDate) /
                7d);
        
        return Math.min(endWeek, weeks.length - 1);
    }

    public WeekLayoutDescription[] getWeekDescriptions() {
        return weeks;
    }

}
