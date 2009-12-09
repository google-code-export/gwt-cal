package com.bradrydzewski.gwt.calendar.client.dayview;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings;
import com.bradrydzewski.gwt.calendar.client.CalendarView;
import com.bradrydzewski.gwt.calendar.client.CalendarWidget;
import com.bradrydzewski.gwt.calendar.client.HasSettings;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings.Click;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickEvent;
import com.bradrydzewski.gwt.calendar.client.util.AppointmentUtil;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

public class DayView extends CalendarView implements HasSettings {

    private DayViewHeader dayViewHeader = null;
    private DayViewBody dayViewBody = null;
    private DayViewMultiDayBody multiViewBody = null;
    private DayViewLayoutStrategy layoutStrategy = null;

    private List<AppointmentWidget> appointmentWidgets = new ArrayList<AppointmentWidget>();
    private Map<Widget, Appointment> widgetAppointmentIndex = new HashMap<Widget, Appointment>();
    private Map<Appointment,Widget[]> appointmentWidgetIndex = new HashMap<Appointment, Widget[]>();

    
	public DayView() {
		


	}

	public void doLayout() {

		
        
        //PERFORM APPOINTMENT LAYOUT NOW
		Date d = (Date) calendarWidget.getDate().clone();
		
        multiViewBody.setDays((Date) d.clone(), calendarWidget.getDays());
        dayViewHeader.setDays((Date) d.clone(), calendarWidget.getDays());
        dayViewHeader.setYear((Date) d.clone());
        dayViewBody.setDays((Date) d.clone(), calendarWidget.getDays());
        dayViewBody.getTimeline().prepare();
        
        //LAYOUT THE VIEW NOW
        //view.doSizing(this);
        //doSizing(widget);
        


        appointmentWidgets.clear();
        widgetAppointmentIndex.clear();
        appointmentWidgetIndex.clear();
        
        //PROBLEMS ARE
        //1) Month View may actually show different dates,
        //         such as days before the 1st and after the last 
        //         day of month. Push this to the "VIEW"?????
        //2) This works great for Absolute Positioning, such as the
        //         DayView and MonthView, but not so well for the
        //         ListView which is relative positioning... how to handle???
        //3) How to handle scenarios where multiple appointments are "grouped"
        //         such as a MS Outlook Resource / Booking view????? FUCK!!!
        //         Or is this a separate widget all together???
        //4) How to handle scenarios where appointments need to be grouped by
        //         the calendar??
        
        //HERE IS WHERE WE DO THE LAYOUT
        Date tmpDate = (Date) calendarWidget.getDate().clone();
        
        for (int i = 0; i < calendarWidget.getDays(); i++) {

            ArrayList<Appointment> filteredList =
                    AppointmentUtil.filterListByDate(calendarWidget.getAppointments(), tmpDate);

            // perform layout
            ArrayList<AppointmentAdapter> appointmentAdapters =
            	layoutStrategy.doLayout(filteredList, i, calendarWidget.getDays());

            // add all appointments back to the grid
            //CHANGE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            for (AppointmentAdapter appt : appointmentAdapters) {
            	
            	AppointmentWidget apptWidget = AppointmentBuilder.getAppointmentWidget(appt);
            	//apptWidget.setStyleName(appt.getAppointment().getStyle());
            	widgetAppointmentIndex.put(apptWidget, appt.getAppointment());
            	appointmentWidgetIndex.put(appt.getAppointment(), new AppointmentWidget[]{apptWidget});
            	appointmentWidgets.add(apptWidget);
            	dayViewBody.getGrid().grid.add(apptWidget);

            }

            tmpDate.setDate(tmpDate.getDate() + 1);
        }
	}
	
	public void doSizing() {
		
        if (calendarWidget.getOffsetHeight() > 0) {
            dayViewBody.setHeight(calendarWidget.getOffsetHeight() - 2 - dayViewHeader.getOffsetHeight() - multiViewBody.getOffsetHeight() + "px");
        //System.out.println("doComponentLayout called");
        }
		
		//multiViewBody.grid.setHeight(desiredHeight + "px");
	}

	public void onDeleteKeyPressed() {
		if(calendarWidget.getSelectedAppointment()!=null)
			calendarWidget.removeAppointment(calendarWidget.getSelectedAppointment());
	}
	public void onDoubleClick(Element element, Event event) {

		Appointment appointment = null;
		AppointmentWidget widget = 
			AppointmentUtil.checkAppointmentElementClicked(
				element, appointmentWidgets);
		
		//If an appointment was not clicked then exit
		if(widget!=null) {
			//Lookup the Appointment
			appointment = widgetAppointmentIndex.get(widget);
		}
		
		if(widget==null || appointment==null) {

			if (getSettings().getTimeBlockClickNumber() == Click.Single &&
					element == dayViewBody.getGrid().gridOverlay.getElement()) {
                int x = DOM.eventGetClientX(event);
                int y = DOM.eventGetClientY(event);
                timeBlockClick(x, y);
			}
			return;
		}
		
		if(appointment == calendarWidget.getSelectedAppointment())
			calendarWidget.fireOpenEvent(appointment);
	}
	public void onMouseDown(Element element, Event event) {
		
		//Ignore the scoll panel
		if(dayViewBody.getScrollPanel().getElement().equals(element)) {
			return;
		}
		
		Appointment appointment = null;
		AppointmentWidget widget = 
			AppointmentUtil.checkAppointmentElementClicked(
				element, appointmentWidgets);
		
		//If an appointment was not clicked then exit
		if(widget!=null) {
			//Lookup the Appointment
			appointment = widgetAppointmentIndex.get(widget);
		}
		
		if(widget==null || appointment==null) {

			if (getSettings().getTimeBlockClickNumber() == Click.Single &&
					element == dayViewBody.getGrid().gridOverlay.getElement()) {
                int x = DOM.eventGetClientX(event);
                int y = DOM.eventGetClientY(event);
                timeBlockClick(x, y);
			}
			return;
		}
		
		setSelectedAppointment(appointment);
		
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

	@Override
	public void setSelectedAppointment(Appointment appointment) {

		Appointment selectedAppointment = calendarWidget.getSelectedAppointment();
		
		if(appointment.equals(selectedAppointment))
			return;

		//Lookup all related Widgets
		Widget[] widgets = appointmentWidgetIndex.get(appointment);

		if(widgets==null)
			return;
		
		//Get the previously selected Widget
		Appointment previouslySelectedAppointment =
			calendarWidget.getSelectedAppointment();
		
		//Set the appointment as selected
		//selectedAppointment = appointment;
		super.setSelectedAppointment(appointment);
		//calendarWidget.setSelectedAppointment(appointment);
		
		//Set all widgets as selected
		for(Widget appointmentWidget : widgets) {
			((AppointmentWidget)appointmentWidget).setSelected(true);
		}
		//Scroll into view
		DOM.scrollIntoView(widgets[0].getElement());
		
		//now we handle the previously selected appointment
		if(previouslySelectedAppointment==null)
			return;
		
		//Lookup the Appointment
		widgets = appointmentWidgetIndex.get(previouslySelectedAppointment);
		for(Widget appointmentWidget : widgets) {
			((AppointmentWidget)appointmentWidget).setSelected(false);
		}
		
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

		if(dayViewBody==null) {
	        dayViewBody = new DayViewBody(this);
	        dayViewHeader = new DayViewHeader(this);
	        layoutStrategy = new DayViewLayoutStrategy(this);
	        multiViewBody = new DayViewMultiDayBody(this);
		}
		
		calendarWidget.getRootPanel().add(dayViewHeader);
		calendarWidget.getRootPanel().add(multiViewBody);
		calendarWidget.getRootPanel().add(dayViewBody);
	}
	
	
	
	
	
	
    void timeBlockClick(int x, int y) {

        int left = dayViewBody.getGrid().gridOverlay.getAbsoluteLeft();
        int top = dayViewBody.getScrollPanel().getAbsoluteTop();
        int width = dayViewBody.getGrid().gridOverlay.getOffsetWidth();
        int scrollOffset = dayViewBody.getScrollPanel().getScrollPosition();

        //x & y are based on screen position,need to get x/y relative to component
        int relativeY = y - top + scrollOffset;
        int relativeX = x - left;

        //find the interval clicked and day clicked
        double interval = Math.floor(relativeY / (double) getSettings().getPixelsPerInterval());
        double day = Math.floor((double) relativeX / ((double) width / (double) calendarWidget.getDays()));

        //create new appointment date based on click
        Date newStartDate = calendarWidget.getDate();
        newStartDate.setHours(0);
        newStartDate.setMinutes(0);
        newStartDate.setSeconds(0);
        newStartDate.setMinutes((int) interval * (60 / getSettings().getIntervalsPerHour()));
        newStartDate.setDate(newStartDate.getDate() + (int) day);

        calendarWidget.fireTimeBlockClickEvent(newStartDate);
    }
}
