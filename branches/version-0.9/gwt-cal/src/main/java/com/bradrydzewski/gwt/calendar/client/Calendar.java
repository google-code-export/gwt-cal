package com.bradrydzewski.gwt.calendar.client;

import com.bradrydzewski.gwt.calendar.client.dayview.DayView;
import com.bradrydzewski.gwt.calendar.client.listview.ListView;
import com.google.gwt.user.client.Element;

public class Calendar extends CalendarWidget {

	
	private DayView dayView = null;
	private ListView listView = null;
	
	public static final int DAY_VIEW = 0;
	public static final int LIST_VIEW = 1;
	public static final int MONTH_VIEW = 2;
	

	public Calendar() {
		this(DAY_VIEW);
	}
	
	public Calendar(int view) {
		//super(new DayView());
		super();
		//setView(view);
	}
	
	public void setView(int view) {
		
		switch(view) {
			
			case 0 : {
				if(dayView==null)
					dayView = new DayView();
				this.view = dayView;
				break;
			}
			case 1 : {
				if(listView==null)
					listView = new ListView();
				this.view = listView;
				break;
			}
			
		}
		
		//clear currently displayed items
		getRootPanel().clear();
		
		//set the style for the calendar, based on the selected view
		setStyleName(this.view.getStyleName());
		
		//attach view to the Calendar
		this.view.setWidget(this);
		//do required sizing and layout
		this.view.doSizing();
		this.view.doLayout();
	}


}
 