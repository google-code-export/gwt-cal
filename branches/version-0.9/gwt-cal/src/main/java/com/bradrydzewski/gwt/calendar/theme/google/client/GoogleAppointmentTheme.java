package com.bradrydzewski.gwt.calendar.theme.google.client;

import com.bradrydzewski.gwt.calendar.client.AppointmentStyle;
import com.bradrydzewski.gwt.calendar.client.AppointmentTheme;

public class GoogleAppointmentTheme extends AppointmentTheme {
                                                                           //border,   background
    public static final AppointmentStyle BLUE   = new GoogleAppointmentStyle("#2952A3","#668CD9");
    public static final AppointmentStyle RED    = new GoogleAppointmentStyle("#A32929","#D96666");
    public static final AppointmentStyle PINK   = new GoogleAppointmentStyle("#B1365F","#E67399");
    public static final AppointmentStyle PURPLE = new GoogleAppointmentStyle("#7A367A","#B373B3");
    public static final AppointmentStyle DARK_PURPLE = new GoogleAppointmentStyle("#5229A3","#8C66D9");
    public static final AppointmentStyle STEELE_BLUE = new GoogleAppointmentStyle("#29527A","#29527A");
    public static final AppointmentStyle LIGHT_BLUE  = new GoogleAppointmentStyle("#1B887A","#59BFB3");
    public static final AppointmentStyle TEAL        = new GoogleAppointmentStyle("#28754E","#65AD89");
    public static final AppointmentStyle LIGHT_TEAL  = new GoogleAppointmentStyle("#4A716C","#85AAA5");
    public static final AppointmentStyle GREEN       = new GoogleAppointmentStyle("#0D7813","#4CB052");
    public static final AppointmentStyle LIGHT_GREEN = new GoogleAppointmentStyle("#528800","#8CBF40");
    public static final AppointmentStyle YELLOW_GREEN = new GoogleAppointmentStyle("#88880E","#BFBF4D");
    public static final AppointmentStyle YELLOW       = new GoogleAppointmentStyle("#AB8B00","#E0C240");
    public static final AppointmentStyle ORANGE       = new GoogleAppointmentStyle("#BE6D00","#F2A640");
    public static final AppointmentStyle RED_ORANGE   = new GoogleAppointmentStyle("#B1440E","#E6804D");
    public static final AppointmentStyle LIGHT_BROWN  = new GoogleAppointmentStyle("#865A5A","#BE9494");
    public static final AppointmentStyle LIGHT_PURPLE = new GoogleAppointmentStyle("#705770","#A992A9");
    public static final AppointmentStyle GREY         = new GoogleAppointmentStyle("#4E5D6C","#8997A5");
    public static final AppointmentStyle BLUE_GREY    = new GoogleAppointmentStyle("#5A6986","#94A2bE");
    public static final AppointmentStyle YELLOW_GREY  = new GoogleAppointmentStyle("#6E6E41","#A7A77D");
    public static final AppointmentStyle BROWN        = new GoogleAppointmentStyle("#8D6F47","#C4A883");

	@Override
	public AppointmentStyle getBlueGreyStyle() {
		return BLUE_GREY;
	}

	@Override
	public AppointmentStyle getBlueStyle() {
		return BLUE;
	}

	@Override
	public AppointmentStyle getBrownStyle() {
		return BROWN;
	}

	@Override
	public AppointmentStyle getDarkPurpleStyle() {
		return DARK_PURPLE;
	}

	@Override
	public AppointmentStyle getGreenStyle() {
		return GREEN;
	}

	@Override
	public AppointmentStyle getGreyStyle() {
		return GREY;
	}

	@Override
	public AppointmentStyle getLightBlueStyle() {
		return LIGHT_BLUE;
	}

	@Override
	public AppointmentStyle getLightBrownStyle() {
		return LIGHT_BROWN;
	}

	@Override
	public AppointmentStyle getLightGreenStyle() {
		return LIGHT_GREEN;
	}

	@Override
	public AppointmentStyle getLightPurpleStyle() {
		return LIGHT_PURPLE;
	}

	@Override
	public AppointmentStyle getLightTealStyle() {
		return LIGHT_TEAL;
	}

	@Override
	public AppointmentStyle getOrangeStyle() {
		return ORANGE;
	}

	@Override
	public AppointmentStyle getPinkStyle() {
		return PINK;
	}

	@Override
	public AppointmentStyle getPurpleStyle() {
		return PURPLE;
	}

	@Override
	public AppointmentStyle getRedOrangeStyle() {
		return RED_ORANGE;
	}

	@Override
	public AppointmentStyle getRedStyle() {
		return RED;
	}

	@Override
	public AppointmentStyle getSteeleBlueStyle() {
		return STEELE_BLUE;
	}

	@Override
	public AppointmentStyle getTealStyle() {
		return TEAL;
	}

	@Override
	public AppointmentStyle getYellowGreenStyle() {
		return YELLOW_GREEN;
	}

	@Override
	public AppointmentStyle getYellowGreyStyle() {
		return YELLOW_GREY;
	}

	@Override
	public AppointmentStyle getYellowStyle() {
		return YELLOW;
	}
}
