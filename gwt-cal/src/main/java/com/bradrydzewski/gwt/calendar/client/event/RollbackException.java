package com.bradrydzewski.gwt.calendar.client.event;

/**
 * <code>RollbackException</code> can be thrown to rollback or cancel
 * any changes made and not yet committed at the time of an Event.
 * <p>
 * An example is when an Appointment is deleted by the end-user.
 * A DeleteEvent is raised and the change can be reversed by throwing
 * the RollbackException.
 * 
 * @author Brad Rydzewski
 */
public class RollbackException extends Exception {
  /**
   * Default constructor.
   */
  public RollbackException() {
  }
}
