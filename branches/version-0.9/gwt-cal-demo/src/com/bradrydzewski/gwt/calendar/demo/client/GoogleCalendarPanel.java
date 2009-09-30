package com.bradrydzewski.gwt.calendar.demo.client;

import java.util.ArrayList;
import java.util.Date;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings;
import com.bradrydzewski.gwt.calendar.client.CalendarSettings.Click;
import com.bradrydzewski.gwt.calendar.client.event.DeleteEvent;
import com.bradrydzewski.gwt.calendar.client.event.DeleteHandler;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickEvent;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickHandler;
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
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedTabBar;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class GoogleCalendarPanel extends FlowPanel {

	private Calendar dayView = null;
	private DatePicker datePicker = new DatePicker();
	private FlexTable layoutTable = new FlexTable();
	private AbsolutePanel leftPanel = new AbsolutePanel();
	private AbsolutePanel topPanel = new AbsolutePanel();
	private DecoratorPanel dayViewDecorator = new DecoratorPanel();
	private DecoratorPanel datePickerDecorator = new DecoratorPanel();
	private DecoratedTabBar daysTabBar = new DecoratedTabBar();

	private CalendarSettings settings = new CalendarSettings();

	public GoogleCalendarPanel() {

		// style this element as absolute position
		DOM.setStyleAttribute(this.getElement(), "position", "absolute");
		DOM.setStyleAttribute(this.getElement(), "top", "20px");
		DOM.setStyleAttribute(this.getElement(), "left", "0px");

		// change hour offset to false to facilitate google style
		settings.setOffsetHourLabels(false);
		settings.setTimeBlockClickNumber(Click.Double);
		// create day view
		dayView = new Calendar();
		dayView.setView(0);
		// set style as google-cal
		dayView.setWidth("100%");
		// set today as default date
		datePicker.setValue(new Date());

		datePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				dayView.setDate(event.getValue());
			}
		});
		dayView.addDeleteHandler(new DeleteHandler<Appointment>() {

			@Override
			public void onDelete(DeleteEvent<Appointment> event) {
				boolean commit = Window
						.confirm("Are you sure you want to delete appointment \""
								+ event.getTarget().getTitle() + "\"");
				if (commit == false) {
					event.setCancelled(true);
					System.out.println("cancelled appointment deletion");
				}

			}

		});
		dayView.addOpenHandler(new OpenHandler<Appointment>() {

			@Override
			public void onOpen(OpenEvent<Appointment> event) {
				final DialogBox dialogBox = createCalendaryEventDialogBox(event);
				dialogBox.center();
				dialogBox.show();

				System.out.println("You double-clicked appointment \""
						+ event.getTarget().getTitle() + "\"");
			}

		});

//		dayView.addValueChangeHandler(new ValueChangeHandler<Appointment>() {
//
//			@Override
//			public void onValueChange(ValueChangeEvent<Appointment> event) {
//				// TODO Auto-generated method stub
//				System.out.println("selected " + event.getValue().getTitle());
//			}
//
//		});

