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

import com.bradrydzewski.gwt.calendar.client.dayview.DayView;

public class Calendar extends CalendarWidget {
    /**
     * The component to manage the presentation of appointments in a single day
     * layout.
     */
    private DayView dayView = null;

    /**
     * The component to manage the presentation of appointments as a list.
     */
    private AgendaView agendaView = null;

    /**
     * The calendar view to display a single day.
     */
    public static final int DAY_VIEW = 0;

    /**
     * The calendar view to display appointments in a list.
     */
    public static final int AGENDA_VIEW = 1;

    /**
     * The calendar view to display the month corresponding to this calendar's
     * <code>date</code> property.
     *
     * @see #getDate()
     */
    public static final int MONTH_VIEW = 2;

    /**
     * Constructs a <code>Calendar</code> with the {@link #DAY_VIEW} currently
     * selected.
     */
    public Calendar() {
        this(DAY_VIEW);
    }

    public Calendar(java.util.Date date) {
        super(date);
    }

    public Calendar(int view) {
        super();
    }

    public void setView(int view) {
        setView(view, getDays());
    }

    /**
     * Sets the current view of this calendar.
     *
     * @param view The ID of a view used to visualize the appointments managed
     *             by the calendar
     * @param days The number of days to display in the view, which can be
     *             ignored by some views.
     */
    public void setView(int view, int days) {
        switch (view) {
            case DAY_VIEW: {
                if (dayView == null)
                    dayView = new DayView();
                dayView.setDisplayedDays(days);
                setView(dayView);
                break;
            }
            case AGENDA_VIEW: {
                //if(agendaView==null)
                //TODO: need to cache agendaView, but there is a layout bug after a calendar item is deleted.
                agendaView = new AgendaView();
                setView(agendaView);
                break;
            }
            case MONTH_VIEW: {
                setView(new MonthView());
            }
        }
        this.refresh();
    }
}
 