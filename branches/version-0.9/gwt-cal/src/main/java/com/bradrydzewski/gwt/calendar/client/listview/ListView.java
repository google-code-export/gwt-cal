package com.bradrydzewski.gwt.calendar.client.listview;

import java.util.ArrayList;
import java.util.Date;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.CalendarWidget;
import com.bradrydzewski.gwt.calendar.client.util.AppointmentUtil;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

public class ListView extends CalendarWidget {

	private FlexTable appointmentGrid = new FlexTable();
	
	public ListView() {
		setStyleName("gwt-cal-ListView");
		rootPanel.add(appointmentGrid);
		DOM.setStyleAttribute(getElement(), "overflowY", "scroll");
	}
	
	@Override
	public void doLayout() {

		appointmentGrid.clear();

        //HERE IS WHERE WE DO THE LAYOUT
        Date tmpDate = (Date) getDate().clone();
        int row=0;
        
        for (int i = 0; i < getDays(); i++) {

        	//Filter the list by date
            ArrayList<Appointment> filteredList =
                    AppointmentUtil.filterListByDate(
                    		getAppointments(), tmpDate);

            Label hdrLabel = new Label(tmpDate.toString());
            DOM.setStyleAttribute(hdrLabel.getElement(), "backgroundColor", "#000999");
            appointmentGrid.setWidget(row, 0, hdrLabel);
            appointmentGrid.setBorderWidth(1);
            appointmentGrid.getFlexCellFormatter().setRowSpan(row, 0, filteredList.size());
            int startingCell = 1;
            for (Appointment appt : filteredList) {
            	
            	appointmentGrid.setWidget(row, startingCell, new Label("S: " +appt.getStart().toString()));
            	appointmentGrid.setWidget(row, startingCell+1, new Label("E: " +appt.getEnd().toString()));
            	appointmentGrid.setWidget(row, startingCell+2, new Label("T: " + appt.getTitle()));

            	startingCell = 0;
            	row++;
            }
            
            
            tmpDate.setDate(tmpDate.getDate() + 1);
        }
	}

	@Override
	public void doSizing() {

	}

	@Override
	public void onDeleteKeyPressed() {

	}

	@Override
	public void onDoubleClick(Element element) {

	}

	@Override
	public void onDownArrowKeyPressed() {

	}

	@Override
	public void onLeftArrowKeyPressed() {

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
