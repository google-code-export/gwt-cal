package com.bradrydzewski.gwt.calendar.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main implements EntryPoint {
	
	
	public void onModuleLoad() {
		/* Un-comment for iCal version of demo */
//		RootPanel.get().add(new iCalCalendarPanel());
		
		/* Un-comment for Google version of demo */
		RootPanel.get().add(new GoogleCalendarPanel());

	}
}
