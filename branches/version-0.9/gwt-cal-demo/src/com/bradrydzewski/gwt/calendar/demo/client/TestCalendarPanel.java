package com.bradrydzewski.gwt.calendar.demo.client;

import java.util.ArrayList;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.CalendarWidget;
import com.bradrydzewski.gwt.calendar.client.listview.ListView;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;

public class TestCalendarPanel extends AbsolutePanel {

	CalendarWidget w = null;
	
	public TestCalendarPanel() {
		
		//DOM.setStyleAttribute(this.getElement(), "padding", "25px");
		DecoratorPanel dp = new DecoratorPanel();
		
		w = new ListView(){};
		
		w.setWidth("800px");
		w.setHeight("400px");
		dp.setWidget(w);
		add(dp);
		
		
		//w.doLayout();
		
        DeferredCommand.addCommand(new Command() {

            //@Override
            public void execute() {
                //if (GWT.isScript()) {
                   // w.doLayout();
                //}
            }
        });
        
        ArrayList<Appointment> appts = AppointmentBuilder.build();
        w.suspendLayout();
        w.addAppointments(appts);
        w.resumeLayout();
	}
}
