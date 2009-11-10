package com.bradrydzewski.gwt.calendar.client.monthview;

import com.bradrydzewski.gwt.calendar.client.Appointment;

/**
 * Represents an <em>all-day</em> <code>appointment</code> to be stacked on the
 * top of a month view week.
 *
 * @author Carlos D. Morales
 */
public class AllDayLayoutDescription
        extends AbstractWeekTopStackableDescription {

    public AllDayLayoutDescription(int weekDay,
                                   Appointment appointment) {
        super(weekDay, weekDay, appointment);
    }
}
