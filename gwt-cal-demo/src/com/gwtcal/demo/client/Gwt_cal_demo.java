package com.gwtcal.demo.client;

import java.util.Arrays;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.gwtcal.client.MonthCalendar;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gwt_cal_demo implements EntryPoint {

	private ListDataProvider<Appointment> dataProvider;
	
	private MonthCalendar<Appointment> calendar;

	public void onModuleLoad() {
		calendar = new MonthCalendar<Appointment>(new AppointmentProviderImpl());
		calendar.setDate(new java.util.Date());

		final SingleSelectionModel<Appointment> selectionModel = new SingleSelectionModel<Appointment>();
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Appointment selected = selectionModel.getSelectedObject();
				Window.alert(selected.getTitle());
			}
		});
		calendar.setSelectionModel(selectionModel);

		Appointment appointment = new Appointment();
		appointment.setDescription("I love Pizza");
		appointment.setTitle("Planet Pizza!!!");
		appointment.setStart(new Date());
		appointment.setEnd(new Date());
		
	    CellTable<Appointment> cellTable = new CellTable<Appointment>();
	    cellTable.addColumn(new TextColumn<Appointment>(){
			@Override
			public String getValue(Appointment object) {
				return object.getTitle();
			}
	    });
	    cellTable.setSelectionModel(selectionModel);

		dataProvider = new ListDataProvider<Appointment>();
	    dataProvider.setList(Arrays.asList(appointment));
	    dataProvider.addDataDisplay(calendar);
	    dataProvider.addDataDisplay(cellTable);
	    
		RootPanel.get().add(calendar);
	    RootPanel.get().add(cellTable);
	}
}
