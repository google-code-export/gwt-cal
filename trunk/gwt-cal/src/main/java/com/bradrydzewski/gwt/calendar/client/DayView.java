package com.bradrydzewski.gwt.calendar.client;

import com.bradrydzewski.gwt.calendar.client.CalendarSettings.Click;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickEvent;
import com.bradrydzewski.gwt.calendar.client.util.AppointmentUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.user.client.Command;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class DayView extends CalendarView {

    // <editor-fold desc="Static Fields" defaultState="collapse">
    private static final String GWT_CALENDAR_STYLE = "gwt-cal";
    // </editor-fold>
    // <editor-fold desc="Private Fields" defaultState="collapse">
    private DayViewHeader dayViewHeader = null;
    private DayViewBody dayViewBody = null;
    private DayViewMultiDayBody multiViewBody = null;
    private DayViewLayoutStrategy layoutStrategy = null;
    private boolean lastWasKeyDown = false;
    private FocusPanel focusPanel = new FocusPanel();
    // </editor-fold>

    // <editor-fold desc="Constructors" defaultState="collapse">
    public DayView() {
        this(CalendarSettings.DEFAULT_SETTINGS);
    }

    public DayView(CalendarSettings settings) {

        super(settings);

        this.dayViewBody = new DayViewBody(this);
        this.dayViewHeader = new DayViewHeader(this);
        this.layoutStrategy = new DayViewLayoutStrategy(this);
        this.multiViewBody = new DayViewMultiDayBody(this);

        this.setStyleName(GWT_CALENDAR_STYLE);

        //focusPanel.setVisible(false);
        rootPanel.add(focusPanel);

        rootPanel.add(dayViewHeader);
        rootPanel.add(multiViewBody);
        rootPanel.add(dayViewBody);



        //Can probably move this to base CalendarView class, since this functionality
        // will be shared among all views.. right?
        DOM.setStyleAttribute(focusPanel.getElement(), "position", "absolute");
        DOM.setStyleAttribute(focusPanel.getElement(), "top", "-10");
        DOM.setStyleAttribute(focusPanel.getElement(), "left", "-10");
        DOM.setStyleAttribute(focusPanel.getElement(), "height", "0px");
        DOM.setStyleAttribute(focusPanel.getElement(), "width", "0px");
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



        DeferredCommand.addCommand(new Command() {

            @Override
            public void execute() {
                if (GWT.isScript()) {
                    doComponentLayout();
                }
            }
        });
    }
    

    public void doComponentLayout() {

        //System.out.println(getOffsetHeight() + " - 2 - " + dayViewHeader.getOffsetHeight() + " - " +  multiViewBody.getOffsetHeight() + "px");
        if (getOffsetHeight() > 0) {
            dayViewBody.setHeight(getOffsetHeight() - 2 - dayViewHeader.getOffsetHeight() - multiViewBody.getOffsetHeight() + "px");
        //System.out.println("doComponentLayout called");
        }
    }

    public void onLoad() {
        doComponentLayout();
    }
    
    // </editor-fold>
    // <editor-fold desc="Public Methods" defaultState="collapse">
    @Override
    public void doLayout() {

        if (layoutSuspended) {
            layoutPending = true;
            return;
        }

        multiViewBody.setDays((Date) getDate().clone(), getDays());
        dayViewHeader.setDays((Date) getDate().clone(), getDays());
        dayViewHeader.setYear((Date) getDate().clone());
        dayViewBody.setDays((Date) getDate().clone(), getDays());
        dayViewBody.getTimeline().prepare();

        // we don't want to re-sort a sorted array
        // so we only sort if some action has triggered a new sort
        if (sortPending) {
            Collections.sort(appointments);
            Collections.sort(multiDayAppointments);
            sortPending = false;
        }

        // remove all appointments from their parent
        for (AppointmentInterface appt : appointments) {
            //((Widget) appt).removeFromParent();
        }

        Date tmpDate = (Date) getDate().clone();

        for (int i = 0; i < getDays(); i++) {

            ArrayList<AppointmentInterface> filteredList =
                    AppointmentUtil.filterListByDate(appointments, tmpDate);

            // perform layout
            ArrayList<AppointmentAdapter> appointmentAdapters =
                    layoutStrategy.doLayout(filteredList, i, getDays());

            // add all appointments back to the grid
            //CHANGE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            for (AppointmentAdapter appt : appointmentAdapters) {
                this.dayViewBody.getGrid().grid.add((Widget) appt.getAppointment());

            }

            tmpDate.setDate(tmpDate.getDate() + 1);
        }
        
        ArrayList<AppointmentInterface> filteredList =
            AppointmentUtil.filterListByDateRange(appointments, getDate(), getDays());
        int desiredHeight = layoutStrategy.doMultiDayLayout(filteredList, getDate(), getDays());
        
        for(AppointmentInterface appt : filteredList) {
            this.multiViewBody.grid.add((Appointment)appt);
        }
        multiViewBody.grid.setHeight(desiredHeight + "px");
        //as part of layout set height
        doComponentLayout();
    }

    public void scrollToHour(int hour) {
        dayViewBody.getScrollPanel().setScrollPosition(hour *
                getSettings().getIntervalsPerHour() * getSettings().getPixelsPerInterval());
    }

    @Override
    public void setHeight(String height) {

        super.setHeight(height);
        doComponentLayout();
    //System.out.println(getOffsetHeight() + " - 2 - " + dayViewHeader.getOffsetHeight() + " - " +  multiViewBody.getOffsetHeight() + "px");
    //dayViewBody.setHeight(getOffsetHeight() - 2 - dayViewHeader.getOffsetHeight() - multiViewBody.getOffsetHeight() + "px");
    }

    @Override
    public void setSize(String width, String height) {

        super.setSize(width, height);
        doComponentLayout();
    }

    @Override
    @SuppressWarnings("fallthrough")
    public void onBrowserEvent(Event event) {
        int eventType = DOM.eventGetType(event);
        Element elem = DOM.eventGetTarget(event);

        switch (eventType) {
            case Event.ONDBLCLICK: {
                if (getSelectedAppointment() != null) {
                    /* get appointment element ... need to cast, unfortunately */
                    Element apptElement =
                            ((Appointment) getSelectedAppointment()).getElement();
                    /* if the selected appointment (or one of its child elements
                     * was clicked fire the double click event */
                    if (DOM.isOrHasChild(apptElement, elem)) {
                        OpenEvent.fire(this, getSelectedAppointment());
                    }

                    /* give focus so we can use up/down arrows */
                    focusPanel.setFocus(true);
                }

                if (getSettings().getTimeBlockClickNumber() == Click.Double &&
                        elem == dayViewBody.getGrid().gridOverlay.getElement()) {
                    int x = DOM.eventGetClientX(event);
                    int y = DOM.eventGetClientY(event);
                    timeBlockClick(x, y);
                }

                break;
            }
            case Event.ONMOUSEDOWN: {
                if (DOM.eventGetCurrentTarget(event) == getElement()) {

                    Appointment appt =
                            AppointmentUtil.checkAppointmentElementClicked(elem, appointments);
                    if (appt != null) {
                        setValue(appt);
                    } else {
                        //handle time block click
                        if (getSettings().getTimeBlockClickNumber() == Click.Single &&
                                elem == dayViewBody.getGrid().gridOverlay.getElement()) {
                            int x = DOM.eventGetClientX(event);
                            int y = DOM.eventGetClientY(event);
                            timeBlockClick(x, y);
                        }
                    }

                    /* give focus so we can use up/down arrows */
                    focusPanel.setFocus(true);

                    /* cancel events so Firefox / Chrome don't give
                     * the scroll panel focus. */
                    DOM.eventCancelBubble(event, true);
                    DOM.eventPreventDefault(event);
                    return;
                }//end if
            } //end case
        } //end switch

        super.onBrowserEvent(event);
    }

    void timeBlockClick(int x, int y) {
        int left = dayViewBody.getGrid().gridOverlay.getAbsoluteLeft();
        int top = dayViewBody.getScrollPanel().getAbsoluteTop();
        int width = dayViewBody.getGrid().gridOverlay.getOffsetWidth();
        int scrollOffset = dayViewBody.getScrollPanel().getScrollPosition();

        //x & y are based on screen position,need to get x/y relative to component
        int relativeY = y - top + scrollOffset;
        int relativeX = x - left;

        //find the interval clicked and day clicked
        double interval = Math.floor(relativeY / (double) getSettings().getPixelsPerInterval());
        double day = Math.floor((double) relativeX / ((double) width / (double) getDays()));

        //create new appointment date based on click
        Date newStartDate = getDate();
        newStartDate.setHours(0);
        newStartDate.setMinutes(0);
        newStartDate.setSeconds(0);
        newStartDate.setMinutes((int) interval * (60 / getSettings().getIntervalsPerHour()));
        newStartDate.setDate(newStartDate.getDate() + (int) day);

        TimeBlockClickEvent.fire(this, newStartDate);
    }

    void keyboardNavigation(int key) {
        switch (key) {
            case KeyCodes.KEY_DELETE: {
                removeAppointment((Appointment) getSelectedAppointment(), true);
                break;
            }
            case KeyCodes.KEY_LEFT:
            case KeyCodes.KEY_UP: {
                selectPreviousAppointment();
                dayViewBody.getScrollPanel().ensureVisible((UIObject) getSelectedAppointment());
                break;
            }
            case KeyCodes.KEY_RIGHT:
            case KeyCodes.KEY_DOWN: {
                selectNextAppointment();
                dayViewBody.getScrollPanel().ensureVisible((UIObject) getSelectedAppointment());
                break;
            }
        }
    }
    // </editor-fold>
}
