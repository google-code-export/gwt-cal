/*
 * This file is part of gwt-cal
 * Copyright (C) 2009,2010  Scottsdale Software LLC
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
package com.allen_sauer.gwt.dnd.client.drop;

import java.util.Date;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.DateUtils;
import com.bradrydzewski.gwt.calendar.client.monthview.AppointmentWidget;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * Controls the Move and Drop (after dragging) events that can be generated in
 * the Month View.
 *
 * @author Brad Rydzewski
 */
public class MonthViewDropController extends AbsolutePositionDropController {

   private int daysPerWeek;
   private int weeksPerMonth;
   private Date firstDateDisplayed;

   /**
    * Flextable that displays a Month in grid format.
    */
   private FlexTable monthGrid;

   /**
    * List of all cells currently highlighted as an appointment is being
    * dragged.
    */
   private Element[] highlightedCells;

   public MonthViewDropController(AbsolutePanel dropTarget,
      FlexTable monthGrid) {

      super(dropTarget);
      this.monthGrid = monthGrid;
   }

   public void setDaysPerWeek(int daysPerWeek) {
      this.daysPerWeek = daysPerWeek;
   }

   public void setWeeksPerMonth(int weeksPerMonth) {
      this.weeksPerMonth = weeksPerMonth;
   }

   public Date getFirstDateDisplayed() {
      return firstDateDisplayed;
   }

   public void setFirstDateDisplayed(Date firstDateDisplayed) {
      this.firstDateDisplayed = firstDateDisplayed;
   }

   /**
    * Manages the highlighting of cells in the month view grid when an
    * <code>Appointment</code> is being dragged.
    *
    * @param context An object providing information about the object being
    *                dragged
    */
   @Override
   public void onMove(DragContext context) {
      super.onMove(context);

      //get the draggable object
      Draggable draggable = draggableList.get(0);

      //make sure it isn't null (shouldn't ever be)
      if (draggable == null)
         return;

      //get the mouse/drag coordinates
      int x =
         context.desiredDraggableX - dropTargetOffsetX + draggable.relativeX;
      int y =
         context.desiredDraggableY - dropTargetOffsetY + draggable.relativeY;

      //Now we need to figure out which cell to highlight based
      // on the X,Y coordinates
      int col =
         (int) Math.floor(x / (monthGrid.getOffsetWidth() / daysPerWeek));
      int row =
         (int) Math.floor(y / (monthGrid.getOffsetHeight() / weeksPerMonth)) +
            1;


      //Get element for cell
      Element currHoveredCell =
         monthGrid.getFlexCellFormatter().getElement(row, col);

      //If this cell isn't already highlighted, we need to highlight
      if (highlightedCells == null || highlightedCells.length < 0 ||
         !currHoveredCell.equals(highlightedCells[0])) {

         if (highlightedCells != null) {
            for (Element elem : highlightedCells) {
               if (elem != null)
                  DOM.setStyleAttribute(elem, "backgroundColor", "#FFFFFF");
            }
         }

         //here I hard-code 5 as the number of cells an appointment
         // should span. This, however, should be calculated.
         //Beware, we need to be very careful about memory here.
         // I tried to do a date diff calculation and got
         // out of memory exceptions in the JVM AND in the chrome browser
         Date startDate =
            ((AppointmentWidget) draggable.widget).getAppointment().getStart();
         Date endDate =
            ((AppointmentWidget) draggable.widget).getAppointment().getEnd();

         int dateDiff = getDateDiff(startDate, endDate);
         dateDiff = (dateDiff <= 0) ? 1 : dateDiff;
         highlightedCells = getCells(row, col, dateDiff);

         //TODO: month view highlighted cell style be moved to the css style sheet
         for (Element elem : highlightedCells) {
            if (elem != null)
               DOM.setStyleAttribute(elem, "backgroundColor", "#C3D9FF");
         }
      }
   }

