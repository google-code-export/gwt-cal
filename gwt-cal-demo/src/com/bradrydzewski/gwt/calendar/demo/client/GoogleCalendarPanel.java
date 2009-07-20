package com.bradrydzewski.gwt.calendar.demo.client;

import java.util.Date;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings;
import com.bradrydzewski.gwt.calendar.client.DayView;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DecoratedTabBar;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.datepicker.client.DatePicker;

public class GoogleCalendarPanel extends AbsolutePanel {

	private DayView dayView = null;	
	private DatePicker datePicker = new DatePicker();
	private FlexTable layoutTable = new FlexTable();
	private AbsolutePanel leftPanel = new AbsolutePanel();
	private AbsolutePanel topPanel = new AbsolutePanel();
	private DecoratorPanel dayViewDecorator = new DecoratorPanel();
	private DecoratorPanel datePickerDecorator = new DecoratorPanel();
	private DecoratedTabBar daysTabBar = new DecoratedTabBar();
	
	private CalendarSettings settings = new CalendarSettings();
	
	public GoogleCalendarPanel() {
		
		//style this element as absolute position
		DOM.setStyleAttribute(this.getElement(), "position", "absolute");
		DOM.setStyleAttribute(this.getElement(), "top", "20px");
		DOM.setStyleAttribute(this.getElement(), "left", "0px");
		
		
		//change hour offset to false to facilitate google style
		settings.setOffsetHourLabels(false);
		//create day view
		dayView = new DayView();
		//set style as google-cal
		dayView.setWidth("100%");
		//set today as default date
		datePicker.setValue(new Date());
		datePicker.addValueChangeHandler(new ValueChangeHandler<Date>(){
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				dayView.setDate(event.getValue());
			}
		});
		
		dayView.addValueChangeHandler(new ValueChangeHandler<Appointment>(){

			@Override
			public void onValueChange(ValueChangeEvent<Appointment> event) {
				// TODO Auto-generated method stub
				System.out.println("selected " + event.getValue().getTitle());
			}
			
		});
		
		
		daysTabBar.addTab("1 Day");
		daysTabBar.addTab("3 Day");
		daysTabBar.addTab("Work Week");
		daysTabBar.addTab("Week");
		daysTabBar.selectTab(1);
		daysTabBar.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				int dayIndex = event.getSelectedItem();
				if(dayIndex==0) {
					dayView.setDays(1);
				}
				else if(dayIndex==1) {
					dayView.setDays(3);
				}
				else if(dayIndex==2)
					dayView.setDays(5);
				else if(dayIndex==3)
					dayView.setDays(7);
			}
		});
		topPanel.add(daysTabBar);
		topPanel.setStyleName("daysTabBar");
		
		leftPanel.setStyleName("leftPanel");
		leftPanel.add(datePickerDecorator);
		datePickerDecorator.add(datePicker);
		dayViewDecorator.add(dayView);
		layoutTable.setWidth("99%");
		layoutTable.setCellPadding(0);
		layoutTable.setCellSpacing(0);
		layoutTable.setText(0, 0, "");
		layoutTable.setWidget(0, 1, topPanel);
		layoutTable.setWidget(1, 1, dayViewDecorator);
		layoutTable.setWidget(1, 0, leftPanel);
		layoutTable.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
		layoutTable.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_TOP);
		layoutTable.getCellFormatter().setWidth(1, 0, "50px");
		add(layoutTable);
		


		//generate random appointments
		generateRandomAppointments();
		
		//window events to handle resizing
		Window.enableScrolling(false);
		Window.addResizeHandler(new ResizeHandler(){
			@Override
			public void onResize(ResizeEvent event) {
				int h = event.getHeight();
				dayView.setHeight(h-85+"px");

			}
		});
		DeferredCommand.addCommand(new Command(){
			@Override
			public void execute() {
				dayView.setHeight(Window.getClientHeight()-85+"px");
			}
		});
		DOM.setStyleAttribute(getElement(), "padding", "10px");
	}
	

	/**
	 * Generate random appoinmentents.
	 */
	public void generateRandomAppointments() {
		
		Integer[] hours = new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
		Integer[] minutes = new Integer[] {0,15,30,45};
		Integer[] durations = new Integer[] {15,30,45,60,90,120,180,240};
		
		
		Date tempDate = new Date();
		tempDate.setHours(0);
		tempDate.setMinutes(0);
		tempDate.setSeconds(0);
		tempDate.setDate(tempDate.getDate()-5);
		
		
		dayView.suspendLayout();
		for(int day=0; day<14; day++) {
			
			for(int a=0; a<8; a++) {
				
				Date start = (Date)tempDate.clone();
				int hour = hours[Random.nextInt(23)];
				int min = minutes[Random.nextInt(3)];
				int dur = durations[Random.nextInt(7)];
				start.setHours(hour);
				start.setMinutes(min);

				Date end = (Date)start.clone();
				end.setMinutes(start.getMinutes() + dur);
				
				//System.out.println("appt start: " + start + "  end: " + end);
				
				String style = (a%3==0)?"gwt-appointment-blue":"gwt-appointment-green";
				Appointment appt = new Appointment();
				appt.setStart(start);
				appt.setEnd(end);
				appt.setTitle("day: " + day + " appt: " + a);
				appt.addStyleName(style);
				dayView.addAppointment(appt);
				
			}
			
			//increment date by +1
			tempDate.setDate(tempDate.getDate()+1);
		}
		
		dayView.resumeLayout();
		

	}

	
}
