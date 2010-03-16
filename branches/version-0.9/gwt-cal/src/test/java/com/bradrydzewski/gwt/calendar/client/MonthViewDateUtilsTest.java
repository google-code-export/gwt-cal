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
package com.bradrydzewski.gwt.calendar.client;

import org.junit.Test;

import com.bradrydzewski.gwt.calendar.client.monthview.MonthViewDateUtils;

import static com.bradrydzewski.gwt.calendar.client.monthview.MonthViewDateUtils.firstDateShownInAMonthView;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Test cases for the {@link com.bradrydzewski.gwt.calendar.client.monthview.MonthViewDateUtils} utilities. This test case checks
 * with the whole 2009 year months.
 * <p/>
 * Also, many of the test cases in this suite are meant to verify that the
 * appropriate <code>java.util.Date</code> cloning occurs to prevent undesired
 * effects of the date manipulations.
 *
 * @author Carlos D. Morales
 */
public class MonthViewDateUtilsTest {

   private DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");

   @Test
   public void testMonthViewRequires5Rows() throws Exception {
      assertMonthRequiredRows(5, "01/01/2009", "Jan 09");
      assertMonthRequiredRows(5, "02/01/2009", "Feb 09");
      assertMonthRequiredRows(5, "03/01/2009", "Mar 09");
      assertMonthRequiredRows(5, "04/01/2009", "Apr 09");
      assertMonthRequiredRows(5, "06/01/2009", "Jun 09");
      assertMonthRequiredRows(5, "07/01/2009", "Jul 09");
      assertMonthRequiredRows(5, "09/01/2009", "Sep 09");
      assertMonthRequiredRows(5, "10/01/2009", "Oct 09");
      assertMonthRequiredRows(5, "11/01/2009", "Nov 09");
      assertMonthRequiredRows(5, "12/01/2009", "Dec 09");
   }

   @Test
   public void firstDateShownInAMonthView_CreatesClone() throws Exception {
      Date dayInMonth = date("02/01/2010");
      Date firstDateShownInMonthView = firstDateShownInAMonthView(dayInMonth);
      assertEquals("First date in month grid", date("01/31/2010"),
                   firstDateShownInMonthView);
      assertNotSame("Date shown in a month view should be a brand new one",
                    dayInMonth, firstDateShownInMonthView);
   }

   @Test
   public void january2010() throws Exception {
      assertMonthRequiredRows(6, "01/31/2010", "Jan 10");
   }


   @Test
   public void february2010() throws Exception {
      assertMonthRequiredRows(5, "02/01/2010", "Feb 10");
   }

   @Test
   public void testMonthViewRequires6Rows_May09AndAug09() throws Exception {
      assertMonthRequiredRows(6, "05/01/2009", "May 09");
      assertMonthRequiredRows(6, "08/01/2009", "Aug 09");
   }

   private void assertMonthRequiredRows(int expectedRows, String dateString,
      String monthYear) throws Exception {
      assertEquals(String.format("%s required %d rows", monthYear,
                                 expectedRows), expectedRows,
                   MonthViewDateUtils.monthViewRequiredRows(date(dateString)));
   }

   public Date date(String dateString) throws Exception {
      return dateFormatter.parse(dateString);
   }

}
