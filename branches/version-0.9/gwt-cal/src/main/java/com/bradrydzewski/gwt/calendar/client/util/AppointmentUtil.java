package com.bradrydzewski.gwt.calendar.client.util;

import java.util.ArrayList;
import java.util.Date;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.DateUtils;

/**
 * Utility class for several operations involving time and {@link Appointment}
 * objects.
 *
 * @author Brad Rydzewski
 * @author Carlos D. Morales
 */
public class AppointmentUtil {

    /**
     * Provides a <code>null</code>-safe way to return the number of millisecons
     * on a <code>date</code>.
     *
     * @param date The date whose value in milliseconds will be returned
     * @return The number of milliseconds in <code>date</code>, <code>0</code>
     *         (zero) if <code>date</code> is <code>null</code>.
     */
    private static long safeInMillis(Date date) {
        return date != null ? date.getTime() : 0;
    }

    public static ArrayList filterListByDateRange(ArrayList<Appointment> fullList, Date date,
                                                  int days) {
        ArrayList<Appointment> group = new ArrayList<Appointment>();
        Date startDate = (Date) date.clone();
        DateUtils.resetTime(startDate);
        Date endDate = DateUtils.shiftDate(date, days);

        for (Appointment appointment : fullList) {
            if (appointment.isMultiDay() && rangeContains(appointment,
                    startDate, endDate)) {
                group.add(appointment);
            }
        }

        return group;
    }

    public static boolean rangeContains(Appointment appt, Date date) {
        Date rangeEnd = (Date) date.clone();
        rangeEnd.setDate(rangeEnd.getDate() + 1);
        DateUtils.resetTime(rangeEnd);
        return rangeContains(appt, date, rangeEnd);
    }

    /**
     * Indicates whether the specified <code>appointment</code> falls within the
     * date range defined by <code>rangeStart</code> and <code>rangeEnd</code>.
     *
     * @param appointment The appointment to test
     * @param rangeStart  The range lower limit
     * @param rangeEnd    The range upper limit
     * @return <code>true</code> if the appointment's date falls within the
     *         range, <code>false</code> otherwise.
     */
    public static boolean rangeContains(Appointment appointment,
                                        Date rangeStart, Date rangeEnd) {
        long apptStartMillis = appointment.getStart().getTime();
        long apptEndMillis = appointment.getEnd().getTime();
        long rangeStartMillis = rangeStart.getTime();
        long rangeEndMillis = rangeEnd.getTime();

        return apptStartMillis >= rangeStartMillis
                && apptStartMillis < rangeEndMillis
                || apptStartMillis <= rangeStartMillis
                && apptEndMillis >= rangeStartMillis;
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
    public static ArrayList<Appointment> filterListByDate(ArrayList<Appointment> fullList, Date startDate) {

        ArrayList<Appointment> group = new ArrayList<Appointment>();
        startDate = new Date(startDate.getYear(), startDate.getMonth(),
                startDate.getDate(), 0, 0, 0);
        Date endDate = new Date(startDate.getYear(), startDate.getMonth(),
                startDate.getDate(), 0, 0, 0);
        endDate.setDate(endDate.getDate() + 1);

        for (Appointment aFullList : fullList) {
            Appointment tmpAppt = (Appointment) aFullList;

            if (!tmpAppt.isMultiDay() && tmpAppt.getEnd()
                    .before(endDate)) {
                //TODO: probably can shorten this by using the compareTo method
                if (tmpAppt.getStart().after(startDate) || tmpAppt.getStart()
                        .equals(startDate)) {
                    group.add(tmpAppt);
                }
            }
        }

        return group;
    }

    /**
     * Resets the date to have no time modifiers (hours, minutes, seconds.)
     *
     * @param date The date to reset
     */
    public static void resetTime(Date date) {
        long msec = safeInMillis(date);
        msec = (msec / 1000) * 1000;
        date.setTime(msec);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
    }
}

