package com.bradrydzewski.gwt.calendar.client;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;

/**
 * Set of constants with Appointment styles that gwt-cal uses during
 * the rendering of an {@link com.bradrydzewski.gwt.calendar.client.Appointment}.
 *
 * @author Brad Rydzewski
 */
public class AppointmentStyles {

	/**
	 * Loads the gwt-cal Theme (ie Google, iCal) which determines
	 * available Coloring and styling for Appointments.
	 */
    public static AppointmentTheme THEME = GWT.create(AppointmentTheme.class);

	public static AppointmentStyle BLUE = THEME.getBlueStyle();
	public static AppointmentStyle RED =  THEME.getRedStyle();
	public static AppointmentStyle PINK = THEME.getPinkStyle();
	public static AppointmentStyle PURPLE = THEME.getPurpleStyle();
	public static AppointmentStyle DARK_PURPLE = THEME.getDarkPurpleStyle();
	public static AppointmentStyle STEELE_BLUE = THEME.getSteeleBlueStyle();
	public static AppointmentStyle LIGHT_BLUE = THEME.getLightBlueStyle();
	public static AppointmentStyle TEAL = THEME.getTealStyle();
	public static AppointmentStyle LIGHT_TEAL = THEME.getLightTealStyle();
	public static AppointmentStyle GREEN = THEME.getGreenStyle();
	public static AppointmentStyle LIGHT_GREEN = THEME.getLightGreenStyle();
	public static AppointmentStyle YELLOW_GREEN = THEME.getYellowGreenStyle();
	public static AppointmentStyle YELLOW =  THEME.getYellowStyle();
	public static AppointmentStyle ORANGE = THEME.getOrangeStyle();
	public static AppointmentStyle RED_ORANGE = THEME.getRedOrangeStyle();
	public static AppointmentStyle LIGHT_BROWN = THEME.getLightBrownStyle();
	public static AppointmentStyle LIGHT_PURPLE = THEME.getLightPurpleStyle();
	public static AppointmentStyle GREY = THEME.getGreyStyle();
	public static AppointmentStyle BLUE_GREY = THEME.getBlueGreyStyle();
	public static AppointmentStyle YELLOW_GREY = THEME.getYellowGreyStyle();
	public static AppointmentStyle BROWN = THEME.getBrownStyle();

	/**
	 * Map linking an AppointmentStyle to its style name.
	 */
	protected static HashMap<String,AppointmentStyle> STYLE_MAP =
		new HashMap<String,AppointmentStyle>();
	
	static {
		STYLE_MAP.put("BLUE", BLUE);
		STYLE_MAP.put("RED", RED);
		STYLE_MAP.put("PINK", PINK);
		STYLE_MAP.put("PURPLE", PURPLE);
		STYLE_MAP.put("DARK_PURPLE", DARK_PURPLE);
		STYLE_MAP.put("STEELE_BLUE", STEELE_BLUE);
		STYLE_MAP.put("LIGHT_BLUE", LIGHT_BLUE);
		STYLE_MAP.put("TEAL", TEAL);
		STYLE_MAP.put("LIGHT_TEAL", LIGHT_TEAL);
		STYLE_MAP.put("GREEN", GREEN);
		STYLE_MAP.put("LIGHT_GREEN", LIGHT_GREEN);
		STYLE_MAP.put("YELLOW_GREEN", YELLOW_GREEN);
		STYLE_MAP.put("YELLOW", YELLOW);
		STYLE_MAP.put("ORANGE", ORANGE);
		STYLE_MAP.put("RED_ORANGE", RED_ORANGE);
		STYLE_MAP.put("LIGHT_BROWN", LIGHT_BROWN);
		STYLE_MAP.put("LIGHT_PURPLE", LIGHT_PURPLE);
		STYLE_MAP.put("GREY", GREY);
		STYLE_MAP.put("BLUE_GREY", BLUE_GREY);
		STYLE_MAP.put("YELLOW_GREY", YELLOW_GREY);
		STYLE_MAP.put("BROWN", BROWN);
	}

	/**
	 * Get's an AppointmentStyle for the given Style name. The planned use
	 * case for this method is when style names are saved in the database
	 * (as strings) and need to be converted to an AppointmentStyle
	 * when creating Appointment objects.
	 * @param styleName Name of the Style to retrieve.
	 * @return AppointmentStyle for the given name.
	 */
	public static AppointmentStyle forName(String styleName) {
		return STYLE_MAP.get(styleName);
	}
}
