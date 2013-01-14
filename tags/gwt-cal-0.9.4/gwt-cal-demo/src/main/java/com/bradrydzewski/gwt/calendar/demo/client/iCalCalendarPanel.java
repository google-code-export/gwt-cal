package com.bradrydzewski.gwt.calendar.demo.client;

import java.util.ArrayList;
import java.util.Date;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings.Click;
import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.bradrydzewski.gwt.calendar.client.event.DeleteEvent;
import com.bradrydzewski.gwt.calendar.client.event.DeleteHandler;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickEvent;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickHandler;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class iCalCalendarPanel extends AbsolutePanel {

	private Calendar calendar = null;	
	private DatePicker datePicker = new DatePicker();
	private SimplePanel splitterPanel = new SimplePanel();
	private AbsolutePanel headerPanel = new AbsolutePanel();
	private FlexTable headerPanelLayout = new FlexTable();
	private AbsolutePanel footerPanel = new AbsolutePanel();
	private DockPanel dateLayoutPanel = new DockPanel();
	private DockPanel mainLayoutPanel = new DockPanel();
	private Button todayButton = new Button();
	private Button nextDayButton = new Button();
	private Button previousDayButton = new Button();
	private Button oneDayButton = new Button();
	private Button threeDayButton = new Button();
	private Button weekDayButton = new Button();
	private Button monthButton = new Button();
	private Button activeDayButton = null;
	
	private CalendarSettings settings = new CalendarSettings();
	
	public iCalCalendarPanel() {
		

		//style this element as absolute position
		DOM.setStyleAttribute(this.getElement(), "position", "absolute");
		DOM.setStyleAttribute(this.getElement(), "top", "0px");
		DOM.setStyleAttribute(this.getElement(), "left", "0px");
		DOM.setStyleAttribute(this.getElement(), "padding", "0px");
		
		DOM.setStyleAttribute(DOM.getElementById("messageBox"), "display", "none");
		
		mainLayoutPanel.setWidth("100%");
		add(mainLayoutPanel);
		
		//add header
		headerPanel.setStyleName("gwt-cal-HeaderPanel");
		DOM.setInnerHTML(headerPanel.getElement(), "&nbsp;");
		footerPanel.setStyleName("gwt-cal-FooterPanel");
		DOM.setInnerHTML(headerPanel.getElement(), "&nbsp;");
		mainLayoutPanel.add(headerPanel, DockPanel.NORTH);
		mainLayoutPanel.add(footerPanel,DockPanel.SOUTH);
		
		//add left panel
		datePicker.setValue(new Date());
		dateLayoutPanel.add(datePicker, DockPanel.SOUTH);
		dateLayoutPanel.add(splitterPanel,DockPanel.SOUTH);
		splitterPanel.setStyleName("splitter");
		mainLayoutPanel.add(dateLayoutPanel, DockPanel.WEST);
		
		//CalendarFormat.INSTANCE.setFirstDayOfWeek(1);
		
		//change hour offset to false to facilitate iCal style
		settings.setOffsetHourLabels(true);
		settings.setTimeBlockClickNumber(Click.Double);
		//create day view
		calendar = new Calendar();
		calendar.setSettings(settings);
		//set style as google-cal
		calendar.setWidth("100%");
		//set today as default date
		mainLayoutPanel.add(calendar, DockPanel.CENTER);
		mainLayoutPanel.setCellVerticalAlignment(dateLayoutPanel, HasAlignment.ALIGN_BOTTOM);
		dateLayoutPanel.setCellVerticalAlignment(datePicker, HasAlignment.ALIGN_BOTTOM);
		dateLayoutPanel.setWidth("168px");
		
		
		//add today button
		todayButton.setStyleName("todayButton");
		todayButton.setText("Today");
		todayButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				datePicker.setValue(new Date(), true);
				//dayView.setDate(new Date());
			}
		});
		previousDayButton.setStyleName("previousButton");
		previousDayButton.setHTML("&laquo;");
		previousDayButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Date d = datePicker.getValue();
				d.setDate(d.getDate()-1);
				datePicker.setValue(d,true);
			}
		});
		nextDayButton.setStyleName("nextButton");
		nextDayButton.setHTML("&raquo;");
		nextDayButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				Date d = datePicker.getValue();
				d.setDate(d.getDate()+1);
				datePicker.setValue(d,true);
			}
		});
		headerPanelLayout.setWidget(0,0,todayButton);
		headerPanelLayout.setWidget(0,1,previousDayButton);
		
		oneDayButton.setText("1 Day");
		oneDayButton.setStyleName("dayButton");
		threeDayButton.setText("3 Day");
		threeDayButton.setStyleName("dayButton");
		threeDayButton.addStyleName("active");
		activeDayButton = threeDayButton;
		weekDayButton.setText("Work Week");
		weekDayButton.setStyleName("dayButton");
		monthButton.setText("Month");
		monthButton.setStyleName("dayButton");
		headerPanelLayout.setWidget(0,2,oneDayButton);
		headerPanelLayout.setWidget(0,3,threeDayButton);
		headerPanelLayout.setWidget(0,4,weekDayButton);
		headerPanelLayout.setWidget(0,5,monthButton);
		headerPanelLayout.setWidget(0,6,nextDayButton);
		headerPanelLayout.setHTML(0, 7, "&nbsp;");
		
		headerPanelLayout.getCellFormatter().setWidth(0, 0, "50%");
		headerPanelLayout.getCellFormatter().setWidth(0, 7, "50%");
		
		headerPanelLayout.setWidth("100%");
		headerPanelLayout.setCellPadding(0);
		headerPanelLayout.setCellSpacing(0);
		headerPanel.add(headerPanelLayout);
		
		footerPanel.add(new HTML("<a href='http://code.google.com/p/gwt-cal'>gwt-cal</a> widget for Google Web Toolkit, GPLv3, by <a href='http://www.google.com/profiles/Brad.Rydzewski'>Brad Rydzewski</a>"));
		
		oneDayButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				activeDayButton.removeStyleName("active");
				activeDayButton = oneDayButton;
				activeDayButton.addStyleName("active");
				calendar.setView(CalendarViews.DAY, 1);
				//calendar.scrollToHour(6);
			}
		});
		threeDayButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				activeDayButton.removeStyleName("active");
				activeDayButton = threeDayButton;
				activeDayButton.addStyleName("active");
				calendar.setView(CalendarViews.DAY, 3);
				//calendar.scrollToHour(6);
			}
		});
		weekDayButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				activeDayButton.removeStyleName("active");
				activeDayButton = weekDayButton;
				activeDayButton.addStyleName("active");
				calendar.setView(CalendarViews.DAY, 5);
				//calendar.scrollToHour(6);
			}
		});
		monthButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				activeDayButton.removeStyleName("active");
				activeDayButton = monthButton;
				activeDayButton.addStyleName("active");
				calendar.setView(CalendarViews.MONTH);
			}
		});
		
		datePicker.addValueChangeHandler(new ValueChangeHandler<Date>(){
			public void onValueChange(ValueChangeEvent<Date> event) {
				calendar.setDate(event.getValue());
			}
		});
		calendar.addDeleteHandler(new DeleteHandler<Appointment>(){
			public void onDelete(DeleteEvent<Appointment> event) {
				boolean commit = Window.confirm("Are you sure you want to delete appointment \"" + event.getTarget().getTitle() + "\"");
				if(commit==false) {
					event.setCancelled(true);
					System.out.println("cancelled appointment deletion");
				}
			
			}
			
		});
		calendar.addOpenHandler(new OpenHandler<Appointment>(){
			public void onOpen(OpenEvent<Appointment> event) {
				Window.alert("You double-clicked appointment \"" + event.getTarget().getTitle() + "\"");
			}
			
		});
		
		calendar.addSelectionHandler(new SelectionHandler<Appointment>(){
			public void onSelection(SelectionEvent<Appointment> event) {
				System.out.println("selected " + event.getSelectedItem().getTitle());
			}
			
		});
		
		calendar.addTimeBlockClickHandler(new TimeBlockClickHandler<Date>(){
			public void onTimeBlockClick(TimeBlockClickEvent<Date> event) {
				Window.alert("you clicked time block " + event.getTarget());
			}
			
		});
		


		
		/* Generate random appointments */
		AppointmentBuilder.appointmentsPerDay = 5;
		AppointmentBuilder.HOURS = new Integer[]{7,8,9,10,11,12,13,14,15,16,17,18,19};
		AppointmentBuilder.MINUTES = new Integer[] {0,30};
		AppointmentBuilder.DURATIONS = new Integer[] {60,90,120,180,240,600};
		AppointmentBuilder.DESCRIPTIONS[1] = "Best show on TV!";
		
		ArrayList<Appointment> appointments = 
			AppointmentBuilder.build(AppointmentBuilder.ICAL_STYLES);
		/* Add appointments to day view */
		calendar.suspendLayout();
		calendar.addAppointments(appointments);

		calendar.resumeLayout();
		
		
		//window events to handle resizing
		Window.enableScrolling(false);
		Window.addResizeHandler(new ResizeHandler(){
			public void onResize(ResizeEvent event) {
				int h = event.getHeight();
				calendar.setHeight(h-headerPanel.getOffsetHeight()-footerPanel.getOffsetHeight()+"px");

			}
		});
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			public void execute() {
				calendar.setHeight(Window.getClientHeight()-headerPanel.getOffsetHeight()-footerPanel.getOffsetHeight()+"px");
				calendar.scrollToHour(6);
			}
		});		
	}
	

}
