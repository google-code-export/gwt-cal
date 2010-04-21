package com.bradrydzewski.gwt.calendar.theme.ical.client;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.dayview.AppointmentWidget;
import com.bradrydzewski.gwt.calendar.client.dayview.DayViewStyleManager;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class ICalDayViewStyleManager extends DayViewStyleManager {

	private static final String APPOINTMENT_STYLE = "dv-appointment";
	private static final String APPOINTMENT_STYLE_SELECTED = "-selected";
	private static final String APPOINTMENT_STYLE_MULTIDAY = "-multiday";

	public void applyStyle(AppointmentWidget widget, boolean selected) {

		// Extract the Appointment for later reference
		Appointment appointment = widget.getAppointment();
		// Extract the DOM Element for later reference
		Element elem = widget.getElement();
		Element bodyElem = widget.getBody().getElement();
		Element headerElem = widget.getHeader().getElement();
		// Is MultiDay?
		boolean multiDay = appointment.isMultiDay() || appointment.isAllDay();

		
		//Lookup the style from the map
		ICalAppointmentStyle style = (ICalAppointmentStyle)
			ICalAppointmentTheme.STYLES.get(appointment.getStyle());
		
		//Determine Style Name
		String styleName = APPOINTMENT_STYLE;
		if(multiDay) styleName+=APPOINTMENT_STYLE_MULTIDAY;
		if(selected) styleName+=APPOINTMENT_STYLE_SELECTED;
		widget.setStylePrimaryName(styleName);
		
		//If no style is found, apply the default blue style
		//TODO: need to check for a custom style
		if(style==null)
			style =  (ICalAppointmentStyle) ICalAppointmentTheme.DEFAULT;
		
		if (multiDay){
			setMultiDayStyle(elem, headerElem, bodyElem, style, selected);
		}else {
			setSingleDayStyle(elem, headerElem, bodyElem, style, selected);
		}

	}
	
	protected void setSingleDayStyle(
			Element elem,Element headerElem,Element bodyElem,
			ICalAppointmentStyle style, boolean selected) {
		
		DOM.setStyleAttribute(elem, "borderColor", style.getBackgroundHeader());
		
		if (selected) {
			DOM.setStyleAttribute(headerElem, "backgroundColor",style.getBackgroundHeader());
			DOM.setStyleAttribute(headerElem, "color","#FFFFFF");
			DOM.setStyleAttribute(bodyElem, "color","#FFFFFF");
			applyGradient(elem, style);
			
		} else {
			DOM.setStyleAttribute(headerElem, "backgroundColor","transparent");
			DOM.setStyleAttribute(headerElem, "color",style.getSelectedBorder());
			DOM.setStyleAttribute(bodyElem, "color", style.getSelectedBorder());
			applyRGBA(elem, style);
		}
	}

	protected void setMultiDayStyle(
			Element elem,Element headerElem,Element bodyElem,
			ICalAppointmentStyle style, boolean selected) {
		
		DOM.setStyleAttribute(elem, "borderColor", style.getBackgroundHeader());
		DOM.setStyleAttribute(elem, "backgroundColor",style.getBackgroundHeader());
		DOM.setStyleAttribute(headerElem, "color","#FFFFFF");
		
//		if (selected) {
//			DOM.setStyleAttribute(elem, "backgroundColor",style.getBackgroundHeader());
//			DOM.setStyleAttribute(headerElem, "color","#FFFFFF");
//
//		} else {
//			DOM.setStyleAttribute(headerElem, "backgroundColor","transparent");
//			DOM.setStyleAttribute(headerElem, "color",style.getSelectedBorder());
//			DOM.setStyleAttribute(bodyElem, "color", style.getSelectedBorder());
//		}
	}

	public void applyRGBA(Element elem, ICalAppointmentStyle style) {
		DOM.setStyleAttribute(elem, "background", style.getBackground());
    }
    
    public void applyGradient(Element elem, ICalAppointmentStyle style) {
    	DOM.setStyleAttribute(elem, "background", style.getBackground());
    }
}
