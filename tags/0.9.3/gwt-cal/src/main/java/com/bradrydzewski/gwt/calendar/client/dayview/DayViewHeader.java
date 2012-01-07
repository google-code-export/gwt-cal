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
package com.bradrydzewski.gwt.calendar.client.dayview;

import java.util.Date;

import com.bradrydzewski.gwt.calendar.client.CalendarFormat;
import com.bradrydzewski.gwt.calendar.client.DateUtils;
import com.bradrydzewski.gwt.calendar.client.HasSettings;
import com.bradrydzewski.gwt.calendar.client.util.WindowUtils;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

public class DayViewHeader extends Composite {
   private FlexTable header = new FlexTable();
   private AbsolutePanel dayPanel = new AbsolutePanel();
   private AbsolutePanel splitter = new AbsolutePanel();
   //private static final DateTimeFormat DAY_FORMAT = DateTimeFormat.getFormat("EEE, MMM d");
   private static final String GWT_CALENDAR_HEADER_STYLE =
      "gwt-calendar-header";
   private static final String DAY_CELL_CONTAINER_STYLE = "day-cell-container";
   private static final String YEAR_CELL_STYLE = "year-cell";
   private static final String SPLITTER_STYLE = "splitter";


   public DayViewHeader(HasSettings settings) {
      initWidget(header);
      header.setStyleName(GWT_CALENDAR_HEADER_STYLE);
      dayPanel.setStyleName(DAY_CELL_CONTAINER_STYLE);

      header.insertRow(0);
      header.insertRow(0);
      header.insertCell(0, 0);
      header.insertCell(0, 0);
      header.insertCell(0, 0);
      header.setWidget(0, 1, dayPanel);
      header.getCellFormatter().setStyleName(0, 0, YEAR_CELL_STYLE);
      header.getCellFormatter().setWidth(0, 2,
                                         WindowUtils.getScrollBarWidth(true) +
                                            "px");
      // header.getCellFormatter().setStyleName(1, 0,SPLITTER_STYLE);

      header.getFlexCellFormatter().setColSpan(1, 0, 3);
      header.setCellPadding(0);
      header.setBorderWidth(0);
      header.setCellSpacing(0);

      splitter.setStylePrimaryName(SPLITTER_STYLE);
      header.setWidget(1, 0, splitter);
   }

   public void setDays(Date date, int days) {

      dayPanel.clear();
      float dayWidth = 100f / days;
      float dayLeft;

      for (int i = 0; i < days; i++) {

         // increment the date by 1
         if (i > 0) {
            DateUtils.moveOneDayForward(date);
         }

         // set the left position of the day splitter to
         // the width * incremented value
         dayLeft = dayWidth * i;

         //String headerTitle = DAY_LIST[date.getDay()] + ", "
         //		+ MONTH_LIST[date.getMonth()] + " " + date.getDate();

         String headerTitle = CalendarFormat.INSTANCE.getDateFormat().format(date);

         Label dayLabel = new Label();
         dayLabel.setStylePrimaryName("day-cell");
         dayLabel.setWidth(dayWidth + "%");
         dayLabel.setText(headerTitle);
         DOM.setStyleAttribute(dayLabel.getElement(), "left", dayLeft + "%");

         // set the style of the header to show that it is today
         if (DateUtils.areOnTheSameDay(new Date(), date)) {
            dayLabel.setStyleName("day-cell-today");
         } else if(DateUtils.isWeekend(date)) {
        	 dayLabel.setStyleName("day-cell-weekend");
         }

         dayPanel.add(dayLabel);
      }
   }

   public void setYear(Date date) {
      setYear(DateUtils.year(date));
   }

   public void setYear(int year) {
      header.setText(0, 0, String.valueOf(year));
   }

}


