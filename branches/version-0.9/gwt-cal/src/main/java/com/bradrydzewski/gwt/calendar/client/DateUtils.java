package com.bradrydzewski.gwt.calendar.client;

import java.util.Date;

/**
 * Contains utility methods involving dates. This class should remain GWT-API
 * independent.
 *
 * @author Carlos D. Morales
 */
public class DateUtils {

    public static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

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

    /**
     * Returns the number of days between the passed dates.
     *
     * @param endDate   The upper limit of the date range
     * @param startDate The lower limit of the date range
     * @return The number of days between <code>endDate</code> and
     *         <code>starDate</code> (inclusive)
     */
    public static int differenceInDays(Date endDate, Date startDate) {
        return (int) Math.floor((endDate.getTime() - startDate.getTime()) /
                MILLIS_IN_A_DAY);
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

    public static boolean areOnTheSameDay(Date dateOne, Date dateTwo) {
        return dateOne.getDate() == dateTwo.getDate() &&
                dateOne.getMonth() == dateTwo.getMonth() &&
                dateOne.getYear() == dateTwo.getYear();
    }
}
