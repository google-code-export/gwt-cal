package com.bradrydzewski.gwt.calendar.client.listview;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.Attendee;
import com.bradrydzewski.gwt.calendar.client.CalendarWidget;
import com.bradrydzewski.gwt.calendar.client.util.AppointmentUtil;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.ui.Widget;

public class ListView extends CalendarWidget {

	private FlexTable appointmentGrid = new FlexTable();
	private HashMap<Widget,Widget> appointmentLabelToDetailMap = new HashMap<Widget,Widget>();
	private HashMap<Widget,Widget> timelineLabelToDetailMap = new HashMap<Widget,Widget>();
	
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

		appointmentLabelToDetailMap.clear();
		timelineLabelToDetailMap.clear();
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
            	Label timeSpanLabel = new Label(timeSpanString.toLowerCase());
            	appointmentGrid.setWidget(row, startingCell, timeSpanLabel);
            	
            	timeSpanLabel.addClickHandler(appointmentTimeSpanClickHandler);
            	
            	
            	//add the title and description
            	AbsolutePanel titleContainer = new AbsolutePanel();
            	InlineLabel titleLabel = new InlineLabel(appt.getTitle());
            	titleContainer.add(titleLabel);
            	InlineLabel descLabel = new InlineLabel(" - "+appt.getDescription());
            	descLabel.setStyleName("descriptionLabel");
            	titleContainer.add(descLabel);
            	appointmentGrid.setWidget(row, startingCell+1, titleContainer);
            	
            	titleLabel.addClickHandler(appointmentTitleClickHandler);
            	
            	//add the detail widget
            	SimplePanel detailContainer = new SimplePanel();
            	detailContainer.setStyleName("detailContainer");
            	AbsolutePanel detailDecorator = new AbsolutePanel();
            	detailDecorator.setStyleName("detailDecorator");
            	titleContainer.add(detailContainer);
            	detailContainer.setVisible(false);
            	detailContainer.add(detailDecorator);
            	
            	if(appt.getLocation()!=null && 
            			!appt.getLocation().isEmpty()) {
	            	AbsolutePanel whereRow = new AbsolutePanel();
	            	InlineLabel whereHeader = new InlineLabel("Where: ");
	            	whereHeader.setStyleName("detailHeader");
	            	whereRow.add(whereHeader);
	            	whereRow.add(new InlineLabel("~ conference room 118"));
	            	detailDecorator.add(whereRow);
            	}
            	if(appt.getCreatedBy()!=null && 
            			!appt.getCreatedBy().isEmpty()) {
	            	AbsolutePanel creatorRow = new AbsolutePanel();
	            	InlineLabel creatorHeader = new InlineLabel("Creator: ");
	            	creatorHeader.setStyleName("detailHeader");
	            	creatorRow.add(creatorHeader);
	            	creatorRow.add(new InlineLabel("john.smith@gmail.com"));
	            	detailDecorator.add(creatorRow);
            	}
            	if(appt.getAttendees()!=null && !appt.getAttendees().isEmpty()) {
	            	AbsolutePanel whoRow = new AbsolutePanel();
	            	InlineLabel whoHeader = new InlineLabel("Who: ");
	            	whoHeader.setStyleName("detailHeader");
	            	whoRow.add(whoHeader);
	            	for(int a=0;a<appt.getAttendees().size();a++) {
	            		Attendee attendee = appt.getAttendees().get(a);
	            		String comma = (a<appt.getAttendees().size()-1)?", ":"";
	            		String labelText = attendee.getEmail() + comma;
	            		whoRow.add(new InlineLabel(labelText));
	            	}
	            	detailDecorator.add(whoRow);
            	}
            	
            	
            	timelineLabelToDetailMap.put(timeSpanLabel, detailContainer);
            	appointmentLabelToDetailMap.put(titleLabel, detailContainer);
            	
            	
            	
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

	
	private ClickHandler appointmentTitleClickHandler = new ClickHandler(){

		public void onClick(ClickEvent event) {
			Widget w = appointmentLabelToDetailMap.get(event.getSource());
			w.setVisible(!w.isVisible());
		}
	};
	private ClickHandler appointmentTimeSpanClickHandler = new ClickHandler(){

		public void onClick(ClickEvent event) {
			Widget w = timelineLabelToDetailMap.get(event.getSource());
			w.setVisible(!w.isVisible());
		}
	};
	
	
	@Override
	public int getDays() {
		return 7;
	}

	@Override
	public void setDays(int days) {
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
