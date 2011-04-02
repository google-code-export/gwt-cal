package com.gwtcal.demo.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.gwtcal.client.AppointmentProvider;
import com.gwtcal.client.MonthCalendar;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gwt_cal_demo implements EntryPoint {

	MonthCalendar<Appointment> calendar;
	AppointmentProvider<Appointment> provider;
	ListDataProvider<Appointment> dataProvider;
	SingleSelectionModel<Appointment> selectionModel;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		this.dataProvider = new ListDataProvider<Appointment>();
		this.provider = new AppointmentProviderImpl();
		this.calendar = new MonthCalendar<Appointment>(provider);

		this.selectionModel = new SingleSelectionModel<Appointment>();
		this.selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Appointment selected = selectionModel.getSelectedObject();
				Window.alert(selected.getTitle());
			}
		});
		this.calendar.setSelectionModel(selectionModel);

		List<Appointment> apptList = new ArrayList<Appointment>();
		Appointment appt1 = new Appointment();
		appt1.setDescription("I love Pizza");
		appt1.setTitle("Planet Pizza!!!");
		appt1.setStart(new Date());
		appt1.setEnd(new Date());
		apptList.add(appt1);
		
		this.dataProvider.addDataDisplay(calendar);
		this.dataProvider.setList(apptList);
//		this.dataProvider.refresh();

	    // Create a CellList that uses the cell.
	    CellTable<Appointment> cellList = new CellTable<Appointment>();
	    cellList.addColumn(new TextColumn<Appointment>(){
			@Override
			public String getValue(Appointment object) {
				return object.getTitle();
			}
	    });
	    cellList.setSelectionModel(selectionModel);
	    // Connect the list to the data provider.
	    dataProvider.addDataDisplay(cellList);

	    
	    // Add it to the table and calendar to the Root panel.
		RootPanel.get().add(calendar);
	    RootPanel.get().add(cellList);
	}
}
