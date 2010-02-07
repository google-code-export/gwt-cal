/*
 * This file is part of gwt-cal
 * Copyright (C) 2010  Scottsdale Software LLC
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
package com.bradrydzewski.gwt.calendar.client.monthview;

import com.bradrydzewski.gwt.calendar.client.DateUtils;

import java.util.Date;

/**
 * Contains utilities with logic required to generate the {@link
 * com.bradrydzewski.gwt.calendar.client.monthview.MonthView}.
 *
 * @author Carlos D. Morales
 */
public class MonthViewHelper {

   private static final long MILLIS_IN_A_DAY = 86400000;

   @SuppressWarnings("deprecation")
   public static Date firstDateShownInAMonthView(Date dayInMonth) {
      Date date = DateUtils.firstOfTheMonth(dayInMonth);
      /* If the 1st of the month not Sunday we need to find the prior */      
      date.setDate(date.getDate() - date.getDay());
      return date;
   }


   @SuppressWarnings("deprecation")
   public static int monthViewRequiredRows(Date dayInMonth) {
      int requiredRows = 5;

      Date firstOfTheMonthClone = (Date) dayInMonth.clone();
      firstOfTheMonthClone.setDate(1);

      Date firstDayInCalendar = firstDateShownInAMonthView(dayInMonth);

      if (firstDayInCalendar.getMonth() != firstOfTheMonthClone.getMonth()) {
         Date lastDayOfPreviousMonth = previousDay(firstOfTheMonthClone);
         int prevMonthOverlap =
            daysInPeriod(firstDayInCalendar, lastDayOfPreviousMonth);

         Date firstOfNextMonth = firstOfNextMonth(firstOfTheMonthClone);

         int daysInMonth =
            daysInPeriod(firstOfTheMonthClone, previousDay(firstOfNextMonth));

         if (prevMonthOverlap + daysInMonth > 35) {
            requiredRows = 6;
         }
      }
//        com.google.gwt.user.client.Window.alert("The first of the month " + dayInMonth + " , requiredRows " + requiredRows);
      return requiredRows;
   }

   @SuppressWarnings("deprecation")
   private static int daysInPeriod(Date start, Date end) {
      if (start.getMonth() != end.getMonth()) {
         throw new IllegalArgumentException(
            "Start and end dates must be in the same month.");
      }
      return 1 + end.getDate() - start.getDate();
   }

   @SuppressWarnings("deprecation")
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
