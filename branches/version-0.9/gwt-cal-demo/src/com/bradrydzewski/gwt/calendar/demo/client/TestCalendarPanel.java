package com.bradrydzewski.gwt.calendar.demo.client;

import java.util.ArrayList;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.Calendar;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.DecoratorPanel;

public class TestCalendarPanel extends AbsolutePanel {

	Calendar w = null;
	
	public TestCalendarPanel() {
		
		//DOM.setStyleAttribute(this.getElement(), "padding", "25px");
		DecoratorPanel dp = new DecoratorPanel();
		
		w = new Calendar();
		
		w.setWidth("800px");
		w.setHeight("400px");
		dp.setWidget(w);
		add(dp);
		
		
		//w.doLayout();
		
        DeferredCommand.addCommand(new Command() {

            //@Override
            public void execute() {
                //if (GWT.isScript()) {
                    //w.doLayout();
                //}
            	//w.setView(0);
            }
        });
        w.setView(0);
        ArrayList<Appointment> appts = AppointmentBuilder.build();
        w.suspendLayout();
        w.addAppointments(appts);
        w.resumeLayout();
       
	}
}
