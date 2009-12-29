package com.bradrydzewski.gwt.calendar.client.dayview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings;
import com.bradrydzewski.gwt.calendar.client.CalendarView;
import com.bradrydzewski.gwt.calendar.client.CalendarWidget;
import com.bradrydzewski.gwt.calendar.client.HasSettings;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings.Click;
import com.bradrydzewski.gwt.calendar.client.util.AppointmentUtil;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;

public class DayView extends CalendarView implements HasSettings {

	private DayViewHeader dayViewHeader = null;
	private DayViewBody dayViewBody = null;
	private DayViewMultiDayBody multiViewBody = null;
	private DayViewLayoutStrategy layoutStrategy = null;

	private List<AppointmentWidget> appointmentWidgets = new ArrayList<AppointmentWidget>();
	/**
	 * List of AppointmentAdapter objects that represent the currently selected
	 * appointment.
	 */
	private ArrayList<AppointmentWidget> selectedAppointmentWidgets = new ArrayList<AppointmentWidget>();

	public DayView() {

	}


	public void doLayout() {

		// PERFORM APPOINTMENT LAYOUT NOW
		Date d = (Date) calendarWidget.getDate().clone();

		multiViewBody.setDays((Date) d.clone(), calendarWidget.getDays());
		dayViewHeader.setDays((Date) d.clone(), calendarWidget.getDays());
		dayViewHeader.setYear((Date) d.clone());
		dayViewBody.setDays((Date) d.clone(), calendarWidget.getDays());
		dayViewBody.getTimeline().prepare();

		// LAYOUT THE VIEW NOW
		// view.doSizing(this);
		// doSizing(widget);

		this.selectedAppointmentWidgets.clear();
		appointmentWidgets.clear();
		// widgetAppointmentIndex.clear();
		// appointmentWidgetIndex.clear();

		// PROBLEMS ARE
		// 1) Month View may actually show different dates,
		// such as days before the 1st and after the last
		// day of month. Push this to the "VIEW"?????
		// 2) This works great for Absolute Positioning, such as the
		// DayView and MonthView, but not so well for the
		// ListView which is relative positioning... how to handle???
		// 3) How to handle scenarios where multiple appointments are "grouped"
		// such as a MS Outlook Resource / Booking view????? FUCK!!!
		// Or is this a separate widget all together???
		// 4) How to handle scenarios where appointments need to be grouped by
		// the calendar??

		// HERE IS WHERE WE DO THE LAYOUT
		Date tmpDate = (Date) calendarWidget.getDate().clone();

		for (int i = 0; i < calendarWidget.getDays(); i++) {

			ArrayList<Appointment> filteredList = AppointmentUtil
					.filterListByDate(calendarWidget.getAppointments(), tmpDate);

			// perform layout
			ArrayList<AppointmentAdapter> appointmentAdapters = layoutStrategy
					.doLayout(filteredList, i, calendarWidget.getDays());

			// add all appointments back to the grid
			// CHANGE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			for (AppointmentAdapter appt : appointmentAdapters) {

		    	AppointmentWidget panel = new AppointmentWidget();
		    	panel.addStyleName(appt.getAppointment().getStyle());
		    	panel.setWidth(appt.getWidth());
		    	panel.setHeight(appt.getHeight());
		    	panel.setTitle(appt.getAppointment().getTitle());
		    	panel.setTop(appt.getTop());
		    	panel.setLeft(appt.getLeft());
				panel.setAppointment(appt.getAppointment());
				panel.setDescription(appt.getAppointment().getDescription());
				dayViewBody.getGrid().grid.add(panel);

				if (calendarWidget.isTheSelectedAppointment(panel
						.getAppointment())) {
					panel.addStyleName("gwt-appointment-selected");
					selectedAppointmentWidgets.add(panel);
				}
				appointmentWidgets.add(panel);
			}

			tmpDate.setDate(tmpDate.getDate() + 1);
		}
		
        ArrayList<Appointment> filteredList =
            AppointmentUtil.filterListByDateRange(calendarWidget.getAppointments(),
            		calendarWidget.getDate(), calendarWidget.getDays());
        
