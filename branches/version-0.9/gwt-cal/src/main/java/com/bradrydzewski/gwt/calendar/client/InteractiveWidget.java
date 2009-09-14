/*
 * This file is part of gwt-cal
 * Copyright (C) 2009  Scottsdale Software LLC
 * 
 * gwt-cal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/
 */

package com.bradrydzewski.gwt.calendar.client;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This is a base UIObject that handles user interaction,
 * Focus, mouse clicks and keyboard commands (arrow, delete, etc).
 * @author Brad Rydzewski
 */
public abstract class InteractiveWidget extends Composite {

	/**
	 * Focus widget used to add keyboard and mouse focus to a calendar.
	 */
	private FocusPanel focusPanel = new FocusPanel();

	/**
	 * Main panel to which all other components are added.
	 */
	protected AbsolutePanel rootPanel = new AbsolutePanel();

	/**
	 * Used by focus widget to make sure a key stroke is only
	 * handled once by the calendar.
	 */
	private boolean lastWasKeyDown = false;

	public InteractiveWidget() {
		
		initWidget(rootPanel);
		
		//Sink events, mouse and keyboard for now
		sinkEvents(Event.ONMOUSEDOWN | Event.ONDBLCLICK | Event.KEYEVENTS);

		//Ensure the focus panel is invisible
		RootPanel.get().add(focusPanel);
		DOM.setStyleAttribute(focusPanel.getElement(), "position", "absolute");
        DOM.setStyleAttribute(focusPanel.getElement(), "top", "-10");
        DOM.setStyleAttribute(focusPanel.getElement(), "left", "-10");
        DOM.setStyleAttribute(focusPanel.getElement(), "height", "0px");
        DOM.setStyleAttribute(focusPanel.getElement(), "width", "0px");
        //Add key handler events to the focus panel
        focusPanel.addKeyPressHandler(new KeyPressHandler() {

            public void onKeyPress(KeyPressEvent event) {

                if (!lastWasKeyDown) {
                    keyboardNavigation(event.getNativeEvent().getKeyCode());
                }
                lastWasKeyDown = false;
            }
        });
        focusPanel.addKeyUpHandler(new KeyUpHandler() {

            public void onKeyUp(KeyUpEvent event) {

                lastWasKeyDown = false;
            }
        });
        focusPanel.addKeyDownHandler(new KeyDownHandler() {

            public void onKeyDown(KeyDownEvent event) {
                keyboardNavigation(event.getNativeEvent().getKeyCode());
                lastWasKeyDown = true;
            }
        });
	}

	/**
	 * Sets whether this object has focus.
	 * @param focused <code>true</code> to give this object focus,
	 * 			<code>false</code> to take it away.
	 */
	public void setFocus(boolean focused) {
		focusPanel.setFocus(focused);
	}

	public ComplexPanel getRootPanel() {
		return rootPanel;
	}
	
	public abstract void onDoubleClick(Element element);

	public abstract void onMouseDown(Element element);

	public abstract void onDeleteKeyPressed();

	public abstract void onUpArrowKeyPressed();

	public abstract void onDownArrowKeyPressed();

	public abstract void onLeftArrowKeyPressed();
	
	public abstract void onRightArrowKeyPressed();

	@Override
	public void onBrowserEvent(Event event) {
		int eventType = DOM.eventGetType(event);
		Element element = DOM.eventGetTarget(event);

		switch (eventType) {
			case Event.ONDBLCLICK: {
				onDoubleClick(element);
				focusPanel.setFocus(true);
				break;
			}
			case Event.ONMOUSEDOWN: {
				if (DOM.eventGetCurrentTarget(event)
						== getElement()) {
	
					onMouseDown(element);
					focusPanel.setFocus(true);
					//Cancel events so Firefox / Chrome don't
					//give child widgets with scrollbars focus.
					//TODO: Should not cancel onMouseDown events in the event an appointment would have a child widget with a scrollbar (if this would ever even happen).
					DOM.eventCancelBubble(event, true);
					DOM.eventPreventDefault(event);
					return;
				}
			}
		}

		super.onBrowserEvent(event);
	}

	
    protected void keyboardNavigation(int key) {
        switch (key) {
            case KeyCodes.KEY_DELETE: {
            	onDeleteKeyPressed();
                //removeAppointment((Appointment) getSelectedAppointment(), true);
                break;
            }
            case KeyCodes.KEY_LEFT: {
            	onLeftArrowKeyPressed();
            	break;
            }
            case KeyCodes.KEY_UP: {
            	onUpArrowKeyPressed();
                //selectPreviousAppointment();
                //dayViewBody.getScrollPanel().ensureVisible((UIObject) getSelectedAppointment());
                break;
            }
            case KeyCodes.KEY_RIGHT: {
            	onRightArrowKeyPressed();
            	break;
            }
            case KeyCodes.KEY_DOWN: {

            	onDownArrowKeyPressed();
                //selectNextAppointment();
                //dayViewBody.getScrollPanel().ensureVisible((UIObject) getSelectedAppointment());
                break;
            }
        }
    }

}
