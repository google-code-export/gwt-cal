package com.bradrydzewski.gwt.calendar.client.event;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

/**
 * A widget that implements this interface is a public source of
 * {@link UpdateEvent} events.
 * 
 * @param <T> the type being opened
 */
public interface HasUpdateHandlers<T> extends HasHandlers {
  /**
   * Adds a {@link UpdateEvent} handler.
   * 
   * @param handler the handler
   * @return the registration for the event
   */
  HandlerRegistration addUpdateHandler(UpdateHandler<T> handler);
}
