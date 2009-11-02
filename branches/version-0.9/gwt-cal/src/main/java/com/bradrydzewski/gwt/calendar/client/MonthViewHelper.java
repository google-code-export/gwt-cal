package com.bradrydzewski.gwt.calendar.client;

import java.util.Date;

/**
 * Contains utilities with logic required to generate the {@link
 * com.bradrydzewski.gwt.calendar.client.MonthView}.
 *
 * @author Carlos D. Morales
 */
public class MonthViewHelper {

    private static final long MILLIS_IN_A_DAY = 86400000;

    public static int monthViewRequiredRows(Date firstOfTheMonth) {
        int requiredRows = 5;
        Date firstOfTheMonthClone = (Date)firstOfTheMonth.clone();
        firstOfTheMonthClone.setDate(1);
        Date firstDayInCalendar = (Date) firstOfTheMonthClone.clone();
        firstDayInCalendar.setDate(firstOfTheMonthClone.getDate() - firstOfTheMonthClone.getDay());

        if ( firstDayInCalendar.getMonth() != firstOfTheMonthClone.getMonth()){
            Date lastDayOfPreviousMonth = previousDay(firstOfTheMonthClone);
            int prevMonthOverlap = daysInPeriod(firstDayInCalendar, lastDayOfPreviousMonth);

            Date firstOfNextMonth = firstOfNextMonth(firstOfTheMonthClone);

            int daysInMonth = daysInPeriod(firstOfTheMonthClone, previousDay(firstOfNextMonth));

            if ( prevMonthOverlap + daysInMonth > 35 ) {
                requiredRows = 6;
            }
        }

        return requiredRows;
    }

    private static int daysInPeriod(Date start, Date end) {
        if (start.getMonth() != end.getMonth()) {
            throw new IllegalArgumentException("Start and end dates must be in the same month.");
        }
        return 1 + end.getDate() - start.getDate();
    }

    public static Date firstOfNextMonth(Date date) {
        Date firstOfNextMonth = null;
        if (date != null) {
            int year = date.getMonth() == 11 ? date.getYear() + 1 : date.getYear();
            firstOfNextMonth = new Date(year, date.getMonth() + 1 % 11, 1);
        }
        return firstOfNextMonth;
    }

    public static Date previousDay(Date date) {
        return new Date(date.getTime() - MILLIS_IN_A_DAY);
    }
}
