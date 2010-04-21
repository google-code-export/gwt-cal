package com.bradrydzewski.gwt.calendar.theme.google.client;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.monthview.AppointmentWidget;
import com.bradrydzewski.gwt.calendar.client.monthview.MonthViewStyleManager;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

public class GoogleMonthViewStyleManager extends MonthViewStyleManager {


	private static final String APPOINTMENT_STYLE = "appointment";
	private static final String APPOINTMENT_STYLE_SELECTED = "-selected";
	private static final String APPOINTMENT_STYLE_MULTIDAY = "-multiday";


	public void applyStyle(AppointmentWidget widget, boolean selected) {
		
		//Extract the Appointment for later reference
		Appointment appointment = widget.getAppointment();
		//Extract the DOM Element for later reference
		Element elem = widget.getElement();
		//Is MultiDay?
		boolean multiDay = appointment.isMultiDay() || appointment.isAllDay();
		
		//Lookup the style from the map
		GoogleAppointmentStyle style = (GoogleAppointmentStyle)
			GoogleAppointmentTheme.STYLES.get(appointment.getStyle());
		
		//Determine Style Name
		String styleName = APPOINTMENT_STYLE;
		if(multiDay) styleName+=APPOINTMENT_STYLE_MULTIDAY;
		if(selected) styleName+=APPOINTMENT_STYLE_SELECTED;
		widget.setStylePrimaryName(styleName);
		
		//If no style is found, apply the default blue style
		//TODO: need to check for a custom style
		if(style==null)
			style =  (GoogleAppointmentStyle) GoogleAppointmentTheme.DEFAULT;
		
		//Apply Single vs. Multi-day style
		if (multiDay) {
			
			DOM.setStyleAttribute(elem, "backgroundColor", style.getBackground());
			DOM.setStyleAttribute(elem, "borderColor", style.getBorder());

		} else {
			
			DOM.setStyleAttribute(elem, "color", style.getSelectedBorder());
		}

		//Apply style specific to selected appointments
		if (selected) {
			
			DOM.setStyleAttribute(elem, "borderColor", style.getSelectedBorder());
		}
	}

}
