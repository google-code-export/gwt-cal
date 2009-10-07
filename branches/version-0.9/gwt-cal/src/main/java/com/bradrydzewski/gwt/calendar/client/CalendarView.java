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

import java.util.ArrayList;
import java.util.Date;

import com.bradrydzewski.gwt.calendar.client.event.DeleteEvent;
import com.bradrydzewski.gwt.calendar.client.event.DeleteHandler;
import com.bradrydzewski.gwt.calendar.client.event.HasDeleteHandlers;
import com.bradrydzewski.gwt.calendar.client.event.HasTimeBlockClickHandlers;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickEvent;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickHandler;
import com.bradrydzewski.gwt.calendar.client.util.AppointmentUtil;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;

/**
 * This is a base class all Calendar Views (i.e. Day view, month view, list view)
 * should build upon. It defines and or implements all required methods and
 * properties.
 * @author Brad Rydzewski
 */
public abstract class CalendarView {

	/**
	 * Calendar widget bound to the view.
	 */
	protected CalendarWidget calendarWidget = null;

    /**
     * Number of days the calendar should display at a given time.
     */
    protected int days = 3;

    /**
     * Attaches the view to the provided calendar widget.
     * @param calendarWidget
     */
	public void attach(CalendarWidget calendarWidget) {
		this.calendarWidget = calendarWidget;
	}

	/**
	 * This method is invoked when the view is detached from the
	 * calendar widget to which it is bound.
	 */
	public void detatch() {
		calendarWidget = null;
	}
	
	public abstract String getStyleName();
	public void doSizing() {
	}
	public abstract void doLayout();
	
	public abstract void onDoubleClick(Element element);
	public abstract void onMouseDown(Element element);
	public void onDeleteKeyPressed() {
		
	}
	public void onUpArrowKeyPressed() {
		
	}
	public void onDownArrowKeyPressed() {
		
	}
	public void onLeftArrowKeyPressed() {
		
	}
	public void onRightArrowKeyPressed() {
		
	}

	public void setSelectedAppointment(Appointment appointment, boolean fireEvent) {
		calendarWidget.selectedAppointment = appointment;
		if(fireEvent) {
			calendarWidget.fireSelectionEvent(appointment);
		}
	}
	public void setSelectedAppointment(Appointment appointment) {
		setSelectedAppointment(appointment, true);
	}
	public int getDays() {
		return this.days;
	}
	public void setDays(int days) {
		this.days = days;
	}
}
