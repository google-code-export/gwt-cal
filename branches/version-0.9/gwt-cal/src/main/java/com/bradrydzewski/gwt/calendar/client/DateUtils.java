/*
 * This file is part of gwt-cal
 * Copyright (C) 2009  Scottsdale Software LLC
 * 
 * gwt-cal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/
 */

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
     * Moves a date <code>shift</code> days. A clone of <code>date</code> to
     * prevent undesired object modifications.
     *
     * @param date  The date to shift
     * @param shift The number of days to push the original <code>date</code>
     *              <em>forward</em>
     * @return A <em>new</em> date pushed <code>shift</code> days forward
     */
    public static Date shiftDate(Date date, int shift) {
        Date result = (Date) date.clone();
        result.setDate(date.getDate() + shift);
        return result;
    }

    /**
     * Resets the date to have no time modifiers (hours, minutes, seconds.)
     *
     * @param date The date to reset
     */
    @SuppressWarnings("deprecation")
    public static void resetTime(Date date) {
        long milliseconds = safeInMillis(date);
        milliseconds = (milliseconds / 1000) * 1000;
        date.setTime(milliseconds);
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
    }

    @SuppressWarnings("deprecation")
    public static boolean areOnTheSameDay(Date dateOne, Date dateTwo) {
        return dateOne.getDate() == dateTwo.getDate() &&
            dateOne.getMonth() == dateTwo.getMonth() &&
            dateOne.getYear() == dateTwo.getYear();
    }
}
