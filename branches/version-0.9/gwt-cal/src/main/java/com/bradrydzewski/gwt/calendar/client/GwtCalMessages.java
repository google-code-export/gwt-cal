package com.bradrydzewski.gwt.calendar.client;

import com.google.gwt.i18n.client.Messages;

/**
 * Defines the Strings that can be localized in gwt-cal.
 *
 * @author Brad Rydzewski
 * @author Carlos D. Morales
 */
public interface GwtCalMessages extends Messages
{
    /**
     * Footer for days in the <code>MonthView</code> in which there are more
     * appointments to display than fit in the day cell.
     *
     * @param appointments The number of additional appointments in the day
     * @return The localized string
     */
    public String monthView_MoreAppointmentsPerDay(int appointments);

    /**
     * The localized text label for 12 p.m.
     *
     * @return The label for the noon
     */
    public String noon();
}
