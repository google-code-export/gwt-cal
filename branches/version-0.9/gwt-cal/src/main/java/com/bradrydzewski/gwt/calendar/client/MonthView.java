package com.bradrydzewski.gwt.calendar.client;

import java.util.ArrayList;
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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class MonthView extends CalendarView {

    private FlexTable monthCalendarGrid = new FlexTable();
    private FlowPanel appointmentContainer = new FlowPanel();
    private ScrollPanel scrollPanel = new ScrollPanel();

    private final static String MONTH_VIEW = "gwt-cal-MonthView";
    private final static String MONTH_GRID_STYLE = "calendar-month-grid";
    private final static String MONTH_DAY_CELL_STYLE = "dayCell";
    private final static String MONTH_DAY_CELL_HEADER_STYLE = "dayCellLabel";
    private final static String MONTH_APPT_CONTAINER = "calendar-month-appt-continer";
    private final static String MONTH_WEEKDAY_LABELS = "weekDayLabel";
    private final static String[] WEEKDAY_LABELS = new String[] {"Sun","Mon","Tue","Wed","Thurs","Fri","Sat"};
    
    private ArrayList<AppointmentAdapter> adapters = new ArrayList<AppointmentAdapter>();
    private ArrayList<AppointmentAdapter> selectedAppointments = new ArrayList<AppointmentAdapter>(); 
    
    private Date firstDateDisplayed;
    private Date lastDateDisplayed;
    
    class test implements HasSettings {

		public CalendarSettings getSettings() {
			// TODO Auto-generated method stub
			return CalendarSettings.DEFAULT_SETTINGS;
		}

		public void setSettings(CalendarSettings settings) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    
    public void attach(CalendarWidget widget) {
    	
    	super.attach(widget);
    	
        //add the month calendar to the grid
//    	DayViewBody dvb = new DayViewBody(new test());
//    	dvb.setDays(new Date(), 3);
    	calendarWidget.getRootPanel().add(monthCalendarGrid);
    	//calendarWidget.getRootPanel().add(scrollPanel);
    	//scrollPanel.add(new HTML("<table style='table-layout: fixed;' border='1' cellpadding='0' cellspacing='0'><tr><td style='vertical-align:top;' width='50px'><div style='position:relative;width:100%;'>1234</div></td><td style='vertical-align:top;'><div style='position:relative;height:1440px;'><div style='width:100%;'>abcd<div></div></td></tr></table>"));

		monthCalendarGrid.setCellPadding(0);
		monthCalendarGrid.setBorderWidth(0);
		monthCalendarGrid.setCellSpacing(0);

        //set the main style
        monthCalendarGrid.setStyleName(MONTH_GRID_STYLE);

        //add the calendar appt container
        calendarWidget.getRootPanel().add(appointmentContainer);
        appointmentContainer.setStyleName(MONTH_APPT_CONTAINER);

    }
    
	@Override
	public void doLayout() {
		appointmentContainer.clear();
		monthCalendarGrid.clear();
		adapters.clear();
		while(monthCalendarGrid.getRowCount()>0) {
			monthCalendarGrid.removeRow(0);
		}
		
		//build the grid
		buildCalendarDays();
		
		//Step 1) sort the appointments
		
		//Step 2) get list of appointments in calendar range
		
		//Step 3) begin layout (gonna suck!)
		//     3.a) for each day get appointments
		//     3.b) do i layout each appointment
		//     3.b.1) for multi day may need to loop, multiple panels
		//     3.c)
		
		
		MonthViewLayout mvl = new MonthViewLayout();
		List<AppointmentAdapter> adapterList = mvl.doLayout(calendarWidget.getAppointments(), firstDateDisplayed, lastDateDisplayed);
		
		int height = monthCalendarGrid.getOffsetHeight() - 20;
		float cellHeight = (float)height / 5f;
		
		int offset = 25;

		for(AppointmentAdapter adapter : adapterList) {
		
			SimplePanel panel = new SimplePanel();
			panel.add(new Label(adapter.getAppointment().getTitle()));

			String left = ((float)adapter.getColumnStart() / 7f)*100f+.5f + "%";
			String width = ((adapter.getColumnStop()-adapter.getColumnStart()+1) / 7f )*100f-1f + "%";
			String top = (35+(adapter.getRow()*cellHeight)+(Math.abs(FormattingUtil.getBorderOffset())*2) +((22f+Math.abs(FormattingUtil.getBorderOffset())*2)*adapter.getOrder()))+"px";

			DOM.setStyleAttribute(panel.getElement(), "position", "absolute");
			DOM.setStyleAttribute(panel.getElement(), "top", top );
			DOM.setStyleAttribute(panel.getElement(), "left", left );
			DOM.setStyleAttribute(panel.getElement(), "width", width );
			if(adapter.getAppointment().isMultiDay())
				panel.setStyleName("multiDayAppointment");
			else
				panel.setStyleName("appointment");
			
			panel.addStyleName(adapter.getAppointment().getStyle());
			
			if(adapter.getAppointment().isSelected())
				panel.addStyleName("selected");
			
			adapter.setAppointmentPanel(panel);
			
			if(adapter.getOrder()<3) {
				appointmentContainer.add(panel);
				adapters.add(adapter);
			}
		}
	}

	@Override
	public void doSizing() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStyleName() {
		return MONTH_VIEW;
	}

	@Override
	public void onDeleteKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDoubleClick(Element element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDownArrowKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeftArrowKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseDown(Element element) {
		
		//if the appointment container is clicked we can exit out
		if(element.equals(appointmentContainer.getElement()))
			return;
		
		//make sure we aren't selected something that is already selected
		for(AppointmentAdapter selected : selectedAppointments) {
			if(DOM.isOrHasChild(selected.getAppointmentPanel().getElement(), element)) {
				return;
			}
		}
		
		//Appointment selectedAppointment = null;
		AppointmentAdapter newSelectedAdapter = null;
		
		for(AppointmentAdapter adapter : adapters) {
			if(newSelectedAdapter==null && DOM.isOrHasChild(
					adapter.getAppointmentPanel().getElement(), element)) {
				
				if(calendarWidget.getSelectedAppointment()!=null) {
					calendarWidget.getSelectedAppointment().setSelected(false);
					for(AppointmentAdapter selected : selectedAppointments) {
						selected.getAppointmentPanel().removeStyleName("selected");
					}
				}
				
				selectedAppointments.clear();
				newSelectedAdapter = adapter;

			}
			
			if(newSelectedAdapter!=null && 
					newSelectedAdapter.getAppointment() == adapter.getAppointment()) {
				
				selectedAppointments.add(adapter);
				adapter.getAppointmentPanel().addStyleName("selected");
				
			}

		}
		
		
		//adapter.getAppointmentPanel().addStyleName("gwt-appointment-selected");
		if(newSelectedAdapter!=null) {
			newSelectedAdapter.getAppointment().setSelected(true);
			super.setSelectedAppointment(newSelectedAdapter.getAppointment(), true);
		}
		
		//DOM.isOrHasChild(parent, child)
		
		//1) Loop through list of appointments
		//2) Check if appointment is / has the clicked element
	}

	@Override
	public void onRightArrowKeyPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpArrowKeyPressed() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	

    public void buildCalendarDays(){
        
        //get the date, set to the first date of the month
        Date tmpDate = calendarWidget.getDate();
        tmpDate.setDate(1);
        
        //clone the date, to be used later on
        Date origDate = (Date)tmpDate.clone();
        
        //if the temp date is not a sunday, need to find the first
        //sunday prior to this date
        tmpDate.setDate(tmpDate.getDate() - tmpDate.getDay());
        
        //store the first date displayed by the calendar for later use
        firstDateDisplayed = (Date)tmpDate.clone();
        
        //today's date
        Date today = new Date();
        AppointmentUtil.resetTime(today);
        
        for(int i=0;i<7;i++) {
            monthCalendarGrid.setText(0, i, WEEKDAY_LABELS[i]);

            //style the cell
            monthCalendarGrid.getCellFormatter()
                .setVerticalAlignment(0, i, HasVerticalAlignment.ALIGN_TOP);
            monthCalendarGrid.getCellFormatter()
                .setStyleName(0, i, MONTH_WEEKDAY_LABELS);
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
                String headerStyle = MONTH_DAY_CELL_HEADER_STYLE;
                String cellStyle = MONTH_DAY_CELL_STYLE;
                if(tmpDate.equals(today)) {
                	headerStyle+="-today";
                	cellStyle+="-today";
                	
                }
                
                if(tmpDate.getMonth()!=origDate.getMonth()){
                	headerStyle+="-disabled";
                }
                
                
                l.setStyleName(headerStyle);
                
                //if anything exists in the cell, let's clear
                //if(monthCalendarGrid.getWidget(i, x)!=null){
                   // monthCalendarGrid.clearCell(i,x);
                //}
                
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
}