//		dayView.addTimeBlockClickHandler(new TimeBlockClickHandler<Date>() {
//
//			@Override
//			public void onTimeBlockClick(TimeBlockClickEvent<Date> event) {
//				// add a panel that can change the values
//				final DialogBox dialogBox = createCalendaryEventDialogBox(event);
//				dialogBox.center();
//				dialogBox.show();
//				System.out.println("New time clicked " + event.getTarget());
//
//			}
//
//		});

		daysTabBar.addTab("1 Day");
		daysTabBar.addTab("3 Day");
		daysTabBar.addTab("Work Week");
		daysTabBar.addTab("Week");
		daysTabBar.addTab("Agenda");
		daysTabBar.addTab("Month");
		daysTabBar.selectTab(1);
		daysTabBar.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				int dayIndex = event.getSelectedItem();
				if (dayIndex == 0) {
					dayView.setView(0,1);
				} else if (dayIndex == 1) {
					dayView.setView(0,3);
				} else if (dayIndex == 2)
					dayView.setView(0,5);
				else if (dayIndex == 3)
					dayView.setView(0,7);
				else if(dayIndex==4)
					dayView.setView(1);
				else if(dayIndex==5)
					dayView.setView(2);
			}
		});
		final Button leftWeekButton = new Button("<b>&lt;</b>");
		final Button rightWeekButton = new Button("<b>&gt;</b>");
		final Button todayButton = new Button("<b>Today</b>");

		leftWeekButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				clickChangeDateButton(-7);
			}
		});
		rightWeekButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				clickChangeDateButton(7);
			}
		});
		todayButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				clickChangeDateButton(0);
			}

		});

		//topPanel.add(leftWeekButton);
		//topPanel.add(rightWeekButton);
		//topPanel.add(todayButton);
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
		layoutTable.getCellFormatter().setVerticalAlignment(1, 0,
				HasVerticalAlignment.ALIGN_TOP);
		layoutTable.getCellFormatter().setVerticalAlignment(1, 1,
				HasVerticalAlignment.ALIGN_TOP);
		layoutTable.getCellFormatter().setWidth(1, 0, "50px");
		add(layoutTable);

		// generate random appointments
        ArrayList<Appointment> appts = AppointmentBuilder.build();
        dayView.suspendLayout();
        dayView.addAppointments(appts);
        dayView.resumeLayout();

		// window events to handle resizing
		Window.enableScrolling(false);
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				int h = event.getHeight();
				dayView.setHeight(h - 85 + "px");

			}
		});
		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				dayView.setHeight(Window.getClientHeight() - 85 + "px");
				// dayView.scrollToHour(6);
			}
		});
		DOM.setStyleAttribute(getElement(), "padding", "10px");

	}

	private DialogBox createCalendaryEventDialogBox(Object event) {
		// Create a dialog box and set the caption text
		final DialogBox dialogBox = new DialogBox();
		dialogBox.ensureDebugId("cwDialogBox");
		dialogBox.setText("Calendar event");

		// Create a table to layout the content
		VerticalPanel dialogContents = new VerticalPanel();
		dialogContents.setSpacing(4);
		dialogBox.setWidget(dialogContents);

		// Add some text to the top of the dialog
		HTML eventName = new HTML("Event Name");
		dialogContents.add(eventName);
		final TextBox eventNameText = new TextBox();
		dialogContents.add(eventNameText);
		eventNameText.ensureDebugId("cwBasicText-textbox");
		eventNameText.setFocus(true);
		eventNameText.selectAll();
		HTML when = new HTML("When");
		dialogContents.add(when);
		final TextBox eventWhenText = new TextBox();
		dialogContents.add(eventWhenText);
		// Description
		HTML description = new HTML("Description");
		dialogContents.add(description);
		// Add a text area
		final TextArea descriptionText = new TextArea();
		descriptionText.ensureDebugId("cwBasicText-textarea");
		descriptionText.setVisibleLines(5);
	    
		dialogContents.add(descriptionText);

		OpenEvent<Appointment> targetAppointment = null;
		Appointment ap = null;
		if (event instanceof OpenEvent) {
			targetAppointment = (OpenEvent<Appointment>) event;
			Appointment appt = targetAppointment.getTarget();
			eventNameText.setText(appt.getTitle());
			eventWhenText
					.setText(appt.getStart().toString());
			descriptionText.setText(appt.getDescription());
			ap = appt;
		} else if (event instanceof TimeBlockClickEvent) {
			TimeBlockClickEvent<Date> clickedDate = (TimeBlockClickEvent<Date>) event;
			Date startDate = clickedDate.getTarget();
			eventWhenText.setText(startDate.toString());
			ap = new Appointment();
			ap.setTitle("(No title)");
			ap.setStart(startDate);
			Date endDate = (Date) startDate.clone();
			// default time is 1 hour
			endDate.setHours(startDate.getHours() + 1);
			ap.setEnd(endDate);
//			ap.addStyleName("gwt-appointment-blue");
			dayView.addAppointment(ap);
		}
		final Appointment appointment = ap;

		// Add a close button at the bottom of the dialog
		Button closeButton = new Button("Close", new ClickHandler() {
			public void onClick(ClickEvent event) {
				appointment.setTitle(eventNameText.getText());
//				appointment.setStart(
//						new Date(eventWhenText.getText()));
				appointment.setDescription(
						descriptionText.getText());
				dialogBox.hide();
			}
		});
		dialogContents.add(closeButton);
		// Add a close button at the bottom of the dialog
		Button deleteButton = new Button("Delete", new ClickHandler() {
			public void onClick(ClickEvent event) {
				dayView.removeAppointment(appointment);
				dialogBox.hide();
			}
		});
		dialogContents.add(deleteButton);

		return dialogBox;
	}

	private void clickChangeDateButton(int numOfDays) {
		if (numOfDays == 0) {
			dayView.setDate(new Date());
		} else {
			Date tempDate = dayView.getDate();
			// Calendar cal = Calendar.getInstance();
			// cal.setTime(dateBefore);
			// add one week (seven days)
			// cal.add(Calendar.DATE, numOfDays);
			// dayView.setDate(cal.getTime());
			tempDate.setDate(tempDate.getDate() + numOfDays);
			dayView.setDate(tempDate);
		}
	}

	/**
	 * Generate random appoinmentents.
	 */
	public void generateRandomAppointments() {

		Integer[] hours = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
				12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24 };
		Integer[] minutes = new Integer[] { 0, 15, 30, 45 };
		Integer[] durations = new Integer[] { 15, 30, 45, 60, 90, 120, 180, 240 };
		String[] styles = new String[] { "gwt-appointment-green",
				"gwt-appointment-blue", "gwt-appointment-lightgreen",
				"gwt-appointment-yellow" };

		Date tempDate = new Date();
		tempDate.setHours(0);
		tempDate.setMinutes(0);
		tempDate.setSeconds(0);
		tempDate.setDate(tempDate.getDate() - 5);

		dayView.suspendLayout();
		for (int day = 0; day < 14; day++) {

			for (int a = 0; a < 8; a++) {

				Date start = (Date) tempDate.clone();
				int hour = hours[Random.nextInt(23)];
				int min = minutes[Random.nextInt(3)];
				int dur = durations[Random.nextInt(7)];
				start.setHours(hour);
				start.setMinutes(min);

				Date end = (Date) start.clone();
				end.setMinutes(start.getMinutes() + dur);

				System.out.println("appt start: " + start + "  end: " + end);

				String style = styles[Random.nextInt(4)];
				Appointment appt = new Appointment();
				appt.setStart(start);
				appt.setEnd(end);
				appt.setTitle("day: " + day + " appt: " + a);
//				appt.addStyleName(style);
				dayView.addAppointment(appt);

			}

			// increment date by +1
			tempDate.setDate(tempDate.getDate() + 1);
		}

		// add my first multi-day appt
		Appointment appt1 = new Appointment();
		appt1.setStart(new Date());
		appt1.setEnd(new Date(new Date().getYear(), new Date().getMonth(),
				new Date().getDate() + 3));
		appt1.setTitle("all day 1");
