package com.bradrydzewski.gwt.calendar.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 *
 * @author Brad
 */
public class TimeBlockClickEvent<T> extends GwtEvent<TimeBlockClickHandler<T>> {

  /**
   * Handler type.
   */
  private static Type<TimeBlockClickHandler<?>> TYPE;
  
  
  

  /**
   * Fires a open event on all registered handlers in the handler manager.If no
   * such handlers exist, this method will do nothing.
   * 
   * @param <T> the target type
   * @param source the source of the handlers
   * @param target the target
   */
  public static <T> void fire(HasTimeBlockClickHandlers<T> source, T target) {
    if (TYPE != null) {
      TimeBlockClickEvent<T> event = new TimeBlockClickEvent<T>(target);
      source.fireEvent(event);
    }
  }

  /**
   * Gets the type associated with this event.
   * 
   * @return returns the handler type
   */
  public static Type<TimeBlockClickHandler<?>> getType() {
    if (TYPE == null) {
      TYPE = new Type<TimeBlockClickHandler<?>>();
    }
    return TYPE;
  }

  private final T target;

  /**
   * Creates a new delete event.
   * 
   * @param target the ui object being opened
   */
  protected TimeBlockClickEvent(T target) {
    this.target = target;
  }

  @SuppressWarnings("unchecked")
  @Override
  public final Type<TimeBlockClickHandler<T>> getAssociatedType() {
    return (Type) TYPE;
  }

  /**
   * Gets the target.
   * 
   * @return the target
   */
  public T getTarget() {
    return target;
  }

  // Because of type erasure, our static type is
  // wild carded, yet the "real" type should use our I param.

  @Override
  protected void dispatch(TimeBlockClickHandler<T> handler) {
        handler.onTimeBlockClick(this);
  }

}
