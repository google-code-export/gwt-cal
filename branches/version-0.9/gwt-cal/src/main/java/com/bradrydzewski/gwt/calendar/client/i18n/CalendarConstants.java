package com.bradrydzewski.gwt.calendar.client.i18n;

import com.google.gwt.i18n.client.Messages;

/**
 * Defines the Strings that can be localized in gwt-cal.
 *
 * @author Brad Rydzewski
 * @author Carlos D. Morales
 */
public interface CalendarConstants extends Messages
{
    /**
     * Footer for days in the <code>MonthView</code> in which there are more
     * appointments to display than fit in the day cell.
     *
     * @param appointments The number of additional appointments in the day
     * @return The localized string
     */
    public String more(int appointments);

    /**
     * The localized text label for 12 p.m.
     *
     * @return The label for the noon
     */
    public String noon();
    
    /**
     * String representing AM. For certain locales this
     * will be null.
     * @return AM string
     */
    public String am();

    /**
     * String representing AM. For certain locales this
     * will be null.
     * @return PM string
     */
    public String pm();
    
   
    /**
     * String used to format time in the <code>DayView</code>
     * components timeline. Example: time is formatted in the US as h AM,
     * and is formatted in French HH:00.
     * @return The localized time format for the DayView
     */
    public String timeFormat();

    /**
     * Each column in the <code>DayView</code> represents
     * a date in time, and displays the date
     * in its header (i.e. Wed, Jan 15). This property provides
     * the pattern to format the date.
     * 
     * @return The localized time format for the DayView
     */
    public String dateFormat();

    /**
     * Each column in the <code>MonthView</code> represents
     * a day of the week, and displays the day of the week
     * in its header (i.e. Mon, Tue, Wed). This property provides
     * the pattern to format the day of the week.
     * @return
     */
    public String weekdayFormat();
}