//		appt1.addStyleName("gwt-appointment-green");
		appt1.setMultiDay(true);
		dayView.addAppointment(appt1);

		Appointment appt2 = new Appointment();
		appt2.setStart(new Date());
		appt2.setEnd(new Date(new Date().getYear(), new Date().getMonth(),
				new Date().getDate() + 3));
		appt2.setTitle("all day 2");
//		appt2.addStyleName("gwt-appointment-green");
		appt2.setMultiDay(true);
		dayView.addAppointment(appt2);

		Appointment appt3 = new Appointment();
		appt3.setStart(new Date());
		appt3.setEnd(new Date(new Date().getYear(), new Date().getMonth(),
				new Date().getDate() + 1));
		appt3.setTitle("all day 3");
//		appt3.addStyleName("gwt-appointment-green");
		appt3.setMultiDay(true);
		dayView.addAppointment(appt3);

		Appointment appt4 = new Appointment();
		appt4.setStart(new Date(new Date().getYear(), new Date().getMonth(),
				new Date().getDate() + 2));
		appt4.setEnd(new Date(new Date().getYear(), new Date().getMonth(),
				new Date().getDate() + 7));
		appt4.setTitle("all day 4 " + appt4.getStart() + " " + appt4.getEnd());
//		appt4.addStyleName("gwt-appointment-green");
		appt4.setMultiDay(true);
		dayView.addAppointment(appt4);

		Appointment appt5 = new Appointment();
		appt5.setStart(new Date(new Date().getYear(), 11, 23, 10, 0));
		appt5.setEnd(new Date(new Date().getYear(), 11, 24, 13, 30));
		appt5.setTitle("Multi Day event");
		appt5.setDescription("From Dec 23 10am to Dec 24 1:30pm");
//		appt5.addStyleName("gwt-appointment-green");
		appt5.setMultiDay(true);
		dayView.addAppointment(appt5);

		Appointment appt6 = new Appointment();
		appt6.setStart(new Date(new Date().getYear(), 11, 25, 0, 0));
		appt6.setEnd(new Date(new Date().getYear(), 11, 26, 0, 0));
		appt6.setTitle("All Day event");
		appt6.setDescription("From Dec 25 to Dec 26");
//		appt6.addStyleName("gwt-appointment-blue");
		appt6.setMultiDay(true);
		dayView.addAppointment(appt6);

		Appointment appt7 = new Appointment();
		appt7.setStart(new Date(new Date().getYear(), 11, 25, 0, 0));
		appt7.setEnd(new Date(new Date().getYear(), 11, 25, 0, 0));
		appt7.setTitle("All Day");
		appt7.setDescription("Dec 25 12am to Midnight (12am Dec 26)");
//		appt7.addStyleName("gwt-appointment-red");
		appt7.setMultiDay(true);
		dayView.addAppointment(appt7);

		dayView.resumeLayout();
	}

}
