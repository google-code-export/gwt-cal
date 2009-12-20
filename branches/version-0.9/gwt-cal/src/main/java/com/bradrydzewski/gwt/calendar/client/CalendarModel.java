package com.bradrydzewski.gwt.calendar.client;

import java.util.Date;

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

	public static final String[] WEEKDAY_NAMES = new String[7];
	
	public static final String[] WEEKDAY_ABBREV_NAMES = new String[7];
	
	public static final String[] MONTH_NAMES = new String[32];
	
	private static final DateTimeFormat dayOfMonthFormatter = DateTimeFormat
			.getFormat("d");
	
	private static final DateTimeFormat dayOfWeekFormatter = DateTimeFormat
			.getFormat("ccccc");
	
	private static final DateTimeFormat dayOfWeekAbbrevFormatter = DateTimeFormat
			.getFormat("EEE");

	

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
