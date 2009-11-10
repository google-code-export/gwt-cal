package com.bradrydzewski.gwt.calendar.client.monthview;

/**
 * Created by IntelliJ IDEA. User: ytsejammer Date: Nov 8, 2009 Time: 8:40:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WeekTopStackableDescription extends
        AppointmentLayoutDescription {

    public void setStackOrder(int stackOrder);

    public int getStackOrder();

    public boolean overlapsWithRange(int from, int to);

    public int getWeekStartDay();

    public int getWeekEndDay();
}
