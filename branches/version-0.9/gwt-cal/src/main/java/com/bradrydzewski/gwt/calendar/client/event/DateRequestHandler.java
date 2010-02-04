package com.bradrydzewski.gwt.calendar.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface DateRequestHandler<T> extends EventHandler {

	  /**
	   * Called when {@link DeleteEvent} is fired.
	   * 
	   * @param event the {@link DeleteEvent} that was fired
	   */
	  void onDateRequested(DateRequestEvent<T> event);
}