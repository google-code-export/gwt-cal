package com.bradrydzewski.gwt.calendar.client;

import com.bradrydzewski.gwt.calendar.client.util.AppointmentUtil;
import com.google.gwt.event.dom.client.KeyCodes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.impl.FocusImpl;

public class DayView extends CalendarView {

    private boolean lastWasKeyDown = false;    
    private Element focusable = null;
    
    // <editor-fold desc="Static Fields" defaultState="collapse">
    private static final String GWT_CALENDAR_STYLE = "gwt-cal";
    // </editor-fold>
    
    // <editor-fold desc="Private Fields" defaultState="collapse">
    private DayViewHeader dayViewHeader = null;
    private DayViewBody dayViewBody = null;
    private DayViewLayoutStrategy layoutStrategy = null;
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

        this.setStyleName(GWT_CALENDAR_STYLE);
        rootPanel.add(dayViewHeader);
        rootPanel.add(dayViewBody);


        // Create naturally-focusable element
        focusable = focusImpl.createFocusable();
        // Hide element and append it to root div
        DOM.setIntStyleAttribute(focusable, "zIndex", -1);
        DOM.appendChild(getElement(), focusable);

        doLayout();
    }

    static final FocusImpl focusImpl = FocusImpl.getFocusImplForPanel();


    // </editor-fold>
    // <editor-fold desc="Public Methods" defaultState="collapse">
    @Override
    public void doLayout() {

        if (layoutSuspended) {
            layoutPending = true;
            return;
        }

        dayViewHeader.setDays((Date) getDate().clone(), getDays());
        dayViewHeader.setYear((Date) getDate().clone());
        dayViewBody.setDays((Date) getDate().clone(), getDays());
        dayViewBody.getTimeline().prepare();

        // we don't want to re-sort a sorted array
        // so we only sort if some action has triggered a new sort
        if (sortPending) {
            Collections.sort(appointments);
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
    }

    public void scrollToHour(int hour) {
        dayViewBody.getScrollPanel().setScrollPosition(hour *
                getSettings().getIntervalsPerHour() * getSettings().getPixelsPerInterval());
    }

    @Override
    public void setHeight(String height) {

        super.setHeight(height);
        dayViewBody.setHeight(getOffsetHeight() - 2 - dayViewHeader.getOffsetHeight() + "px");
    }

    @Override
    public void setSize(String width, String height) {

        super.setSize(width, height);
        dayViewBody.setHeight(getOffsetHeight() - 2 - dayViewHeader.getOffsetHeight() + "px");
    }

    @Override
    @SuppressWarnings("fallthrough")
    public void onBrowserEvent(Event event) {
        int eventType = DOM.eventGetType(event);

        switch (eventType) {
            case Event.ONMOUSEDOWN: {
                if (DOM.eventGetCurrentTarget(event) == getElement()) {
                    Element elem = DOM.eventGetTarget(event);
                    Appointment appt =
                            AppointmentUtil.checkAppointmentElementClicked(elem, appointments);
                    if (appt != null) {
                        setValue(appt);
                    }
                }
                break;
            }
            case Event.ONKEYPRESS: {
                if (!lastWasKeyDown) {
                    keyboardNavigation(event);
                }
                lastWasKeyDown = false;
                break;
            }
            case Event.ONKEYDOWN: {
                keyboardNavigation(event);
                lastWasKeyDown = true;
                break;
            }
            case Event.ONKEYUP: {

                lastWasKeyDown = false;
                break;
            }
        }

        super.onBrowserEvent(event);
    }

    private void keyboardNavigation(Event event) {


        //only proceed if an appointment is selected
        if (getSelectedAppointment() == null) {
            return;
        }

        //get the key
        int key = DOM.eventGetKeyCode(event);
        //Window.alert("pressed: " + key);
        switch (key) {
            case KeyCodes.KEY_DELETE: {
                DOM.eventCancelBubble(event, true);
                DOM.eventPreventDefault(event);
                removeAppointment((Appointment) getSelectedAppointment());
                break;
            }
            case KeyCodes.KEY_LEFT:
            case KeyCodes.KEY_UP: {
                DOM.eventCancelBubble(event, true);
                DOM.eventPreventDefault(event);
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
