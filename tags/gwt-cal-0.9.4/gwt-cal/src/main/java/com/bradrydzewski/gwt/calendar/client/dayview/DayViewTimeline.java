/*
 * This file is part of gwt-cal
 * Copyright (C) 2010  Scottsdale Software LLC
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
package com.bradrydzewski.gwt.calendar.client.dayview;

import com.bradrydzewski.gwt.calendar.client.CalendarFormat;
import com.bradrydzewski.gwt.calendar.client.HasSettings;
import com.bradrydzewski.gwt.calendar.client.util.FormattingUtil;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A sequential display of the hours in a day. Each
 * hour label should visually line up to a cell in the DayGrid.
 *
 * @author Brad Rydzewski
 */
public class DayViewTimeline extends Composite {

    private AbsolutePanel timelinePanel = new AbsolutePanel();
    private HasSettings settings = null;
    private static final String TIME_LABEL_STYLE = "hour-label";

    public DayViewTimeline(HasSettings settings) {
        initWidget(timelinePanel);
        timelinePanel.setStylePrimaryName("time-strip");
        this.settings = settings;
        prepare();
    }

    public final void prepare() {
        timelinePanel.clear();
        float labelHeight = settings.getSettings().getIntervalsPerHour()
                * settings.getSettings().getPixelsPerInterval();

        int i = 0;
        if (settings.getSettings().isOffsetHourLabels()) {
            i = 1;
            SimplePanel sp = new SimplePanel();
            sp.setHeight((labelHeight / 2) + "px");
            timelinePanel.add(sp);
        }

        int dayStartsAt = settings.getSettings().getDayStartsAt();

        while (i < CalendarFormat.HOURS_IN_DAY) {
            int index = i + dayStartsAt;
            if (index >= CalendarFormat.HOURS_IN_DAY) {
                index = index - CalendarFormat.HOURS_IN_DAY;
            }
            String hour = CalendarFormat.INSTANCE.getHourLabels()[index];
            index++;
            i++;

            //block
            SimplePanel hourWrapper = new SimplePanel();
            hourWrapper.setStylePrimaryName(TIME_LABEL_STYLE);
            hourWrapper.setHeight((labelHeight + FormattingUtil.getBorderOffset()) + "px");

            FlowPanel flowPanel = new FlowPanel();
            flowPanel.setStyleName("hour-layout");

            String amPm = " ";

            if (index < 13)
                amPm += CalendarFormat.INSTANCE.getAm();
            else if (index > 13)
                amPm += CalendarFormat.INSTANCE.getPm();
            else {
                if (CalendarFormat.INSTANCE.isUseNoonLabel()) {
                    hour = CalendarFormat.INSTANCE.getNoon();
                    amPm = "";
                } else {
                    amPm += CalendarFormat.INSTANCE.getPm();
                }
            }

            Label hourLabel = new Label(hour);
            hourLabel.setStylePrimaryName("hour-text");
            flowPanel.add(hourLabel);

            Label amPmLabel = new Label(amPm);
            amPmLabel.setStylePrimaryName("ampm-text");
            flowPanel.add(amPmLabel);

            hourWrapper.add(flowPanel);

            timelinePanel.add(hourWrapper);
        }
    }
}