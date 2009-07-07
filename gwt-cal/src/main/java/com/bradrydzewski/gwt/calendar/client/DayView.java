package com.bradrydzewski.gwt.calendar.client;

import com.bradrydzewski.gwt.calendar.client.util.AppointmentUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Widget;

public class DayView extends CalendarView {

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

        doLayout();
    }

    // </editor-fold>
    
    // <editor-fold desc="Public Methods" defaultState="collapse">
    
    public void testAddToGrid(Appointment appt) {
        this.dayViewBody.getGrid().grid.add(appt);
    }
    public void testAdd(Appointment appt) {
        rootPanel.add(appt);
    }
    public void testAddToScrollBody(Appointment appt) {
        dayViewBody.add(appt);
    }
    
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
        dayViewBody.getScrollPanel().setScrollPosition(hour * getSettings().getIntervalsPerHour() * getSettings().getPixelsPerInterval() * hour);
    }

    @Override
    public void setHeight(String height) {

        super.setHeight(height);
        this.dayViewBody.setHeight(this.getOffsetHeight() - 2 - this.dayViewHeader.getOffsetHeight() + "px");
    }

    @Override
    public void setSize(String width, String height) {

        super.setSize(width, height);
        this.dayViewBody.setHeight(this.getOffsetHeight() - 2 - this.dayViewHeader.getOffsetHeight() + "px");
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
        }
        super.onBrowserEvent(event);
    }
    // </editor-fold>
}
