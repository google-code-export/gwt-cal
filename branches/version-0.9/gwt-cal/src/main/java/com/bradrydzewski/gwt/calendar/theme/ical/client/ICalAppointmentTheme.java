package com.bradrydzewski.gwt.calendar.theme.ical.client;

import java.util.HashMap;
import java.util.Map;

import com.bradrydzewski.gwt.calendar.client.AppointmentStyleEnum;
import com.google.gwt.core.client.GWT;

public class ICalAppointmentTheme {

	private static final String URL = GWT.getModuleBaseURL()+"/";
    private static final ICalAppointmentStyle BLUE    = new ICalAppointmentStyle("#8DAFEA", "#0D50D5", URL+"blue-appt-gradient.gif");
    private static final ICalAppointmentStyle RED     = new ICalAppointmentStyle("#f76260", "#e3231f", URL+"red-appt-gradient.gif");
    private static final ICalAppointmentStyle PURPLE  = new ICalAppointmentStyle("#aa92ea", "#4b2ca0", URL+"purple-appt-gradient.gif");
    private static final ICalAppointmentStyle GREEN   = new ICalAppointmentStyle("#8EED7F", "#12A300", URL+"green-appt-gradient.gif");
    private static final ICalAppointmentStyle ORANGE  = new ICalAppointmentStyle("#fca550", "#f37b14", URL+"orange-appt-gradient.gif");
    private static final ICalAppointmentStyle FUCHSIA = new ICalAppointmentStyle("#c45cc3", "#b02cae", URL+"fuschia-appt-gradient.gif");
    public static final ICalAppointmentStyle DEFAULT = ORANGE;
    public static Map<AppointmentStyleEnum, ICalAppointmentStyle> STYLES;
    
    static {
		STYLES = new HashMap<AppointmentStyleEnum, ICalAppointmentStyle>();
		STYLES.put(AppointmentStyleEnum.BLUE, BLUE);
		STYLES.put(AppointmentStyleEnum.GREEN, GREEN);
		STYLES.put(AppointmentStyleEnum.LIGHT_PURPLE, FUCHSIA);
		STYLES.put(AppointmentStyleEnum.ORANGE, ORANGE);
		STYLES.put(AppointmentStyleEnum.PURPLE, PURPLE);
		STYLES.put(AppointmentStyleEnum.RED, RED);
		STYLES.put(AppointmentStyleEnum.DEFAULT, DEFAULT);
    }
}

