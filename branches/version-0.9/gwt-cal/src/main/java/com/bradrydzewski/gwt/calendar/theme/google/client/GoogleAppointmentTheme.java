package com.bradrydzewski.gwt.calendar.theme.google.client;

import java.util.HashMap;
import java.util.Map;

import com.bradrydzewski.gwt.calendar.client.AppointmentStyleEnum;

public class GoogleAppointmentTheme {
                                                                           //border,   background
    public static final GoogleAppointmentStyle BLUE   = new GoogleAppointmentStyle("#2952A3","#668CD9");
    public static final GoogleAppointmentStyle RED    = new GoogleAppointmentStyle("#A32929","#D96666");
    public static final GoogleAppointmentStyle PINK   = new GoogleAppointmentStyle("#B1365F","#E67399");
    public static final GoogleAppointmentStyle PURPLE = new GoogleAppointmentStyle("#7A367A","#B373B3");
    public static final GoogleAppointmentStyle DARK_PURPLE = new GoogleAppointmentStyle("#5229A3","#8C66D9");
    public static final GoogleAppointmentStyle STEELE_BLUE = new GoogleAppointmentStyle("#29527A","#29527A");
    public static final GoogleAppointmentStyle LIGHT_BLUE  = new GoogleAppointmentStyle("#1B887A","#59BFB3");
    public static final GoogleAppointmentStyle TEAL        = new GoogleAppointmentStyle("#28754E","#65AD89");
    public static final GoogleAppointmentStyle LIGHT_TEAL  = new GoogleAppointmentStyle("#4A716C","#85AAA5");
    public static final GoogleAppointmentStyle GREEN       = new GoogleAppointmentStyle("#0D7813","#4CB052");
    public static final GoogleAppointmentStyle LIGHT_GREEN = new GoogleAppointmentStyle("#528800","#8CBF40");
    public static final GoogleAppointmentStyle YELLOW_GREEN = new GoogleAppointmentStyle("#88880E","#BFBF4D");
    public static final GoogleAppointmentStyle YELLOW       = new GoogleAppointmentStyle("#AB8B00","#E0C240");
    public static final GoogleAppointmentStyle ORANGE       = new GoogleAppointmentStyle("#BE6D00","#F2A640");
    public static final GoogleAppointmentStyle RED_ORANGE   = new GoogleAppointmentStyle("#B1440E","#E6804D");
    public static final GoogleAppointmentStyle LIGHT_BROWN  = new GoogleAppointmentStyle("#865A5A","#BE9494");
    public static final GoogleAppointmentStyle LIGHT_PURPLE = new GoogleAppointmentStyle("#705770","#A992A9");
    public static final GoogleAppointmentStyle GREY         = new GoogleAppointmentStyle("#4E5D6C","#8997A5");
    public static final GoogleAppointmentStyle BLUE_GREY    = new GoogleAppointmentStyle("#5A6986","#94A2bE");
    public static final GoogleAppointmentStyle YELLOW_GREY  = new GoogleAppointmentStyle("#6E6E41","#A7A77D");
    public static final GoogleAppointmentStyle BROWN        = new GoogleAppointmentStyle("#8D6F47","#C4A883");
    public static final GoogleAppointmentStyle DEFAULT = BLUE;
    public static Map<AppointmentStyleEnum, GoogleAppointmentStyle> STYLES;
    
    static {
		STYLES = new HashMap<AppointmentStyleEnum, GoogleAppointmentStyle>();
		STYLES.put(AppointmentStyleEnum.BLUE, BLUE);
		STYLES.put(AppointmentStyleEnum.BLUE_GREY, BLUE_GREY);
		STYLES.put(AppointmentStyleEnum.BROWN, BROWN);
		STYLES.put(AppointmentStyleEnum.DARK_PURPLE, DARK_PURPLE);
		STYLES.put(AppointmentStyleEnum.GREEN, GREEN);
		STYLES.put(AppointmentStyleEnum.GREY, GREY);
		STYLES.put(AppointmentStyleEnum.LIGHT_BLUE, LIGHT_BLUE);
		STYLES.put(AppointmentStyleEnum.LIGHT_BROWN, LIGHT_BROWN);
		STYLES.put(AppointmentStyleEnum.LIGHT_GREEN, LIGHT_GREEN);
		STYLES.put(AppointmentStyleEnum.LIGHT_PURPLE, LIGHT_PURPLE);
		STYLES.put(AppointmentStyleEnum.LIGHT_TEAL, LIGHT_TEAL);
		STYLES.put(AppointmentStyleEnum.ORANGE, ORANGE);
		STYLES.put(AppointmentStyleEnum.PINK, PINK);
		STYLES.put(AppointmentStyleEnum.PURPLE, PURPLE);
		STYLES.put(AppointmentStyleEnum.RED, RED);
		STYLES.put(AppointmentStyleEnum.RED_ORANGE, RED_ORANGE);
		STYLES.put(AppointmentStyleEnum.STEELE_BLUE, STEELE_BLUE);
		STYLES.put(AppointmentStyleEnum.TEAL, TEAL);
		STYLES.put(AppointmentStyleEnum.YELLOW, YELLOW);
		STYLES.put(AppointmentStyleEnum.YELLOW_GREEN, YELLOW_GREEN);
		STYLES.put(AppointmentStyleEnum.YELLOW_GREY, YELLOW_GREY);
		STYLES.put(AppointmentStyleEnum.DEFAULT, DEFAULT);
    }
}
