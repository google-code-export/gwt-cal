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

import com.google.gwt.user.client.Element;

/**
 * Abstract base class defining the operations to render a calendar and
 * user-input dispatching methods. <p/> <p></p>Subclasses will provide the
 * details of rendering the calendar to visualize by day (Day View), monthly
 * (month view), agenda (list view) and the logic implementing the user-input
 * event processing.
 *
 * @author Brad Rydzewski
 */
public abstract class CalendarView {

    /**
     * Calendar widget bound to the view.
     *
     * @see CalendarWidget
     */
    protected CalendarWidget calendarWidget = null;

    /**
     * Number of days the calendar should display at a given time, 3 by
     * default.
     */
    private int displayedDays = 3;

    /**
     * Attaches this view to the provided {@link CalendarWidget}.
     *
     * @param calendarWidget The interactive widget containing the calendar
     */
    public void attach(CalendarWidget calendarWidget) {
        this.calendarWidget = calendarWidget;
    }

    /**
     * Detaches this view from the currently associated {@link CalendarWidget}.
     * TODO: The CalendarWidget might still have a reference to this
     * CalendarView, is that correct??
     */
    public void detatch() {
        calendarWidget = null;
    }

    /**
     * Returns the CSS style name of this calendar view.
     *
     * @return The CSS style that should be used when rendering this calendar
     *         view
     */
    public abstract String getStyleName();

    public void doSizing() {
    }

    public abstract void doLayout();

    public abstract void onDoubleClick(Element element);

    public abstract void onMouseDown(Element element);

    /**
     * Processes user {@link com.google.gwt.event.dom.client.KeyCodes.KEY_DELETE}
     * keystrokes. The <code>CalendarView</code> implementation is empty so that
     * subclasses are not forced to implement it if no specific logic is needed
     * for {@link com.google.gwt.event.dom.client.KeyCodes.KEY_DELETE}
     * keystrokes.
     */
    public void onDeleteKeyPressed() {
    }

    /**
     * Processes user {@link com.google.gwt.event.dom.client.KeyCodes.KEY_UP}
     * keystrokes. The <code>CalendarView</code> implementation is empty so that
     * subclasses are not forced to implement it if no specific logic is needed
     * for {@link com.google.gwt.event.dom.client.KeyCodes.KEY_UP} keystrokes.
     */
    public void onUpArrowKeyPressed() {
    }

    /**
     * Processes user {@link com.google.gwt.event.dom.client.KeyCodes.KEY_DOWN}
     * keystrokes. The <code>CalendarView</code> implementation is empty so that
     * subclasses are not forced to implement it if no specific logic is needed
     * for {@link com.google.gwt.event.dom.client.KeyCodes.KEY_DOWN}
     * keystrokes.
     */

    public void onDownArrowKeyPressed() {
    }

    /**
     * Processes user {@link com.google.gwt.event.dom.client.KeyCodes.KEY_LEFT}
     * keystrokes. The <code>CalendarView</code> implementation is empty so that
     * subclasses are not forced to implement it if no specific logic is needed
     * for {@link com.google.gwt.event.dom.client.KeyCodes.KEY_LEFT}
     * keystrokes.
     */

    public void onLeftArrowKeyPressed() {
    }

    /**
     * Processes user {@link com.google.gwt.event.dom.client.KeyCodes.KEY_RIGHT}
     * keystrokes. The <code>CalendarView</code> implementation is empty so that
     * subclasses are not forced to implement it if no specific logic is needed
     * for {@link com.google.gwt.event.dom.client.KeyCodes.KEY_RIGHT}
     * keystrokes.
     */

    public void onRightArrowKeyPressed() {
    }

    /**
     * Configures this view component's  currently selected
     * <code>appointment</code>. Notification to the calendar widget associated
     * is optional and controlled with the <code>fireEvent</code> flag.
     *
     * @param appointment The appointment in the calendar in-memory model to be
     *                    configured as the currently selected
     * @param fireEvent   Indicates whether a selection event should be
     *                    triggered by the parent widget so that it informs its
     *                    set of registered listeners about the change
     */
    public void setSelectedAppointment(Appointment appointment,
                                       boolean fireEvent) {
        calendarWidget.setSelectedAppointment(appointment);
        if (fireEvent) {
            calendarWidget.fireSelectionEvent(appointment);
        }
    }

    /**
     * Configures this view component's currently selected
     * <code>appointment</code> and notifies widget about the change in the
     * model state.
     *
     * @param appointment The appointment in the calendar in-memory model to be
     *                    configured as the currently selected
     */
    public void setSelectedAppointment(Appointment appointment) {
        setSelectedAppointment(appointment, true);
    }

    /**
     * Returns the configured number of days the calendar should display at a
     * given time.
     *
     * @return The number of days this calendar view should display at a given
     *         time
     */
    public int getDisplayedDays() {
        return displayedDays;
    }

    /**
     * Sets the configured number of days the calendar should display at a given
     * time.
     *
     * @param displayedDays The number of days this calendar view should display
     *                      at a given time
     */
    public void setDisplayedDays(int displayedDays) {
        this.displayedDays = displayedDays;
    }
}