   /**
    * Callback method executed once the drag has completed. We need to reset the
    * background color of all previously highlighted cells. Also need to
    * actually change the appointment's start / end date here (code doesn't
    * exist yet).
    *
    * @param context An object containing information of what was dragged and
    *                dropped, including the <code>Appointment</code>,
    *                coordinates of the drop, etc.
    */
   @Override
   public void onDrop(DragContext context) {
      super.onDrop(context);

      for (Element elem : highlightedCells) {
         if (elem != null) {
            DOM.setStyleAttribute(elem, "backgroundColor", "#FFFFFF");
         }
      }
      highlightedCells = null;

      Draggable draggable = draggableList.get(0);

      Appointment appointment =
         ((AppointmentWidget) context.draggable).getAppointment();

      long originalStartToEndTimeDistance =
         appointment.getEnd().getTime() - appointment.getStart().getTime();

      //get the column and row for the draggable widget
      int row = getRow(context, draggable) - 1;
      int col = getColumn(context, draggable);
      int cell = row * daysPerWeek + col;

      //calculate the new start & end dates
      Date newStart = DateUtils.shiftDate(firstDateDisplayed, cell);
      newStart.setHours(appointment.getStart().getHours());
      newStart.setMinutes(appointment.getStart().getMinutes());
      newStart.setSeconds(appointment.getStart().getSeconds());

      Date newEnd = new Date(newStart.getTime() + originalStartToEndTimeDistance);

      //Set the appointment's new start & end dates
      appointment.setStart(newStart);
      appointment.setEnd(newEnd);
   }

   /**
    * Gets all the cells (as DOM Elements) that an appointment spans. Note: It
    * only includes cells in the table. If an appointment ends in the following
    * month the last cell in the list will be the last cell in the table.
    *
    * @param row  Appointment's starting row
    * @param col  Appointment's starting column
    * @param days Number of days an appointment spans
    * @return Cell elements that an appointment spans
    */
   protected Element[] getCells(int row, int col, int days) {

      Element[] elems = new Element[days];

      for (int i = 0; i < days; i++) {

         if (col > daysPerWeek - 1) {
            col = 0;
            row++;
         }

         //Cheap code here. If the row / cell throw an out of index exception
         // we just break. THis kind of sucks because we have to
         // now account for null items in the Element[] array.
         try {
            elems[i] = monthGrid.getFlexCellFormatter().getElement(row, col);
         } catch (Exception ex) {
            break;
         }

         col++;
      }

      return elems;
   }

   /**
    * Gets the difference in days between two Dates. TODO: not correctly
    * calculating when end date's month != start date's month.
    *
    * TODO: Seriously analyze if it's not better to use DateUtils.differenceInDays
    * considering the additional logic that is required to calculate
    * the difference between two days additional to the difference and
    * division by milliseconds in a day
    *
    * @param startDate
    * @param endDate
    * @return
    */
   public int getDateDiff(Date startDate, Date endDate) {

      if (startDate.getMonth() == endDate.getMonth()) {
         return endDate.getDate() - startDate.getDate() + 1;
      } else
         return (int) Math.ceil(((endDate.getTime() - startDate.getTime()) /
            (1000 * 60 * 60 * 24))) + 1;
   }


   public int getRow(DragContext context, Draggable draggable) {
      int yCoordinate =
         context.desiredDraggableY - dropTargetOffsetY + draggable.relativeY;
      //Figure out which cell to highlight based on X,Y coordinates
      return
         (int) Math.floor(
            yCoordinate / (monthGrid.getOffsetHeight() / weeksPerMonth)) +
            1;
   }

   public int getColumn(DragContext context, Draggable draggable) {
      int xCoordinate =
         context.desiredDraggableX - dropTargetOffsetX + draggable.relativeX;
      //Figure out which cell to highlight based on X,Y coordinates
      return (int) Math
         .floor(xCoordinate / (monthGrid.getOffsetWidth() / daysPerWeek));
   }
}
