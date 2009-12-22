package com.bradrydzewski.gwt.calendar.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;

@SuppressWarnings("deprecation")
public class CalendarModel {

	/**
	 * The number of weeks normally displayed in a month.
	 */
	public static final int WEEKS_IN_MONTH = 6;

	/**
	 * Number of days normally displayed in a week.
	 */
	public static final int DAYS_IN_WEEK = 7;
	
	/**
	 * Number of hours in a day.
	 */
	public static final int HOURS_IN_DAY = 24;

	public static final String[] WEEKDAY_NAMES = new String[7];
	
	public static final String[] WEEKDAY_ABBREV_NAMES = new String[7];
	
	public static final String[] MONTH_NAMES = new String[32];
	
	public static final String[] HOURS = new String[24];
	
	private static final DateTimeFormat dayOfMonthFormatter = DateTimeFormat
			.getFormat("d");
	
	private static final DateTimeFormat dayOfWeekFormatter = DateTimeFormat
			.getFormat("ccccc");
	
	private static final DateTimeFormat dayOfWeekAbbrevFormatter = DateTimeFormat
			.getFormat("EEE");
	
	public static final DateTimeFormat hourFormatter = DateTimeFormat.getFormat("h");


	public static CalendarModel INSTANCE = new CalendarModel();

   public static final GwtCalMessages MESSAGES = (GwtCalMessages) GWT.create(GwtCalMessages.class);

   public static String AM = "AM";
   public static String PM = "PM";
   public static String NOON = MESSAGES.noon();

	public CalendarModel() {

		// Finding day of week names
		Date date = new Date();
		for (int i = 1; i <= 7; i++) {
			date.setDate(i);
			int dayOfWeek = date.getDay();
			WEEKDAY_NAMES[dayOfWeek] = getDayOfWeekFormatter().format(date);
			WEEKDAY_ABBREV_NAMES[dayOfWeek] = getAbbreviatedDayOfWeekFormatter().format(date);
		}

		// Finding day of month names
		date.setMonth(0);

		for (int i = 1; i < 32; ++i) {
			date.setDate(i);
			MONTH_NAMES[i] = getDayOfMonthFormatter().format(date);
		}

		
		//here we format the hour blocks
		//This is a hack... should use Google's built-in i18n
		//See http://code.google.com/p/gwt-cal/issues/detail?id=18
		DateTimeFormat shortTimeFormat =
			DateTimeFormat.getShortTimeFormat();
		
		date.setHours(12);
		date.setMinutes(0);
		String hour = shortTimeFormat.format(date);
		String hourFormat = "h";
		
		if(!hour.equals("12:00 PM")) {
			NOON = hour;
			AM = "";
			PM = "";
			hourFormat = shortTimeFormat.getPattern();//"HH";
		}

		shortTimeFormat =
			DateTimeFormat.getFormat(hourFormat);
		
		for(int i=0; i<HOURS_IN_DAY;i++) {

			date.setHours(i);
			hour = shortTimeFormat.format(date);
			HOURS[i] = hour;//hour.replaceAll(":00", "");
		}
	}

	/**
	 * Gets the date of month formatter.
	 * 
	 * @return the day of month formatter
	 */
	protected DateTimeFormat getDayOfMonthFormatter() {
		return dayOfMonthFormatter;
	}

	/**
	 * Gets the day of week formatter.
	 * 
	 * @return the day of week formatter
	 */
	protected DateTimeFormat getDayOfWeekFormatter() {
		return dayOfWeekFormatter;
	}
	
	/**
	 * Gets the abbreviated day of week formatter.
	 * 
	 * @return the day of week formatter
	 */
	protected DateTimeFormat getAbbreviatedDayOfWeekFormatter() {
		return dayOfWeekAbbrevFormatter;
	}
}
