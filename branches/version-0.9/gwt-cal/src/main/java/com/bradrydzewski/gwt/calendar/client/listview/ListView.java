package com.bradrydzewski.gwt.calendar.client.listview;

import java.util.ArrayList;
import java.util.Date;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.CalendarWidget;
import com.bradrydzewski.gwt.calendar.client.util.AppointmentUtil;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class ListView extends CalendarWidget {

	private FlexTable appointmentGrid = new FlexTable();
	
	public ListView() {
		setStyleName("gwt-cal-ListView");
		appointmentGrid.setCellPadding(5);
		appointmentGrid.setCellSpacing(0);
		appointmentGrid.setBorderWidth(0);
		appointmentGrid.setWidth("100%");
		rootPanel.add(appointmentGrid);
		DOM.setStyleAttribute(getElement(), "overflowY", "scroll");
	}
	
	@Override
	public void doLayout() {

		appointmentGrid.clear();

        //HERE IS WHERE WE DO THE LAYOUT
        Date tmpDate = (Date) getDate().clone();
        Date today = new Date();
        AppointmentUtil.resetTime(today);
        AppointmentUtil.resetTime(tmpDate);
        int row=0;
        
        for (int i = 0; i < getDays(); i++) {

        	//Filter the list by date
            ArrayList<Appointment> filteredList =
                    AppointmentUtil.filterListByDate(
                    		getAppointments(), tmpDate);


            appointmentGrid.setText(row, 0, 
            		DateTimeFormat.getFormat("EEE MMM d").format(tmpDate));
            
            appointmentGrid.getCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);
            appointmentGrid.getFlexCellFormatter().setRowSpan(row, 0, filteredList.size());
            appointmentGrid.getFlexCellFormatter().setStyleName(row, 0, "dateCell");
            int startingCell = 1;
            
            String rowStyle = (i%2==0)?"row":"row-alt";
            if(tmpDate.equals(today))
            	rowStyle+="-today";
            //appointmentGrid.getRowFormatter().setStyleName(row, rowStyle);
            
            for (Appointment appt : filteredList) {
            	
            	//add the time range
            	String timeSpanString = 
            		DateTimeFormat.getShortTimeFormat().format(appt.getStart()) + " - " + 
            		DateTimeFormat.getShortTimeFormat().format(appt.getEnd());
            	appointmentGrid.setWidget(row, startingCell, new Label(timeSpanString.toLowerCase()));
            	
            	//add the title and description
            	AbsolutePanel titleContainer = new AbsolutePanel();
            	titleContainer.add(new InlineLabel(appt.getTitle()));
            	InlineLabel descLabel = new InlineLabel(" - "+appt.getDescription());
            	descLabel.setStyleName("descriptionLabel");
            	titleContainer.add(descLabel);
            	appointmentGrid.setWidget(row, startingCell+1, titleContainer);
            	
            	//add the detail widget
            	//HARD CODED FOR NOW, NEED TO REMOVE
            	SimplePanel detailContainer = new SimplePanel();
            	detailContainer.setStyleName("detailContainer");
            	AbsolutePanel detailDecorator = new AbsolutePanel();
            	detailDecorator.setStyleName("detailDecorator");
            	titleContainer.add(detailContainer);
            	detailContainer.add(detailDecorator);
            	AbsolutePanel whereRow = new AbsolutePanel();
            	whereRow.add(new InlineLabel("Where: "));
            	whereRow.add(new InlineLabel("~ conference room 118"));
            	detailDecorator.add(whereRow);
            	AbsolutePanel creatorRow = new AbsolutePanel();
            	creatorRow.add(new InlineLabel("Creator: "));
            	creatorRow.add(new InlineLabel("john.smith@gmail.com"));
            	detailDecorator.add(creatorRow);
            	AbsolutePanel whoRow = new AbsolutePanel();
            	whoRow.add(new InlineLabel("Who: "));
            	whoRow.add(new InlineLabel("bob.smith@gmail.com, bill.evans@gmail.com"));
            	detailDecorator.add(whoRow);
            	HTMLPanel moreDetailsRow =
            		new HTMLPanel("more details&#0187;");
            	moreDetailsRow.setStyleName("moreDetailsButton");
            	detailDecorator.add(moreDetailsRow);
            	
            	//Format the Cells
            	appointmentGrid.getCellFormatter().setVerticalAlignment(row, startingCell, HasVerticalAlignment.ALIGN_TOP);
            	appointmentGrid.getCellFormatter().setVerticalAlignment(row, startingCell+1, HasVerticalAlignment.ALIGN_TOP);
            	appointmentGrid.getCellFormatter().setStyleName(row, startingCell, "timeCell");
            	appointmentGrid.getCellFormatter().setStyleName(row, startingCell+1, "titleCell");
            	appointmentGrid.getRowFormatter().setStyleName(row, rowStyle);
            	
            	//increment the row
            	//make sure the starting column is reset to 0
            	startingCell = 0;
            	row++;
            }
            
            //increment the date
            tmpDate.setDate(tmpDate.getDate() + 1);
        }
	}

	@Override
	public int getDays() {
		return 7;
	}
	public void setDays() {
		super.setDays(7);
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
