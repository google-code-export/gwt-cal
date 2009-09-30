package com.bradrydzewski.gwt.calendar.client;

import java.util.Date;

import com.bradrydzewski.gwt.calendar.client.dayview.DayViewBody;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;

public class MonthView extends CalendarView {

    private FlexTable monthCalendarGrid = new FlexTable();
    private FlowPanel appointmentContainer = new FlowPanel();
    private ScrollPanel scrollPanel = new ScrollPanel();

    private final static String MONTH_VIEW = "gwt-cal-MonthView";
    private final static String MONTH_GRID_STYLE = "calendar-month-grid";
    private final static String MONTH_DAY_HEADER_STYLE = "calendar-month-day-header";
    private final static String MONTH_DAY_CELL_STYLE = "calendar-month-cell";
    private final static String MONTH_DAY_CELL_HEADER_STYLE = "calendar-month-header";
    private final static String MONTH_DAY_CELL_HEADER_INACTIVE_STYLE = "calendar-month-header-disabled";
    private final static String MONTH_APPT_CONTAINER = "calendar-month-appt-continer";

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
        appointmentContainer.add(new Label("asdf"));
        appointmentContainer.setStyleName(MONTH_APPT_CONTAINER);

    }
    
	@Override
	public void doLayout() {
		//appointmentContainer.clear();
		monthCalendarGrid.clear();
		while(monthCalendarGrid.getRowCount()>0) {
			monthCalendarGrid.removeRow(0);
		}
//		for(int i=monthCalendarGrid.getRowCount()-1;i>=0;i--){
//			monthCalendarGrid.removeRow(i);
//		}
		
		//build the grid
		buildCalendarDays();
		
		
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
		// TODO Auto-generated method stub
		
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
                if(tmpDate.getMonth()==origDate.getMonth()){
                    l.setStyleName(MONTH_DAY_CELL_HEADER_STYLE);
                }else{
                    l.setStyleName(MONTH_DAY_CELL_HEADER_INACTIVE_STYLE);
                }
                
                //if anything exists in the cell, let's clear
                //if(monthCalendarGrid.getWidget(i, x)!=null){
                   // monthCalendarGrid.clearCell(i,x);
                //}
                
                //add the label to the cell
                monthCalendarGrid.setWidget(i-1, x, l);
                

                
                //style the cell
                monthCalendarGrid.getCellFormatter()
                    .setVerticalAlignment(i-1, x, HasVerticalAlignment.ALIGN_TOP);
                monthCalendarGrid.getCellFormatter()
                    .setStyleName(i-1, x, MONTH_DAY_CELL_STYLE);
                

            }
        }  
    }
}
