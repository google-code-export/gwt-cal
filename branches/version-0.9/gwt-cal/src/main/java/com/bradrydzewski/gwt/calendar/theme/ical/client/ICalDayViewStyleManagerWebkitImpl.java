package com.bradrydzewski.gwt.calendar.theme.ical.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class ICalDayViewStyleManagerWebkitImpl extends ICalDayViewStyleManager {
	@Override
    public void applyGradient(Element elem, ICalAppointmentStyle style) {
    	DOM.setStyleAttribute(elem, "backgroundImage", "-webkit-gradient(linear, 0% 0%, 100% 0%,color-stop(0.0,"+style.getGradientStart()+"),color-stop(0.5,"+style.getGradientStop()+"))");
	}
}
