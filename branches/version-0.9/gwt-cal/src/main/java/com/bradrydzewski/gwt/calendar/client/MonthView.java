/*
 * This file is part of gwt-cal
 * Copyright (C) 2009  Scottsdale Software LLC
 * 
 * gwt-cal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/
 */

package com.bradrydzewski.gwt.calendar.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.GridConstrainedDropController;
import com.bradrydzewski.gwt.calendar.client.monthview.AppointmentStackingManager;
import com.bradrydzewski.gwt.calendar.client.monthview.DayLayoutDescription;
import com.bradrydzewski.gwt.calendar.client.monthview.MonthLayoutDescription;
import com.bradrydzewski.gwt.calendar.client.monthview.WeekLayoutDescription;
import com.bradrydzewski.gwt.calendar.client.monthview.WeekTopStackableDescription;
import com.bradrydzewski.gwt.calendar.client.util.AppointmentUtil;
import com.bradrydzewski.gwt.calendar.client.util.FormattingUtil;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * A CalendarView that displays appointments for a given month. The Month is
 * displayed in a grid-style view where cells represents days, columns
 * represents days of the week (i.e. Monday, Tuesday, etc.) and rows represent a
 * full week (Sunday through Saturday).
 * <p/>
 * <h3>CSS Style Rules</h3> <ul class='css'>
 * <li>.gwt-cal-MonthView { }</li>
 * <li>.dayCell { cell that represents a day }</li>
 * <li>.dayCell-today { cell that represents today }</li>
 * <li>.dayCell-disabled { cell's day falls outside the month }</li>
 * <li>.dayCell-today-disabled { cell represents today, falls outside the month
 * }</li>
 * <li>.dayCellLabel { header for the cell }</li>
 * <li>.dayCellLabel-today { cell represents today }</li>
 * <li>.dayCellLabel-disabled { cell's day falls outside the month }</li>
 * <li>.dayCellLabel-today-disabled { cell represents today, falls outside the
 * month }</li>
 * <li>.weekDayLabel { label for the days of the week }</li> </ul>
 * <p/>
 * <h3>Things to fix:</h3>
 * <ol>
 * <li>COMPLETE: Fix the sorting. Sort by Multi-day, then by Date/Time
 * <li>COMPLETE: Hook up the double click function
 * <li>COMPLETE: Doesn't correctly toggle style for selected appointment after
 * doLayout() is called
 * <li>Slightly incorrect calculation of "+X more" appointments, especially when
 * there are all day appts
 * <li>"+X more" doesn't calculate for last day w/ appointments in the view
 * <li>Need method to filter correct list of appointments to display, right now
 * it renders all appointments in the list
 * <li>Styles in IE still screwed up... DAMN IE6/7!!!!
 * <li>Some months span 6 weeks, not 5. Need to account for this
 * <li>Some unit tests would be nice :)
 * </ol>
 * 
 * @author Brad Rydzewski
 * @since 0.9.0
 */
public class MonthView extends CalendarView {

    public class AppointmentAdapter {
    	
    	private Appointment appointment;
    	private Widget panel;
    	
    	public AppointmentAdapter(Appointment appointment, Widget panel) {
    		this.appointment = appointment;
    		this.panel = panel;
    	}

		public Appointment getAppointment() {
			return appointment;
		}

		public Widget getPanel() {
			return panel;
		}
    }

	public static final Comparator<Appointment> APPOINTMENT_COMPARATOR = new Comparator<Appointment>() {

		public int compare(Appointment a1, Appointment a2) {
			int compare = Boolean.valueOf(a2.isMultiDay()).compareTo(
					a1.isMultiDay());

			if (compare == 0) {
				compare = a1.getStart().compareTo(a2.getStart());
			}

			if (compare == 0) {
				compare = a2.getEnd().compareTo(a1.getEnd());
			}

			return compare;
		}
	};

	private final static String MONTH_VIEW = "gwt-cal-MonthView";
	private final static String CANVAS_STYLE = "canvas";
	private final static String GRID_STYLE = "grid";
	private final static String CELL_STYLE = "dayCell";
	private final static String MORE_LABEL_STYLE = "moreAppointments";
	private final static String CELL_HEADER_STYLE = "dayCellLabel";
	private final static String WEEKDAY_LABEL_STYLE = "weekDayLabel";
	private final static String[] WEEKDAY_LABELS = new String[] { "Sun", "Mon",
			"Tue", "Wed", "Thurs", "Fri", "Sat" };

	private static final int DAYS_IN_A_WEEK = 7;

	/**
	 * List of adapters that map an appointment to the panels that represent it
	 * on the screen.
	 */
	private ArrayList<AppointmentAdapter> appointmentsAdapters = new ArrayList<AppointmentAdapter>();

	/**
	 * All appointments are placed on this canvas and arranged.
	 */
	private AbsolutePanel appointmentCanvas = new AbsolutePanel();

	/**
	 * The first date displayed on the MonthView (1st cell.) This date is not
	 * necessarily the first date of the month as the month view will sometimes
	 * display days from the adjacent months because of the number of days
	 * fitting in the visible grid.
	 */
	private Date firstDateDisplayed;

	/**
	 * The last date displayed on the MonthView (last cell.) This date is not
	 * necessarily the last date of the month as the month view will sometimes
	 * display days from the adjacent months because of the number of days
	 * fitting in the visible grid.
	 */
	private Date lastDateDisplayed;

	/**
	 * Layout manager use to arrange the appointments on the canvas.
	 */
	private MonthViewLayout layoutManager = new MonthViewLayout();

	/**
	 * Grid that makes up the days and weeks of the MonthView.
	 */
	private FlexTable monthCalendarGrid = new FlexTable();

	/**
	 * The number of rows required to display the entire month in grid format.
	 * Although most months span a total of five weeks, there are some months
	 * that span six weeks.
	 */
	private int monthViewRequiredRows = 5;

	/**
	 * List of AppointmentAdapter objects that represent the currently selected
	 * appointment.
	 */
	private ArrayList<AppointmentAdapter> selectedAppointmentAdapters = new ArrayList<AppointmentAdapter>();

	private PickupDragController dragController = null;

	/**
	 * This method is called when the MonthView is attached to the Calendar and
	 * displayed. This is where all components are configured and added to the
	 * RootPanel.
	 */
	public void attach(CalendarWidget widget) {
		super.attach(widget);

		calendarWidget.addToRootPanel(monthCalendarGrid);
		monthCalendarGrid.setCellPadding(0);
		monthCalendarGrid.setBorderWidth(0);
		monthCalendarGrid.setCellSpacing(0);
		monthCalendarGrid.setStyleName(GRID_STYLE);

		calendarWidget.addToRootPanel(appointmentCanvas);
		appointmentCanvas.setStyleName(CANVAS_STYLE);

		selectedAppointmentAdapters.clear();

		if (dragController == null) {
			dragController = new PickupDragController(appointmentCanvas, true);
		}

		/*
		 * Need to re-set appointmentCanvas to position:absolute because gwt-dnd
		 * will set it to relative, but then the layout gets f***ed up
		 */
		DOM.setStyleAttribute(appointmentCanvas.getElement(), "position",
				"absolute");

		dragController.setBehaviorDragStartSensitivity(5);
		dragController.setBehaviorDragProxy(true);
		
	    // instantiate our drop controller
		GridConstrainedDropController gridConstrainedDropController =
			new GridConstrainedDropController(appointmentCanvas,500,500);
	    dragController.registerDropController(gridConstrainedDropController);

	}

	/**
	 * Performs a Layout and arranges all appointments on the MonthView's
	 * appointment canvas.
	 */
	@Override
	public void doLayout() {
		
		//Clear all existing appointments
		appointmentCanvas.clear();
		monthCalendarGrid.clear();
		appointmentsAdapters.clear();
		selectedAppointmentAdapters.clear();
		while (monthCalendarGrid.getRowCount() > 0) {
			monthCalendarGrid.removeRow(0);
		}

		//Rebuild the month grid
		buildCalendarGrid();
		
		//(Re)calculate some variables
		calculateCellHeight();
		calculateCellAppointments();

		//Sort the appointments
		//TODO: don't re-sort the appointment unless necessary
		Collections.sort(calendarWidget.getAppointments(),
				APPOINTMENT_COMPARATOR);

		// Send appointments to layout manager
		MonthLayoutDescription monthLayoutDescription = new MonthLayoutDescription(
				firstDateDisplayed, calendarWidget.getAppointments());

		// Get the layouts for each week in the month
		WeekLayoutDescription[] weeks = monthLayoutDescription
				.getWeekDescriptions();

		for (int weekOfMonth = 0; weekOfMonth < weeks.length && weekOfMonth<monthViewRequiredRows; weekOfMonth++) {

			WeekLayoutDescription week = weeks[weekOfMonth];

			if (week != null) {

				AppointmentStackingManager topAppointmentManager = week
						.getTopAppointmentsManager();
				int layer = 0;

				while (true) {

					List<WeekTopStackableDescription> layerDescriptions = topAppointmentManager
							.getDescriptionsInLayer(layer);

					//avoid null pointer
					if (layerDescriptions == null)
						break;

					// can't exceed max # of appointments per cell
					if (layer >= calculatedCellAppointments)
						break;
					
					for (int i = 0; i < layerDescriptions.size(); i++) {

						WeekTopStackableDescription apptDesc = layerDescriptions.get(i);
						Appointment appt = apptDesc.getAppointment();

						//Create the appointment panel
						FocusPanel panel = new FocusPanel();
						panel.add(new Label(appt.getTitle()));
						
						//set the appointments position
						placeItemInGrid(
								panel,apptDesc.getWeekStartDay(),
								apptDesc.getWeekEndDay(),weekOfMonth,layer);
						
						//create an adapters to hold the appointment & panel
						AppointmentAdapter adapter =
							new AppointmentAdapter(appt,panel);
						
						//if a multi-day, set the appropriate style
						if (appt.isMultiDay())
							panel.setStyleName("multiDayAppointment");
						else
							panel.setStyleName("appointment");
						
						//add the appropriate style (blue, red, etc)
						panel.addStyleName(appt.getStyle());
						
						//make draggable
						dragController.makeDraggable(panel);
						
						//if selected, set the style & add the adapter
						// to the list of selected appointments
						if (appt.equals(calendarWidget.getSelectedAppointment())) {
							adapter.getPanel().addStyleName("selected");
							selectedAppointmentAdapters.add(adapter);
						}
						
						//add the adapter to the list of adapters
						appointmentsAdapters.add(adapter);
						
						//draw appointment to the canvas
						appointmentCanvas.add(panel);
					}

					layer++;
				}

				// get appointments for each day of the week
				for (int dayOfWeek = 0; dayOfWeek < DAYS_IN_A_WEEK; dayOfWeek++) {

					int maxMultiDay = topAppointmentManager
							.singleDayLowestOrder(dayOfWeek);

					// get the layout data for the specified day of the week
					DayLayoutDescription dayLayoutDescription = week
							.getDayLayoutDescription(dayOfWeek);

					if (dayLayoutDescription != null) {

						int count = dayLayoutDescription.getAppointments().size();
						
						// for each appointment that falls on the given day,
						// need to paint to the panel
						for (int i = 0; i < count; i++) {

							Appointment appt = dayLayoutDescription
									.getAppointments().get(i);

							//check if we exceeded max # appts per cell
							//if yes, add label "+ X more"
							if (maxMultiDay + i >= calculatedCellAppointments) {

								Label more = new Label("+" + (count-i) + " more");
								more.setStyleName(MORE_LABEL_STYLE);
								placeItemInGrid(
										more,dayOfWeek,
										dayOfWeek,weekOfMonth,calculatedCellAppointments);
								appointmentCanvas.add(more);
								break;
							}

							//Create the appointment panel
							FocusPanel panel = new FocusPanel();
							panel.add(new Label(appt.getTitle()));
							
							//set the appointments position
							placeItemInGrid(
									panel,dayOfWeek,
									dayOfWeek,weekOfMonth,i + maxMultiDay);
							
							//create an adapters to hold the appointment & panel
							AppointmentAdapter adapter =
								new AppointmentAdapter(appt,panel);
							
							//set the panel style name
							panel.setStyleName("appointment");
							
							//add the appropriate style (blue, red, etc)
							panel.addStyleName(appt.getStyle());
							
							//make draggable
							dragController.makeDraggable(panel);
							
							//if selected, set the style & add the adapter
							// to the list of selected appointments
							if (appt.equals(calendarWidget.getSelectedAppointment())) {
								adapter.getPanel().addStyleName("selected");
								selectedAppointmentAdapters.add(adapter);
							}
							
							//add the adapter to the list of adapters
							appointmentsAdapters.add(adapter);
							
							//draw appointment to the canvas
							appointmentCanvas.add(panel);
						}
					}
				}
			}
		}
	}

	/**
	 * Gets the Month View's primary style name.
	 */
	public String getStyleName() {
		return MONTH_VIEW;
	}

	/**
	 * Handles the DoubleClick event to determine if an Appointment has been
	 * selected. If an appointment has been double clicked the OpenEvent will
	 * get fired for that appointment.
	 */
	public void onDoubleClick(Element clickedElement) {
		ArrayList<AppointmentAdapter> list = findAllAdaptersForAppointmentOnElement(clickedElement);
		if (!list.isEmpty()) {
			Appointment appt = list.get(0).getAppointment();
			if (appt.equals(calendarWidget.getSelectedAppointment()))
				calendarWidget.fireOpenEvent(appt);
		}
	}

	/**
	 * Handles the a single click to determine if an appointment has been
	 * selected. If an appointment is clicked it's selected status will be set
	 * to true and a SelectionEvent will be fired.
	 */
	@Override
	public void onMouseDown(Element clickedElement) {
		if (clickedElement.equals(appointmentCanvas.getElement()))
			return;
		
		ArrayList<AppointmentAdapter> clickedAppointmentAdapters = findAllAdaptersForAppointmentOnElement(clickedElement);

		if (!clickedAppointmentAdapters.isEmpty()) {
			Appointment clickedAppt = clickedAppointmentAdapters.get(0)
					.getAppointment();

			if (!calendarWidget.isTheSelectedAppointment(clickedAppt)) {

				if (calendarWidget.hasAppointmentSelected()) {
					calendarWidget.resetSelectedAppointment();
					for (AppointmentAdapter adapter : selectedAppointmentAdapters) {
						adapter.getPanel().removeStyleName(
								"selected");
					}
				}

				for (AppointmentAdapter adapter : clickedAppointmentAdapters) {
					
					adapter.getPanel().addStyleName("selected");
				}

				selectedAppointmentAdapters.clear();
				selectedAppointmentAdapters = clickedAppointmentAdapters;

				clickedAppt.setSelected(true);
				super.setSelectedAppointment(clickedAppt, true);
			}
		}
	}

	private ArrayList<AppointmentAdapter> findAllAdaptersForAppointmentOnElement(
			Element element) {
		return findAppointmentAdapters(findAppointmentByElement(element));
	}

	/**
	 * Builds and formats the Calendar Grid. No appointments are included when
	 * building the grid.
	 */
	@SuppressWarnings("deprecation")
	private void buildCalendarGrid() {
		Date date = calendarWidget.getDate();
		date.setDate(1);

		int month = date.getMonth();

		/* If the 1st of the month not Sunday we need to find the prior */
		date.setDate(date.getDate() - date.getDay());

		firstDateDisplayed = (Date) date.clone();

		Date today = new Date();
		AppointmentUtil.resetTime(today);

		/* Add the calendar weekday heading */
		for (int i = 0; i < DAYS_IN_A_WEEK; i++) {
			monthCalendarGrid.setText(0, i, WEEKDAY_LABELS[i]);
			monthCalendarGrid.getCellFormatter().setVerticalAlignment(0, i,
					HasVerticalAlignment.ALIGN_TOP);
			monthCalendarGrid.getCellFormatter().setStyleName(0, i,
					WEEKDAY_LABEL_STYLE);
		}

		monthViewRequiredRows = MonthViewHelper.monthViewRequiredRows(date);
		for (int monthGridRowIndex = 1; monthGridRowIndex <= monthViewRequiredRows; monthGridRowIndex++) {
			for (int dayOfWeekIndex = 0; dayOfWeekIndex < DAYS_IN_A_WEEK; dayOfWeekIndex++) {

				if (monthGridRowIndex != 1 || dayOfWeekIndex != 0) {
					date.setDate(date.getDate() + 1);
				}

				configureDayInGrid(monthGridRowIndex, dayOfWeekIndex, String
						.valueOf(date.getDate()), date.equals(today), date
						.getMonth() != month);
			}

			lastDateDisplayed = (Date) date.clone();
		}
	}

	/**
	 * Configures a single day in the month grid of this <code>MonthView</code>.
	 * 
	 * @param row
	 *            The row in the grid on which the day will be set
	 * @param col
	 *            The col in the grid on which the day will be set
	 * @param text
	 *            The heading in the day cell, i.e. the day number
	 * @param isToday
	 *            Indicates whether the day corresponds to today in the month
	 *            view
	 * @param notInCurrentMonth
	 *            Indicates whether the day is in the current visualized month
	 *            or belongs to any of the two adjacent months of the current
	 *            month
	 */
	private void configureDayInGrid(int row, int col, String text,
			boolean isToday, boolean notInCurrentMonth) {
		Label label = new Label(text);

		StringBuilder headerStyle = new StringBuilder(CELL_HEADER_STYLE);
		StringBuilder cellStyle = new StringBuilder(CELL_STYLE);
		if (isToday) {
			headerStyle.append("-today");
			cellStyle.append("-today");
		}

		if (notInCurrentMonth) {
			headerStyle.append("-disabled");
		}

		label.setStyleName(headerStyle.toString());

		monthCalendarGrid.setWidget(row, col, label);
		monthCalendarGrid.getCellFormatter().setVerticalAlignment(row, col,
				HasVerticalAlignment.ALIGN_TOP);
		monthCalendarGrid.getCellFormatter().setStyleName(row, col,
				cellStyle.toString());

	}

	/**
	 * Returns the {@link Appointment} indirectly associated to the passed
	 * <code>element</code>. Each Appointment drawn on the CalendarView maps to
	 * a Widget and therefore an Element. This method attempts to find an
	 * Appointment based on the provided Element. If no match is found a null
	 * value is returned.
	 * 
	 * @param element
	 *            Element to look up.
	 * @return Appointment matching the element.
	 */
	private Appointment findAppointmentByElement(Element element) {
		Appointment appointmentAtElement = null;
		for (AppointmentAdapter adapter : appointmentsAdapters) {
			if (DOM.isOrHasChild(adapter.getPanel().getElement(),
					element)) {
				appointmentAtElement = adapter.getAppointment();
				break;
			}
		}
		return appointmentAtElement;
	}

	/**
	 * Finds any related adapters that match the given Appointment.
	 * 
	 * @param appt
	 *            Appointment to match.
	 * @return List of related AppointmentAdapter objects.
	 */
	private ArrayList<AppointmentAdapter> findAppointmentAdapters(
			Appointment appt) {
		ArrayList<AppointmentAdapter> appointmentAdapters = new ArrayList<AppointmentAdapter>();
		if (appt != null) {
			for (AppointmentAdapter adapter : appointmentsAdapters) {
				if (adapter.getAppointment().equals(appt)) {
					appointmentAdapters.add(adapter);
				}
			}
		}
		return appointmentAdapters;
	}

	public void onDeleteKeyPressed() {
		calendarWidget.removeCurrentlySelectedAppointment();
	}



	//HERE ARE A BUNCH OF CALCULATED VALUES THAT ARE USED DURING LAYOUT
	// NOT SURE IF THE VARIABLES SHOULD BE KEPT AT THE CLASS LEVEL
	// OR AT THE METHOD LEVEL
	
	private int calculatedWeekDayHeaderHeight;
	private int calculatedDayHeaderHeight;

	/**
	 * Maximum appointments per cell (day).
	 */
	private int calculatedCellAppointments;
	
	/**
	 * Height of each Cell (day), including
	 * the day's header.
	 */
	private float calculatedCellOffsetHeight;

	/**
	 * Height of each Cell (day), excluding
	 * the day's header.
	 */
	private float calculatedCellHeight;

	/**
	 * Calculates the height of each day cell in the Month grid.
	 * It excludes the height of each day's header, as well
	 * as the overall header that shows the weekday labels.
	 * @return
	 */
	private void calculateCellHeight() {
		
		int gridHeight = monthCalendarGrid.getOffsetHeight();
		int weekdayRowHeight = monthCalendarGrid.getRowFormatter().getElement(0).getOffsetHeight();
		int dayHeaderHeight = monthCalendarGrid.getFlexCellFormatter().getElement(1, 0).getFirstChildElement().getOffsetHeight();
		
		calculatedCellOffsetHeight = (float)(gridHeight-weekdayRowHeight) / monthViewRequiredRows;
		calculatedCellHeight = calculatedCellOffsetHeight - dayHeaderHeight;
		calculatedWeekDayHeaderHeight = weekdayRowHeight;
		calculatedDayHeaderHeight = dayHeaderHeight;
	}

	/**
	 * Calculates the maximum number of appointments that can be displayed
	 * in a given "day cell".
	 */
	private void calculateCellAppointments() {
		
		int apptPaddingTop = 1 + (Math.abs(FormattingUtil.getBorderOffset()) * 3);
		int apptPaddingBottom = 0;
		int apptHeight = 20; //TODO: calculate appointment height dynamically
		
		calculatedCellAppointments = (int)Math.floor(
				(float)(calculatedCellHeight-apptPaddingTop)/(float)(apptHeight + apptPaddingTop))-1;
	}

	private void placeItemInGrid(
			Widget panel, int colStart, int colEnd, int row, int cellPosition) {
		
		int apptPaddingTop = 1 + (Math.abs(FormattingUtil.getBorderOffset()) * 3);
		int apptHeight = 20; //TODO: calculate appointment height dynamically
		
		float left = (float)colStart / (float)DAYS_IN_A_WEEK * 100f + .5f;
		
		
		float width = ((float)(colEnd - colStart + 1) / (float)DAYS_IN_A_WEEK) * 100f - 1f;

		float top = calculatedWeekDayHeaderHeight + 
					(row * calculatedCellOffsetHeight) +
					calculatedDayHeaderHeight +
					apptPaddingTop +
					(cellPosition*(apptHeight+apptPaddingTop));
		
		//System.out.println(calculatedWeekDayHeaderHeight + " + (" + row + " * " + calculatedCellOffsetHeight + ") + " + calculatedDayHeaderHeight + " + " + apptPaddingTop + " + (" + cellPosition+"*("+apptHeight+"+"+apptPaddingTop + "));");
		
		DOM.setStyleAttribute(panel.getElement(),"position", "absolute");
		DOM.setStyleAttribute(panel.getElement(), "top",top + "px");
		DOM.setStyleAttribute(panel.getElement(), "left",left + "%");
		DOM.setStyleAttribute(panel.getElement(), "width",width + "%");
	}
}