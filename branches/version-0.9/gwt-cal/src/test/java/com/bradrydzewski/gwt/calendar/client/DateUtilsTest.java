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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.bradrydzewski.gwt.calendar.client.DateUtils.firstOfTheMonth;
import static com.bradrydzewski.gwt.calendar.client.DateUtils.moveOneDayForward;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Test cases for the set of utilities to work with <code>java.util.Date</code>
 * objects.
 *
 * @author Carlos D. Morales
 */
public class DateUtilsTest {

   private DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");

   @Test
   public void moveOneDayForward_WithinSameMonth() throws Exception {
      assertEquals(date("01/31/2010"), moveOneDayForward(date("01/30/2010")));
   }

   @Test
   public void moveOneDayForward_AcrossMonths() throws Exception {
      assertEquals(date("02/01/2010"), moveOneDayForward(date("01/31/2010")));
   }

   @Test
   public void moveOneDayForward_AcrossYears() throws Exception {
      assertEquals(date("01/01/2011"), moveOneDayForward(date("12/31/2010")));
   }

   @Test
   public void firstOfTheMonth_LastOfJan2010() throws Exception {
      Date someDayInMonth = date("01/31/2010");
      Date first = firstOfTheMonth(someDayInMonth);
      assertEquals(date("01/01/2010"), first);
      assertNotSame(first, someDayInMonth);
   }

   @Test
   public void firstOfTheMonth_LastOfFeb2010() throws Exception {
      Date someDayInMonth = date("02/28/2010");
      Date first = firstOfTheMonth(someDayInMonth);
      assertEquals(date("02/01/2010"), first);
      assertNotSame(first, someDayInMonth);
   }

   @Test
   public void differenceInDays_SunApr_20100425_MonMay_20100503() throws Exception {
      assertEquals(8, DateUtils.differenceInDays(date("05/03/2010"), date("04/25/2010")));
   }


   @Test
   public void differenceInDays_SunDec_20101226_MonMay_20110103() throws Exception {
      assertEquals(8, DateUtils.differenceInDays(date("01/03/2011"), date("12/26/2010")));
   }

   @Test
   public void differenceInDays_consecutive_but_swapped() throws Exception {
      assertEquals(1,
                   DateUtils.differenceInDays(date("05/25/1981"), date("05/26/1981")));
   }

   public Date date(String dateString) throws Exception {
      return dateFormatter.parse(dateString);
   }

}
