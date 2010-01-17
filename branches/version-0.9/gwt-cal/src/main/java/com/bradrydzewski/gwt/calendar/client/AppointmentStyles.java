package com.bradrydzewski.gwt.calendar.client;

import com.google.gwt.core.client.GWT;

/**
 * Set of constants with the names of the CSS styles that gwt-cal uses during
 * the rendering of an {@link com.bradrydzewski.gwt.calendar.client.Appointment}.
 *
 * @author Brad Rydzewski
 */
public class AppointmentStyles {

    public static AppointmentTheme THEME = GWT.create(AppointmentTheme.class);

    protected static final String STYLE_PREFIX = "gwt-appointment-";
    public static final String BLUE = STYLE_PREFIX + "blue";
    public static final String RED = STYLE_PREFIX + "red";
    public static final String PINK = STYLE_PREFIX + "pink";
    public static final String PURPLE = STYLE_PREFIX + "purple";
    public static final String DARK_PURPLE = STYLE_PREFIX + "darkpurple";
    public static final String STEELE_BLUE = STYLE_PREFIX + "steelblue";
    public static final String LIGHT_BLUE = STYLE_PREFIX + "lightblue";
    public static final String TEAL = STYLE_PREFIX + "teal";
    public static final String LIGHT_TEAL = STYLE_PREFIX + "lightteal";
    public static final String GREEN = STYLE_PREFIX + "green";
    public static final String LIGHT_GREEN = STYLE_PREFIX + "light";
    public static final String YELLOW_GREEN = STYLE_PREFIX + "yellowgreen";
    public static final String YELLOW = STYLE_PREFIX + "yellow";
    public static final String ORANGE = STYLE_PREFIX + "orange";
    public static final String RED_ORANGE = STYLE_PREFIX + "redorange";
    public static final String LIGHT_BROWN = STYLE_PREFIX + "lightbrown";
    public static final String LIGHT_PURPLE = STYLE_PREFIX + "lightpurple";
    public static final String GREY = STYLE_PREFIX + "grey";
    public static final String BLUE_GREY = STYLE_PREFIX + "bluegrey";
    public static final String YELLOW_GREY = STYLE_PREFIX + "yellowgrey";
    public static final String BROWN = STYLE_PREFIX + "brown";
}
