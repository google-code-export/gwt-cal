package com.bradrydzewski.gwt.calendar.client.monthview;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.DateUtils;

import java.util.Date;

/**
 * Describes the layout of days (single, all and multiday) within a single
 * week that is visualized in the <code>MonthView</code>. A
 * <code>WeekLayoutDescription</code> is not aware of any other thing than
 * placing an appointment <em>horizontally</em>, i.e., without considering the
 * exact week the appointment belongs to. It is the <code>MonthLayoutDescription</code>
 * responsibility to allocate the month necessary <code>weeks</code> and
 * distributing appointments over them.
 *
 * @author Carlos D. Morales
 * @see com.bradrydzewski.gwt.calendar.client.MonthView
 * @see com.bradrydzewski.gwt.calendar.client.monthview.MonthLayoutDescription
 */
public class WeekLayoutDescription {

    private AppointmentStackingManager topAppointmentsManager =
            new AppointmentStackingManager();

    private DayLayoutDescription[] days = null;

    private Date calendarFirstDay = null;

    public WeekLayoutDescription(Date calendarFirstDay) {
        this.calendarFirstDay = calendarFirstDay;
        days = new DayLayoutDescription[7];
    }

    private void assertValidDayIndex(int day) {
        if (day < 0 || day > days.length) {
            throw new IllegalArgumentException(
                    "Invalid day index (" + day + ")");
        }
    }

    private DayLayoutDescription initDay(int day) {
        assertValidDayIndex(day);
        if (days[day] == null) {
            days[day] = new DayLayoutDescription(day);
        }
        return days[day];
    }

    public boolean areThereAppointmentsOnDay(int day) {
        assertValidDayIndex(day);
        return days[day] != null || topAppointmentsManager.areThereAppointmentsOn(day);
    }

    public DayLayoutDescription getDayLayoutDescription(int day) {
        assertValidDayIndex(day);
        if (!areThereAppointmentsOnDay(day)) {
            return null;
        }
        return days[day];
    }

    public void addAppointment(Appointment appointment) {
        int dayOfWeek = dayInWeek(appointment.getStart());
        if ( appointment.isAllDay() ) {
            topAppointmentsManager.assignLayer(new AllDayLayoutDescription(dayOfWeek, appointment));
        } else {
            initDay(dayOfWeek).addAppointment(appointment);
        }
    }

    public int currentStackOrderInDay(int dayIndex) {
        return topAppointmentsManager.singleDayLowestOrder(dayIndex);
    }

    public void addMultiDayAppointment(Appointment appointment) {
        int weekStartDay = dayInWeek(appointment.getStart());
        int weekEndDay = dayInWeek(appointment.getEnd());
        //System.out.println(appointment.getTitle() + " ----- " + weekStartDay + " to " + weekEndDay);
        topAppointmentsManager.assignLayer(
                new MultiDayLayoutDescription(weekStartDay, weekEndDay,
                        appointment));
    }

    public void addMultiWeekAppointment(Appointment appointment,
                                        AppointmentWeekPresenceInMonth presenceInMonth) {
        switch (presenceInMonth) {
            case FIRST_WEEK:
                int weekStartDay = dayInWeek(appointment.getStart());
                topAppointmentsManager.assignLayer(
                        new MultiDayLayoutDescription(weekStartDay, 6,
                                appointment));
                break;
            case IN_BETWEEN:
                topAppointmentsManager.assignLayer(
                        new MultiDayLayoutDescription(0, 6, appointment));
                break;
            case LAST_WEEK:
                int weekEndDay = dayInWeek(appointment.getEnd());
                topAppointmentsManager.assignLayer(
                        new MultiDayLayoutDescription(0, weekEndDay,
                                appointment));
                break;
        }
    }

    private int dayInWeek(Date date) {
        return (int) Math
                .floor(DateUtils.differenceInDays(date, calendarFirstDay) % 7d);
    }

    public AppointmentStackingManager getTopAppointmentsManager() {
        return topAppointmentsManager;
    }
}