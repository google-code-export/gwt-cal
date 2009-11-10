package com.bradrydzewski.gwt.calendar.client.monthview;

import com.bradrydzewski.gwt.calendar.client.Appointment;

/**
 * Contains the layout details of an <code>Appointment</code> that spans more
 * than a day.
 *
 * @author Carlos D. Morales
 */
public class MultiDayLayoutDescription extends AbstractWeekTopStackableDescription {

    public MultiDayLayoutDescription(int fromWeekDay, int toWeekDay,
                                     Appointment appointment) {
        super(toWeekDay, fromWeekDay, appointment);
    }
}