package com.bradrydzewski.gwt.calendar.client.monthview;

import com.bradrydzewski.gwt.calendar.client.Appointment;

/**
 * Contains common properties and behavior for layout descriptions
 * that can be &quot;stacked&quot; on top of each month view's week.
 *
 * @author Carlos D. Morales
 */
public abstract class AbstractWeekTopStackableDescription
        implements WeekTopStackableDescription {

    private int stackOrder = 0;
    private int fromWeekDay = 0;
    private int toWeekDay = 0;
    private Appointment appointment = null;

    AbstractWeekTopStackableDescription(int toWeekDay, int fromWeekDay,
                                               Appointment appointment) {
        this.toWeekDay = toWeekDay;
        this.fromWeekDay = fromWeekDay;
        this.appointment = appointment;
    }

    public boolean overlapsWithRange(int from, int to) {
        return fromWeekDay >= from && fromWeekDay <= to ||
                fromWeekDay <= from && toWeekDay >= from;
    }

    public void setStackOrder(int stackOrder) {
        this.stackOrder = stackOrder;
    }

    public int getStackOrder() {
        return stackOrder;
    }

    public int getWeekStartDay() {
        return fromWeekDay;
    }

    public int getWeekEndDay() {
        return toWeekDay;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
