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

import com.bradrydzewski.gwt.calendar.client.MonthViewLayout.AppointmentAdapter;
import com.bradrydzewski.gwt.calendar.client.util.AppointmentUtil;
import com.bradrydzewski.gwt.calendar.client.util.FormattingUtil;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * A CalendarView that displays appointments for a given month. The Month
 * is displayed in a grid-style view where cells represents days, columns
 * represents days of the week (i.e. Monday, Tuesday, etc.) and rows
 * represent a full week (Sunday through Saturday).
 * 
 * <h3>CSS Style Rules</h3>
 * <ul class='css'>
 * <li>.gwt-cal-MonthView { }</li>
 * <li>.dayCell { cell that represents a day }</li>
 * <li>.dayCell-today { cell that represents today }</li>
 * <li>.dayCell-disabled { cell's day falls outside the month }</li>
 * <li>.dayCell-today-disabled { cell represents today, falls outside the month }</li>
 * <li>.dayCellLabel { header for the cell } </li>
 * <li>.dayCellLabel-today { cell represents today } </li>
 * <li>.dayCellLabel-disabled { cell's day falls outside the month } </li>
 * <li>.dayCellLabel-today-disabled { cell represents today, falls outside the month } </li>
 * <li>.weekDayLabel { label for the days of the week } </li>
 * </ul>
 * 
 * <h3>Things to fix:</h3>
 * <ol>
 * <li> COMPLETE: Fix the sorting. Sort by Multi-day, then by Date/Time
 * <li> COMPLETE: Hook up the double click function
 * <li> COMPLETE: Doesn't correctly toggle style for selected appointment after doLayout() is called
 * <li> Slightly incorrect calculation of "+X more" appointments, especially when there are all day appts
 * <li> "+X more" doesn't calculate for last day w/ appointments in the view
 * <li> Need method to filter correct list of appointments to display, right now it renders all appointments in the list
 * <li> Styles in IE still screwed up... DAMN IE6/7!!!!
 * <li> Some months span 6 weeks, not 5. Need to account for this
 * <li> Some unit tests would be nice :)
 * </ol>
 * 
 * @author Brad Rydzewski
 * @since 0.9.0
 */
public class MonthView extends CalendarView {

	protected static final Comparator<Appointment> APPOINTMENT_COMPARATOR =
		new Comparator<Appointment>() { //implements Comparator<Appointment>{

	    public int compare(Appointment a1, Appointment a2) {
	        int compare = Boolean.valueOf(a2.isMultiDay()).compareTo(
	        		Boolean.valueOf(a1.isMultiDay()));
	        
	        if(compare==0) {
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
    private final static String[] WEEKDAY_LABELS = new String[] {"Sun","Mon","Tue","Wed","Thurs","Fri","Sat"};


    /**
     * List of adapters that map an appointment to the panels that represent
     * it on the screen.
     */
    private ArrayList<AppointmentAdapter> adapters =
    	new ArrayList<AppointmentAdapter>();

    /**
     * All appointments are placed on the canvas and arranged.
     */
    private FlowPanel appointmentCanvas = new FlowPanel();

    /**
     * The first date displayed on the MonthView (1st cell).
     */
    private Date firstDateDisplayed;

    /**
     * The last date displayed on the MonthView (last cell)
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
     * List of AppointmentAdapter objects that represent the currently
     * selected appointment.
     */
    private ArrayList<AppointmentAdapter> selectedAppointments =
    	new ArrayList<AppointmentAdapter>();


    /**
     * This method is called when the MonthView is attached to the
     * Calendar and displayed. This is where all components are configured
     * and added to the RootPanel.
     */
    public void attach(CalendarWidget widget) {
    	
    	super.attach(widget);
    	
    	//add the month grid
    	calendarWidget.getRootPanel().add(monthCalendarGrid);
		monthCalendarGrid.setCellPadding(0);
		monthCalendarGrid.setBorderWidth(0);
		monthCalendarGrid.setCellSpacing(0);
        monthCalendarGrid.setStyleName(GRID_STYLE);

        //add the appointment canvas
        calendarWidget.getRootPanel().add(appointmentCanvas);
        appointmentCanvas.setStyleName(CANVAS_STYLE);

        //clears the list of selected appointment adapters
        selectedAppointments.clear();
    }

    /**
     * Performs a Layout and arranges all appointments on the MonthView's
     * appointment canvas.
     */
	@Override
	public void doLayout() {
		appointmentCanvas.clear();
		monthCalendarGrid.clear();
		adapters.clear();
		while(monthCalendarGrid.getRowCount()>0) {
			monthCalendarGrid.removeRow(0);
		}
		
		//build the grid
		buildCalendarGrid();
		
		//AppointmentUtil.filterListByDateRange(fullList, date, days)
		
		Collections.sort(
				calendarWidget.getAppointments(),APPOINTMENT_COMPARATOR);
		
		List<AppointmentAdapter> adapterList =
			layoutManager.doLayout(calendarWidget.getAppointments(), 
					firstDateDisplayed, lastDateDisplayed);
		
		int h = monthCalendarGrid.getOffsetHeight() - 20;
		//int w = monthCalendarGrid.getOffsetWidth();
		float cellHeight = (float)h / 5f;
		float cellWidth = 1 / 7f * 100f;
		int apptsPerCell = (int)Math.ceil( (cellHeight-45)/30 );

		

		//for(AppointmentAdapter adapter : adapterList) {
		for(int i=0;i<adapterList.size();i++) {
			AppointmentAdapter adapter = adapterList.get(i);
			
			SimplePanel panel = new SimplePanel();
			panel.add(new Label(adapter.getAppointment().getTitle()));

			String left = ((float)adapter.getColumnStart() / 7f)*100f+.5f + "%";
			String width = ((adapter.getColumnStop()-adapter.getColumnStart()+1) / 7f )*100f-1f + "%";
			float top = (35+(adapter.getRow()*cellHeight)+(Math.abs(FormattingUtil.getBorderOffset())*2) +((22f+Math.abs(FormattingUtil.getBorderOffset())*2)*adapter.getOrder()));//+"px";

			DOM.setStyleAttribute(panel.getElement(), "position", "absolute");
			DOM.setStyleAttribute(panel.getElement(), "top", top+"px" );
			DOM.setStyleAttribute(panel.getElement(), "left", left );
			DOM.setStyleAttribute(panel.getElement(), "width", width );
			if(adapter.getAppointment().isMultiDay())
				panel.setStyleName("multiDayAppointment");
			else
				panel.setStyleName("appointment");
			
			panel.addStyleName(adapter.getAppointment().getStyle());
			
			if(adapter.getAppointment().isSelected()) {
				panel.addStyleName("selected");
				selectedAppointments.add(adapter);
			}
			
			adapter.setAppointmentPanel(panel);
			
			

			
			if(adapter.getOrder()<apptsPerCell) {
				appointmentCanvas.add(panel);
				adapters.add(adapter);
			} else {
				
				
				int row = adapter.getRow();
				int cell = adapter.getColumnStart();
				int count = 0;
				while(true) {

					if(i==adapterList.size())
						break;
					
					if(count>0) {
						i++;
						adapter = adapterList.get(i);
					}
					
					if(adapter.getRow()==row && adapter.getColumnStart()==cell) {
						count++;
					} else {
						i--;
						break;
					}
				}
				
				Label more = new Label("+"+count+" more " + row + ", " + cell);
				more.setWidth((cellWidth)+"%");
				more.setStyleName(MORE_LABEL_STYLE);
				DOM.setStyleAttribute(more.getElement(), "top", (top)+"px");
				DOM.setStyleAttribute(more.getElement(), "left", left);
				
				appointmentCanvas.add(more);
				//left;
				//w
				
				//if(i!=adapterList.size())
				//	i--;
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
	 * Handles the DoubleClick event to determine if an Appointment
	 * has been selected. If an appointment has been double clicked
	 * the OpenEvent will get fired for that appointment.
	 */
	public void onDoubleClick(Element element) {
		
		ArrayList<AppointmentAdapter> list =
			findRelatedAdapters(findAppointmentByElement(element));

		if(list.isEmpty())
			return;

		Appointment appt = list.get(0).getAppointment();
		if(appt.equals(calendarWidget.getSelectedAppointment()))
			calendarWidget.fireOpenEvent(appt);
	}

	/**
	 * Handles the a single click to determine if an appointment has
	 * been selected. If an appointment is clicked it's selected status
	 * will be set to true and a SelectionEvent will be fired.
	 */
	@Override
	public void onMouseDown(Element element) {
		
		//if the appointment container is clicked we can exit out
		if(element.equals(appointmentCanvas.getElement()))
			return;
		
		//check to see if element maps to an appointment adapter
		ArrayList<AppointmentAdapter> list =
			findRelatedAdapters(findAppointmentByElement(element));

		//if no, then exit
		if(list.isEmpty())
			return;
		
		//extract the appointment object
		Appointment appt = list.get(0).getAppointment();
		
		//if the adapters match the selected appointent, exit
		if(calendarWidget.getSelectedAppointment()!=null && 
				calendarWidget.getSelectedAppointment().equals(appt))
			return;
		
		//de-select any previously selected appointments
		if(calendarWidget.getSelectedAppointment()!=null) {
			calendarWidget.getSelectedAppointment().setSelected(false);
			for(AppointmentAdapter selected : selectedAppointments) {
				selected.getAppointmentPanel().removeStyleName("selected");
			}
		}
		
		//set list of selected appointments
		selectedAppointments.clear();
		selectedAppointments = list;
		
		//change style of each adapter to selected
		for(AppointmentAdapter adapter : selectedAppointments) {
			adapter.getAppointmentPanel().addStyleName("selected");
		}
		
		//set selected appointment
		appt.setSelected(true);
		super.setSelectedAppointment(appt, true);

	}


	/**
	 * Builds and formats the Calendar Grid.
	 */
	
	

    @SuppressWarnings("deprecation")
	void buildCalendarGrid(){
        
        //get the date, set to the first date of the month
        Date tmpDate = calendarWidget.getDate();
        tmpDate.setDate(1);
        
        //keep track of the current month being displayed
        int month = tmpDate.getMonth();
        
        //if the 1st of the month is not a Sunday we need to find the prior Sunday
        tmpDate.setDate(tmpDate.getDate() - tmpDate.getDay());
        
        //Store the first date displayed by the calendar for later use
        firstDateDisplayed = (Date)tmpDate.clone();
        
        //Store todays date for later use
        Date today = new Date();
        AppointmentUtil.resetTime(today);
        
        //Add the calendar weekday heading
        for(int i=0;i<7;i++) {
            monthCalendarGrid.setText(0, i, WEEKDAY_LABELS[i]);

            //style the cell
            monthCalendarGrid.getCellFormatter()
                .setVerticalAlignment(0, i, HasVerticalAlignment.ALIGN_TOP);
            monthCalendarGrid.getCellFormatter()
                .setStyleName(0, i, WEEKDAY_LABEL_STYLE);
        }
        
        //add all days to the grid
        //there are 5 rows
        for(int i=1;i<6;i++){
        
            //for each day of the week
            for(int x=0;x<7;x++){
                
                //increment the date by 1
                if(i==1 && x==0){}
                else tmpDate.setDate(tmpDate.getDate() + 1);
                
                //create a label for the date
                Label l = new Label();
                l.setText(tmpDate.getDate()+"");
                
                //set the label style
                //use different styles if the date is not in the current month
                String headerStyle = CELL_HEADER_STYLE;
                String cellStyle = CELL_STYLE;
                if(tmpDate.equals(today)) {
                	headerStyle+="-today";
                	cellStyle+="-today";
                	
                }
                
                if(tmpDate.getMonth()!=month){
                	headerStyle+="-disabled";
                }
                
                l.setStyleName(headerStyle);
                
                //add the label to the cell
                monthCalendarGrid.setWidget(i, x, l);

                //style the cell
                monthCalendarGrid.getCellFormatter()
                    .setVerticalAlignment(i, x, HasVerticalAlignment.ALIGN_TOP);
                monthCalendarGrid.getCellFormatter()
                    .setStyleName(i, x, cellStyle);
            }
            
            //store the last date displayed for later use
            lastDateDisplayed = (Date)tmpDate.clone();
        }  
    }

	/**
	 * Each Appointment drawn on the CalendarView maps to a Widget and
	 * therefore an Element. This method attempts to find an Appointment
	 * based on the provided Element. If no match is found a null value
	 * is returned.
	 * @param element Element to look up.
	 * @return Appointment matching the element.
	 */
	Appointment findAppointmentByElement(Element element) {

		//iterate through list of adapters
		for(AppointmentAdapter adapter : adapters) {
			//if adapter's appointment panel matches element, return appointment
			if(DOM.isOrHasChild(adapter.getAppointmentPanel()
					.getElement(), element)) {
				return adapter.getAppointment();
			}
		}
		//if no matches return null
		return null;
	}

	/**
	 * Finds any related adapters that match the given Appointment.
	 * @param appt Appointment to match.
	 * @return List of related AppointmentAdapter objects.
	 */
	ArrayList<AppointmentAdapter> findRelatedAdapters(Appointment appt) {
		
		//create list for all adapters related to a particular appointment
		ArrayList<AppointmentAdapter> list = new ArrayList<AppointmentAdapter>();
		
		//if the appointment is null return an empty list
		if(appt==null)
			return list;
		
		for(AppointmentAdapter adapter : adapters) {
			if(adapter.getAppointment().equals(appt)) {
				list.add(adapter);
			}
		}	
		return list;
	}
	
	public void onDeleteKeyPressed() {
		if(calendarWidget.getSelectedAppointment()!=null)
			calendarWidget.removeAppointment(calendarWidget.getSelectedAppointment());
	}
	
	
}
