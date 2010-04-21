package com.bradrydzewski.gwt.calendar.demo.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class ICalCalendarPanel extends ResizeComposite {

	private static ICalCalendarPanelUiBinder uiBinder = GWT
			.create(ICalCalendarPanelUiBinder.class);

	interface ICalCalendarPanelUiBinder extends
			UiBinder<Widget, ICalCalendarPanel> {
	}

	interface ICalClientBundle extends ClientBundle {
		@Source("ICalCssResource.css")
		public ICalCssResource css();
	}
	
	interface ICalCssResource extends CssResource {
		String test();
		String test2();
	}

	@UiField Calendar calendar;
	@UiField DockLayoutPanel layoutPanel;
	@UiField Button todayButton;
	@UiField Button oneDayButton;
	@UiField Button threeDayButton;
	@UiField Button monthButton;
	@UiField DatePicker datePicker;

	@SuppressWarnings("unchecked")
	public ICalCalendarPanel() {
		
		ICalClientBundle css = GWT.create(ICalClientBundle.class);
		css.css().ensureInjected();
		
		initWidget(uiBinder.createAndBindUi(this));

		List<Appointment> appointments = AppointmentBuilder.build(AppointmentBuilder.ICAL_STYLES);
		calendar.suspendLayout();
		calendar.addAppointments((ArrayList)appointments);
		calendar.resumeLayout();

		monthButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				calendar.setView(CalendarViews.MONTH);
			}
		});
		oneDayButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				calendar.setView(CalendarViews.DAY,1);
			}
		});
		threeDayButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				calendar.setView(CalendarViews.DAY,3);
			}
		});
		todayButton.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				datePicker.setValue(new Date(),true);
			}
		});
		datePicker.addValueChangeHandler(new ValueChangeHandler<Date>(){
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				calendar.setDate(event.getValue());
			}
		});
	}


}
