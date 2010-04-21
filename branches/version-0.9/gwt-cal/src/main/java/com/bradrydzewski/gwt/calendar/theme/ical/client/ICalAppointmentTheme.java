package com.bradrydzewski.gwt.calendar.theme.ical.client;

import java.util.HashMap;
import java.util.Map;

import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;
import com.google.gwt.core.client.GWT;

public class ICalAppointmentTheme {

	private static final String URL = GWT.getModuleBaseURL()+"/";
    private static final ICalAppointmentStyle BLUE    = new ICalAppointmentStyle("rgba(40,104,222,0.45)" /*"#8DAFEA"*/, "#0D50D5", URL+"blue-appt-gradient.gif",   "#2868de","#5e93f1");
    private static final ICalAppointmentStyle RED     = new ICalAppointmentStyle("rgba(247,98,96,0.45)" /*"#f76260"*/, "#e3231f", URL+"red-appt-gradient.gif",    "#f33734","#f76260");
    private static final ICalAppointmentStyle PURPLE  = new ICalAppointmentStyle("rgba(169,146,234,0.45)" /*"#aa92ea"*/, "#4b2ca0", URL+"purple-appt-gradient.gif", "#6446b4","#aa92ea");
    private static final ICalAppointmentStyle GREEN   = new ICalAppointmentStyle("rgba(69,173,41,0.45)" /*"#8EED7F"*/, "#12A300", URL+"green-appt-gradient.gif",  "#45ad29","#71c15b");
    private static final ICalAppointmentStyle ORANGE  = new ICalAppointmentStyle("rgba(246,134,30,0.45)" /*"#fca550"*/, "#f37b14", URL+"orange-appt-gradient.gif", "#f6861e","#fca550");
    private static final ICalAppointmentStyle FUCHSIA = new ICalAppointmentStyle("rgba(187,72,185,0.45)" /*"#c45cc3"*/, "#b02cae", URL+"fuschia-appt-gradient.gif","#bb48b9","#c45cc3");
    public static final ICalAppointmentStyle DEFAULT = ORANGE;
    public static Map<AppointmentStyle, ICalAppointmentStyle> STYLES;
    
    static {
		STYLES = new HashMap<AppointmentStyle, ICalAppointmentStyle>();
		STYLES.put(AppointmentStyle.BLUE, BLUE);
		STYLES.put(AppointmentStyle.GREEN, GREEN);
		STYLES.put(AppointmentStyle.LIGHT_PURPLE, FUCHSIA);
		STYLES.put(AppointmentStyle.ORANGE, ORANGE);
		STYLES.put(AppointmentStyle.PURPLE, PURPLE);
		STYLES.put(AppointmentStyle.RED, RED);
		STYLES.put(AppointmentStyle.DEFAULT, DEFAULT);
    }
}

