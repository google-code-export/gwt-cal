package com.bradrydzewski.gwt.calendar.theme.ical.client;

import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;
import com.bradrydzewski.gwt.calendar.client.AppointmentTheme;
import com.google.gwt.core.client.GWT;

public class ICalAppointmentTheme extends AppointmentTheme {

	private static final String URL = GWT.getModuleBaseURL()+"/";
    private static final AppointmentStyle BLUE    = new ICalAppointmentStyle("#8DAFEA", "#0D50D5", URL+"blue-appt-gradient.gif");
    private static final AppointmentStyle RED     = new ICalAppointmentStyle("#f76260", "#e3231f", URL+"red-appt-gradient.gif");
    private static final AppointmentStyle PURPLE  = new ICalAppointmentStyle("#aa92ea", "#4b2ca0", URL+"purple-appt-gradient.gif");
    private static final AppointmentStyle GREEN   = new ICalAppointmentStyle("#8EED7F", "#12A300", URL+"green-appt-gradient.gif");
    private static final AppointmentStyle ORANGE  = new ICalAppointmentStyle("#fca550", "#f37b14", URL+"orange-appt-gradient.gif");
    private static final AppointmentStyle FUCHSIA = new ICalAppointmentStyle("#c45cc3", "#b02cae", URL+"fuschia-appt-gradient.gif");
    private static final AppointmentStyle DEFAULT = BLUE;

	@Override
	public AppointmentStyle getBlueStyle() {
		return BLUE;
	}

	@Override
	public AppointmentStyle getGreenStyle() {
		return GREEN;
	}

	@Override
	public AppointmentStyle getLightPurpleStyle() {
		return FUCHSIA;
	}

	@Override
	public AppointmentStyle getOrangeStyle() {
		return ORANGE;
	}

	@Override
	public AppointmentStyle getPurpleStyle() {
		return PURPLE;
	}

	@Override
	public AppointmentStyle getRedStyle() {
		return RED;
	}


	/*
	 * Below are styles not implemented in the iCal
	 * theme. They will all use the default Blue theme.
	 */

	@Override
	public AppointmentStyle getBlueGreyStyle() {
		return DEFAULT;
	}

	@Override
	public AppointmentStyle getBrownStyle() {
		return DEFAULT;
	}

	@Override
	public AppointmentStyle getDarkPurpleStyle() {
		return DEFAULT;
	}

	@Override
	public AppointmentStyle getGreyStyle() {
		return DEFAULT;
	}

	@Override
	public AppointmentStyle getLightBlueStyle() {
		return DEFAULT;
	}

	@Override
	public AppointmentStyle getLightBrownStyle() {
		return DEFAULT;
	}

	@Override
	public AppointmentStyle getLightGreenStyle() {
		return DEFAULT;
	}

	@Override
	public AppointmentStyle getLightTealStyle() {
		return DEFAULT;
	}

	@Override
	public AppointmentStyle getPinkStyle() {
		return DEFAULT;
	}

	@Override
	public AppointmentStyle getRedOrangeStyle() {
		return DEFAULT;
	}

	@Override
	public AppointmentStyle getSteeleBlueStyle() {
		return DEFAULT;
	}

	@Override
	public AppointmentStyle getTealStyle() {
		return DEFAULT;
	}

	@Override
	public AppointmentStyle getYellowGreenStyle() {
		return DEFAULT;
	}

	@Override
	public AppointmentStyle getYellowGreyStyle() {
		return DEFAULT;
	}

	@Override
	public AppointmentStyle getYellowStyle() {
		return DEFAULT;
	}
}

