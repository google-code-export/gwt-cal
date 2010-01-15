package com.bradrydzewski.gwt.calendar.theme.google.client;

import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;

public class GoogleAppointmentStyle extends AppointmentStyle {

	public GoogleAppointmentStyle(String border, String background) {
		super();
		
		//set the border
		this.border = border;
		this.selectedBorder = border;
		
		//set the body text
		this.text = "#FFFFFF";
		this.selectedText = text;
		
		//set the header text
		this.headerText = text;
		this.selectedHeaderText = text;
		
		//set the background colors
		this.background = background;
		this.selectedBackground = background;
		
		//set the header colors to the same color as the border
		this.backgroundHeader = border;
		this.selectedBackgroundHeader = border;
		
	}
	
	
}
