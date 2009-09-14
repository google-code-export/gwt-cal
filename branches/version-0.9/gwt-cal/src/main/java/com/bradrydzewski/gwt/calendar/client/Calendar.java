package com.bradrydzewski.gwt.calendar.client;

import java.util.Date;

import com.bradrydzewski.gwt.calendar.client.dayview.DayView;

public abstract class Calendar extends CalendarWidget {

	public static final int DAY_VIEW = 0;
	public static final int MONTH_VIEW = 0;
	public static final int LIST_VIEW = 0;
	

	public Calendar() {
		this(DAY_VIEW);
	}

	public Calendar(int view) {
		this(view,3,new Date());
	}
	
	public Calendar(int view, int days, Date date) {
		//super(new DayView());
	}
	
	public void setView(int view) {
		switch(view) {
			case 0 : {
				DayView dayView = new DayView();
				//super.setView(dayView);
				break;
			}
			default : {
				
			}
		}
	}
}
 