package com.bradrydzewski.gwt.calendar.theme.ical.client;

import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class ICalDayViewStyleManagerIEImpl extends ICalDayViewStyleManager {
	
    static {
    	//Need to swap out RGBA values for HEX color values
    	//Used this to auto-generate these values: http://kimili.com/journal/rgba-css-generator-for-internet-explorer
		ICalAppointmentTheme.STYLES.get(AppointmentStyle.BLUE).setBackground("#722868DE");
		ICalAppointmentTheme.STYLES.get(AppointmentStyle.GREEN).setBackground("#7245AD29");
		ICalAppointmentTheme.STYLES.get(AppointmentStyle.LIGHT_PURPLE).setBackground("#72BB48B9");
		ICalAppointmentTheme.STYLES.get(AppointmentStyle.ORANGE).setBackground("#72F6861E");
		ICalAppointmentTheme.STYLES.get(AppointmentStyle.PURPLE).setBackground("#72A992EA");
		ICalAppointmentTheme.STYLES.get(AppointmentStyle.RED).setBackground("#72F76260");
    }
	
	@Override
	public void applyRGBA(Element elem, ICalAppointmentStyle style) {
		DOM.setStyleAttribute(elem, "filter", "progid:DXImageTransform.Microsoft.gradient(startColorstr="+style.getBackground()+",endColorstr="+style.getBackground()+")");
    }

	@Override
    public void applyGradient(Element elem,  ICalAppointmentStyle style) {
		//elem.getStyle().setBackgroundColor("#FFF");
//		DOM.setStyleAttribute(elem, "background", "transparent");
		DOM.setStyleAttribute(elem, "filter", "progid:DXImageTransform.Microsoft.gradient(startColorstr="+style.getGradientStart()+",endColorstr="+style.getGradientStop()+") progid:DXImageTransform.Microsoft.dropshadow(OffX=3, OffY=3,Color='#C7C7C7', Positive='true')");
    }
}
