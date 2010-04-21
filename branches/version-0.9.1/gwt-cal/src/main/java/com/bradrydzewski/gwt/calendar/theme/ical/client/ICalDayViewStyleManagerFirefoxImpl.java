package com.bradrydzewski.gwt.calendar.theme.ical.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class ICalDayViewStyleManagerFirefoxImpl extends ICalDayViewStyleManager {

	@Override
    public void applyGradient(Element elem, ICalAppointmentStyle style) {
    	DOM.setStyleAttribute(elem, "background", "-moz-linear-gradient(left center,"+style.getGradientStart()+" 0%,"+style.getGradientStop()+" 50%)");
    }
}
