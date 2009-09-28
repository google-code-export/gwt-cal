package com.bradrydzewski.gwt.calendar.client;

import com.bradrydzewski.gwt.calendar.client.listview.ListView;
import com.google.gwt.user.client.Element;

public class Calendar extends CalendarWidget {

	public static final int DAY_VIEW = 0;
	public static final int MONTH_VIEW = 0;
	public static final int LIST_VIEW = 0;
	

	public Calendar() {
		this(DAY_VIEW);
	}
	
	public Calendar(int view) {
		//super(new DayView());
		super();
		//setView(view);
	}
	
	public void setView(int view) {
//		switch(view) {
//			case 0 : {
//				DayView dayView = new DayView();
//				//super.setView(dayView);
//				break;
//			}
//			default : {
//				
//			}
//		}
		
		ListView lv = new ListView();
		this.setStyleName(lv.getStyleName());
		this.view = lv;
		lv.setWidget(this);
		lv.doSizing();
		lv.doLayout();
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
}
 