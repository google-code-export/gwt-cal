package com.bradrydzewski.gwt.calendar.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;

/**
 *
 * @author Brad Rydzewski
 */
public class DeleteEvent<T> extends GwtEvent<DeleteHandler<T>> {

  /**
   * Handler type.
   */
  private static Type<DeleteHandler<?>> TYPE;
  
  private boolean cancelled = false;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
  
  

  /**
   * Fires a open event on all registered handlers in the handler manager.If no
   * such handlers exist, this method will do nothing.
   * 
   * @param <T> the target type
   * @param source the source of the handlers
   * @param target the target
   */
  public static <T> boolean fire(HasDeleteHandlers<T> source, T target) {
    if (TYPE != null) {
      DeleteEvent<T> event = new DeleteEvent<T>(target);
      source.fireEvent(event);
      return !event.isCancelled();
    }
    return true;
  }

  /**
   * Gets the type associated with this event.
   * 
   * @return returns the handler type
   */
  public static Type<DeleteHandler<?>> getType() {
    if (TYPE == null) {
      TYPE = new Type<DeleteHandler<?>>();
    }
    return TYPE;
  }

  private final T target;

  /**
   * Creates a new delete event.
   * 
   * @param target the ui object being opened
   */
  protected DeleteEvent(T target) {
    this.target = target;
  }

  @SuppressWarnings("unchecked")
  @Override
  public final Type<DeleteHandler<T>> getAssociatedType() {
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
  protected void dispatch(DeleteHandler<T> handler) {
        handler.onDelete(this);
  }
}
