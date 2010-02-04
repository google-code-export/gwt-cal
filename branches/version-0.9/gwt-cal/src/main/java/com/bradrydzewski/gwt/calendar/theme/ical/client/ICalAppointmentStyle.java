package com.bradrydzewski.gwt.calendar.theme.ical.client;

import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;

class ICalAppointmentStyle extends AppointmentStyle {
	public ICalAppointmentStyle(String background, String border, String image) {
		super();
		this.background = background;
		this.backgroundHeader = border;
		this.border = border;
		this.headerText = "#FFFFFF";
		this.text = border;
		
		this.selectedHeaderText = "#FFFFFF";
		this.selectedBackground = background;
		this.selectedBackgroundImage = image;
		this.selectedBackgroundHeader = border;
		this.selectedText = border;
	}
}