        ArrayList<AppointmentAdapter> adapterList = new ArrayList<AppointmentAdapter>();
        int desiredHeight = layoutStrategy.doMultiDayLayout(filteredList,
        		adapterList, calendarWidget.getDate(), calendarWidget.getDays());
        
        multiViewBody.grid.setHeight(desiredHeight + "px");
        
        for (AppointmentAdapter appt : adapterList) {
        	
	    	AppointmentWidget panel = new AppointmentWidget();
	    	panel.addStyleName(appt.getAppointment().getStyle());
	    	panel.setWidth(appt.getWidth());
	    	panel.setHeight(appt.getHeight());
	    	panel.setTitle(appt.getAppointment().getTitle());
	    	panel.setTop(appt.getTop());
	    	panel.setLeft(appt.getLeft());
			panel.setAppointment(appt.getAppointment());
			panel.setMultiDay(true);

			dayViewBody.getGrid().grid.add(panel);

			if (calendarWidget.isTheSelectedAppointment(panel
					.getAppointment())) {
				panel.addStyleName("gwt-appointment-selected");
				selectedAppointmentWidgets.add(panel);
			}
			appointmentWidgets.add(panel);
            this.multiViewBody.grid.add(panel);
        }
	}

	@Override
    public void scrollToHour(int hour) {
        dayViewBody.getScrollPanel().setScrollPosition(hour *
                getSettings().getIntervalsPerHour() * getSettings().getPixelsPerInterval());
    }	
	
	
	public void doSizing() {

		if (calendarWidget.getOffsetHeight() > 0) {
			dayViewBody.setHeight(calendarWidget.getOffsetHeight() - 2
					- dayViewHeader.getOffsetHeight()
					- multiViewBody.getOffsetHeight() + "px");
			// System.out.println("doComponentLayout called");
		}

		// multiViewBody.grid.setHeight(desiredHeight + "px");
	}

	public void onDeleteKeyPressed() {
		//System.out.println("onDeleteKeyPressed: "+calendarWidget.getSelectedAppointment());
		if(calendarWidget.getSelectedAppointment()!=null)
			calendarWidget.fireDeleteEvent(calendarWidget.getSelectedAppointment());
	}

	public void onDoubleClick(Element element, Event event) {

		ArrayList<AppointmentWidget> list = findAppointmentWidgetsByElement(element);
		if (!list.isEmpty()) {
			Appointment appt = list.get(0).getAppointment();
			//if (appt.equals(calendarWidget.getSelectedAppointment()))
				calendarWidget.fireOpenEvent(appt);
			// exit out

		} else if (getSettings().getTimeBlockClickNumber() == Click.Double
				&& element == dayViewBody.getGrid().gridOverlay.getElement()) {
			int x = DOM.eventGetClientX(event);
			int y = DOM.eventGetClientY(event);
			timeBlockClick(x, y);
		}

	}

	public void onSingleClick(Element element, Event event) {

		// Ignore the scoll panel
		if (dayViewBody.getScrollPanel().getElement().equals(element)) {
			return;
		}

        Appointment appt =
        	findAppointmentByElement(element);
        
        if(appt!=null) {
        	selectAppointment(appt);
        } else if (getSettings().getTimeBlockClickNumber() == Click.Single
				&& element == dayViewBody.getGrid().gridOverlay
				.getElement()) {
			int x = DOM.eventGetClientX(event);
			int y = DOM.eventGetClientY(event);
			timeBlockClick(x, y);
		}
	}
	
	
	@Override
	public void onAppointmentSelected(Appointment appt) {

		ArrayList<AppointmentWidget> clickedAppointmentAdapters =
			findAppointmentWidget(appt);

		if (!clickedAppointmentAdapters.isEmpty()) {
			Appointment clickedAppt = clickedAppointmentAdapters.get(0)
					.getAppointment();

			//if (!calendarWidget.isTheSelectedAppointment(clickedAppt)) {

				//if (calendarWidget.hasAppointmentSelected()) {
				//	calendarWidget.resetSelectedAppointment();
					for (AppointmentWidget adapter : selectedAppointmentWidgets) {
						adapter.removeStyleName("gwt-appointment-selected");
					}
				//}

				for (AppointmentWidget adapter : clickedAppointmentAdapters) {

					adapter.addStyleName("gwt-appointment-selected");
				}

				selectedAppointmentWidgets.clear();
				selectedAppointmentWidgets = clickedAppointmentAdapters;

				//clickedAppt.setSelected(true);
				DOM.scrollIntoView(clickedAppointmentAdapters.get(0).getElement());
				
			//}
		}
	}

	public void onRightArrowKeyPressed() {
		calendarWidget.selectNextAppointment();
	}

	public void onUpArrowKeyPressed() {
		calendarWidget.selectPreviousAppointment();
	}

	public void onDownArrowKeyPressed() {
		calendarWidget.selectNextAppointment();
	}

	public void onLeftArrowKeyPressed() {
		calendarWidget.selectPreviousAppointment();
	}

	public CalendarSettings getSettings() {
		// REMOVE THIS, this should not be here
		return super.calendarWidget.getSettings();
	}

	public void setSettings(CalendarSettings settings) {
		// REMOVE THIS, this should not be here
	}

	@Override
	public String getStyleName() {
		return "gwt-cal";
	}

	@Override
	public void attach(CalendarWidget widget) {
		super.attach(widget);

		if (dayViewBody == null) {
			dayViewBody = new DayViewBody(this);
			dayViewHeader = new DayViewHeader(this);
			layoutStrategy = new DayViewLayoutStrategy(this);
			multiViewBody = new DayViewMultiDayBody(this);
		}

		calendarWidget.getRootPanel().add(dayViewHeader);
		calendarWidget.getRootPanel().add(multiViewBody);
		calendarWidget.getRootPanel().add(dayViewBody);
		
		if(getSettings()!=null)
			scrollToHour(getSettings().getScrollToHour());
	}

	void timeBlockClick(int x, int y) {

		int left = dayViewBody.getGrid().gridOverlay.getAbsoluteLeft();
		int top = dayViewBody.getScrollPanel().getAbsoluteTop();
		int width = dayViewBody.getGrid().gridOverlay.getOffsetWidth();
		int scrollOffset = dayViewBody.getScrollPanel().getScrollPosition();

		// x & y are based on screen position,need to get x/y relative to
		// component
		int relativeY = y - top + scrollOffset;
		int relativeX = x - left;

		// find the interval clicked and day clicked
		double interval = Math.floor(relativeY
				/ (double) getSettings().getPixelsPerInterval());
		double day = Math.floor((double) relativeX
				/ ((double) width / (double) calendarWidget.getDays()));

		// create new appointment date based on click
		Date newStartDate = calendarWidget.getDate();
		newStartDate.setHours(0);
		newStartDate.setMinutes(0);
		newStartDate.setSeconds(0);
		newStartDate.setMinutes((int) interval
				* (60 / getSettings().getIntervalsPerHour()));
		newStartDate.setDate(newStartDate.getDate() + (int) day);

		calendarWidget.fireTimeBlockClickEvent(newStartDate);
	}

	private ArrayList<AppointmentWidget> findAppointmentWidgetsByElement(
			Element element) {
		return findAppointmentWidget(findAppointmentByElement(element));
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
		for (AppointmentWidget widget : appointmentWidgets) {
			if (DOM.isOrHasChild(widget.getElement(), element)) {
				appointmentAtElement = widget.getAppointment();
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
	 * @return List of related AppointmentWidget objects.
	 */
	private ArrayList<AppointmentWidget> findAppointmentWidget(Appointment appt) {
		ArrayList<AppointmentWidget> appointmentAdapters = new ArrayList<AppointmentWidget>();
		if (appt != null) {
			for (AppointmentWidget widget : appointmentWidgets) {
				if (widget.getAppointment().equals(appt)) {
					appointmentAdapters.add(widget);
				}
			}
		}
		return appointmentAdapters;
	}



}
