package com.bradrydzewski.gwt.calendar.client.event;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * A widget that implements this interface is a public source of
 * {@link DeleteEvent} events.
 * 
 * @param <T> the type being opened
 */
public interface HasTimeBlockClickHandlers<T> extends HasHandlers {
  /**
   * Adds a {@link DeleteEvent} handler.
   * 
   * @param handler the handler
   * @return the registration for the event
   */
  HandlerRegistration addTimeBlockClickHandler(TimeBlockClickHandler<T> handler);
}
