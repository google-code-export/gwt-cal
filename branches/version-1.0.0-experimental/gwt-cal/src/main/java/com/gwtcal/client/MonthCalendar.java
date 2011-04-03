/*
 * This file is part of gwt-cal
 * Copyright (C) 2009-2011  Scottsdale Software LLC
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
package com.gwtcal.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.RangeChangeEvent.Handler;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.gwtcal.client.layout.month.MonthDateUtils;
import com.gwtcal.client.layout.month.MonthLayoutDescription;
import com.gwtcal.client.util.Assert;
import com.gwtcal.client.util.DateUtils;

import static com.gwtcal.client.layout.month.MonthDateUtils.firstDateShownInAMonthView;
import static com.gwtcal.client.util.DateUtils.moveOneDayForward;

/**
 * GWT-Cal Widget to display a list of appointments in a month.
 *
 * @author Brad Rydewski
 * @author Carlos Morales
 */
public class MonthCalendar<T> extends Composite implements HasData<T>, IsWidget {

    private static final int DAYS_IN_A_WEEK = 7;

    interface MonthCalendarTemplate extends SafeHtmlTemplates {

        @Template("<table>{0}</table>")
        SafeHtml calendarGrid(SafeHtml rows);

        @Template("<div>{0}</div>")
        SafeHtml dayHeading(String dayName);


        @Template("<div __index='{0}'>{1} -- {2}</div>")
        SafeHtml appointment(int id, String start, String title);
    }

    private static MonthCalendarTemplate MONTH_TEMPLATES = GWT.create(MonthCalendarTemplate.class);

    /**
     * Manages event-queue-friendly requests to redraw the widget state.
     */
    private final Scheduler.ScheduledCommand redrawCommand = new Scheduler.ScheduledCommand() {
        @Override
        public void execute() {
            redraw();
        }
    };

    private final HTMLPanel root;

    private final AppointmentProvider<T> provider;

    private SelectionModel<? super T> selectionModel;

    private List<? extends T> appointments;

    private Date date;

    /**
     * The first date displayed on the MonthView (1st cell.) This date is not
     * necessarily the first date of the month as the month view will sometimes
     * display days from the adjacent months because of the number of days
     * fitting in the visible grid.
     */
    private Date firstDateDisplayed;

    /**
     * The number of rows required to display the entire month in grid format.
     * Although most months span a total of five weeks, there are some months
     * that span six weeks.
     */
    private int monthViewRequiredRows = 5;


    public MonthCalendar(AppointmentProvider<T> provider) {
        this.provider = provider;
        this.root = new HTMLPanel("");
        initWidget(root);

        this.sinkEvents(Event.ONCLICK);// | Event.ONMOUSEDOWN | Event.ONMOUSEMOVE);
    }


    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the selection model configured for this widget.
     *
     * @return A model for selection within a list.
     * @see com.google.gwt.view.client.HasData#getSelectionModel()
     */
    @Override
    public SelectionModel<? super T> getSelectionModel() {
        return selectionModel;
    }

    /**
     * Sets the selection model configured for this widget.
     *
     * @see com.google.gwt.view.client.HasData#setSelectionModel(com.google.gwt.view.client.SelectionModel)
     */
    @Override
    public void setSelectionModel(SelectionModel<? super T> selectionModel) {
        this.selectionModel = selectionModel;
    }

    /**
     * Sets the data this widget will render.
     *
     * @param start  the start index of the data
     * @param values the values within the range
     * @see com.google.gwt.view.client.HasData#setRowData(int, java.util.List)
     */
    @Override
    public void setRowData(int start, List<? extends T> values) {
        this.appointments = values;
        redrawCommand.execute();
    }

    /**
     * Get the total count of all rows set for this widget.
     *
     * @return the total row count
     * @see com.google.gwt.view.client.HasRows#getRowCount()
     */
    @Override
    public int getRowCount() {
        return (appointments == null) ? 0 : appointments.size();
    }

    /**
     * This method is called immediately after a widget becomes attached to the
     * browser's document.
     *
     * @see com.google.gwt.user.client.ui.Widget#onLoad()
     */
    @Override
    public void onLoad() {
        redrawCommand.execute();
        //TODO: Get rid of this line, Widget.onLoad does nothing...
        super.onLoad();
    }

    @Override
    public void onBrowserEvent(Event event) {
        switch (event.getTypeInt()) {
            case Event.ONMOUSEDOWN:
            case Event.ONMOUSEUP:
            case Event.ONMOUSEMOVE:
            case Event.ONMOUSEOUT:
            case Event.ONMOUSEOVER:
            case Event.ONCLICK:
                //TODO: This is doing nothing yet...
        }

        EventTarget eventTarget = event.getEventTarget();
        if (!Element.is(eventTarget)) {
            return;
        }

        final Element target = event.getEventTarget().cast();

        int index = target.getPropertyInt("__index");
        T value = appointments.get(index);

        if (selectionModel != null) {
            selectionModel.setSelected(value, true);
        }

        //TODO: what about mouse over/mouse out events?

        super.onBrowserEvent(event);
    }


    private void redraw() {

        if (appointments == null) {
            return;
        }

        Assert.notNull(provider, "You must define a non-null AppointmentProvider<T> when using this widget.");

//        MonthLayoutDescription monthLayoutDescription = new
//                MonthLayoutDescription(
//                firstDateDisplayed, monthViewRequiredRows,
//                calendarWidget.getAppointments(),
//                calculatedCellAppointments - 1);

        SafeHtmlBuilder widgetMarkup = new SafeHtmlBuilder();
        widgetMarkup.append(buildCalendarGrid());
        for (int i = 0; i < appointments.size(); i++) {
            T value = appointments.get(i);
            widgetMarkup.append(
                    MONTH_TEMPLATES.appointment(i, provider.getStart(value).toString(), provider.getTitle(value)));
        }

        root.getElement().setInnerHTML(widgetMarkup.toSafeHtml().asString());
    }



    private String[] dayNames={"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    /**
     * Builds and formats the Calendar Grid. No appointments are included when
     * building the grid.
     */
    @SuppressWarnings("deprecation")
    private SafeHtml buildCalendarGrid() {
        //TODO: USE OUR CONFIGURATION
        int firstDayOfWeek = 0;
        int month = date.getMonth();
        firstDateDisplayed = firstDateShownInAMonthView(date, firstDayOfWeek);

        Date today = new Date();
        DateUtils.resetTime(today);
        SafeHtmlBuilder calendarGrid = new SafeHtmlBuilder();
        for (int i = 0; i < DAYS_IN_A_WEEK; i++) {
            calendarGrid.append(MONTH_TEMPLATES.dayHeading(dayNames[(i + firstDayOfWeek) % 7]));
//            monthCalendarGrid.setText(0, i, CalendarFormat.INSTANCE.getDayOfWeekAbbreviatedNames()[(i + firstDayOfWeek) % 7]);
//            monthCalendarGrid.getCellFormatter().setVerticalAlignment(0, i, HasVerticalAlignment.ALIGN_TOP);
//            monthCalendarGrid.getCellFormatter().setStyleName(0, i, WEEKDAY_LABEL_STYLE);
        }
//        Date date = (Date) firstDateDisplayed.clone();
//        monthViewRequiredRows = MonthDateUtils.monthViewRequiredRows(date, firstDayOfWeek);
//        for (int monthGridRowIndex = 1; monthGridRowIndex <= monthViewRequiredRows; monthGridRowIndex++) {
//            for (int dayOfWeekIndex = 0; dayOfWeekIndex < DAYS_IN_A_WEEK; dayOfWeekIndex++) {
//
//                if (monthGridRowIndex != 1 || dayOfWeekIndex != 0) {
//                    moveOneDayForward(date);
//                }
//
                //TODO: Configure days in grid...
//                configureDayInGrid(monthGridRowIndex, dayOfWeekIndex, String
//                        .valueOf(date.getDate()), date.equals(today), date
//                        .getMonth() != month);
//            }
//        }
        return calendarGrid.toSafeHtml();
    }

    /**
     * @see com.google.gwt.view.client.HasCellPreviewHandlers#addCellPreviewHandler(com.google.gwt.view.client.CellPreviewEvent.Handler)
     * @see com.google.gwt.user.client.ui.Widget#addHandler(com.google.gwt.event.shared.EventHandler, com.google.gwt.event.shared.GwtEvent.Type)
     */
    @Override
    public HandlerRegistration addCellPreviewHandler(CellPreviewEvent.Handler<T> handler) {
        return super.addHandler(handler, CellPreviewEvent.getType());
    }

    /**
     * Set the visible range and clear the current visible data.
     *
     * @see com.google.gwt.view.client.HasData#setVisibleRangeAndClearData(com.google.gwt.view.client.Range, boolean)
     */
    @Override
    public void setVisibleRangeAndClearData(Range range, boolean forceRangeChangeEvent) {
        if (forceRangeChangeEvent) {
            redrawCommand.execute();
        }
    }

    /**
     * @see com.google.gwt.view.client.HasData#addRangeChangeHandler(com.google.gwt.view.client.RangeChangeEvent.Handler)
     * @see com.google.gwt.user.client.ui.Widget#addHandler(com.google.gwt.event.shared.EventHandler, com.google.gwt.event.shared.GwtEvent.Type)
     */
    @Override
    public HandlerRegistration addRangeChangeHandler(Handler handler) {
        return super.addHandler(handler, RangeChangeEvent.getType());
    }

    /**
     * @see com.google.gwt.view.client.HasData#addRowCountChangeHandler(com.google.gwt.view.client.RowCountChangeEvent.Handler)
     * @see com.google.gwt.user.client.ui.Widget#addHandler(com.google.gwt.event.shared.EventHandler, com.google.gwt.event.shared.GwtEvent.Type)
     */
    @Override
    public HandlerRegistration addRowCountChangeHandler(RowCountChangeEvent.Handler handler) {
        return super.addHandler(handler, RowCountChangeEvent.getType());
    }

    /**
     * @see com.google.gwt.view.client.HasData#getVisibleItem(int)
     */
    @Override
    public T getVisibleItem(int indexOnPage) {
        return appointments.get(indexOnPage);
    }

    /**
     * @see com.google.gwt.view.client.HasData#getVisibleItemCount()
     */
    @Override
    public int getVisibleItemCount() {
        return (appointments == null) ? 0 : appointments.size();
    }

    /**
     * @see com.google.gwt.view.client.HasData#getVisibleItems()
     */
    @SuppressWarnings("unchecked")
    @Override
    public Iterable<T> getVisibleItems() {
        return (Iterable<T>) appointments.iterator();
    }

    /**
     * @see com.google.gwt.view.client.HasData#getVisibleRange()
     */
    @Override
    public Range getVisibleRange() {
        return new Range(0, Integer.MAX_VALUE);
    }

    /**
     * @see com.google.gwt.view.client.HasData#setVisibleRange(int, int)
     */
    @Override
    public void setVisibleRange(int start, int length) {
    }

    /**
     * @see com.google.gwt.view.client.HasData#setVisibleRange(int, int)
     */
    @Override
    public void setVisibleRange(Range range) {
    }

    /**
     * @see com.google.gwt.view.client.HasData#isRowCountExact()
     */
    @Override
    public boolean isRowCountExact() {
        return true;
    }

    /**
     * @see com.google.gwt.view.client.HasData#setRowCount(int)
     */
    @Override
    public void setRowCount(int count) {
    }

    /**
     * @see com.google.gwt.view.client.HasData#setRowCount(int, boolean)
     */
    @Override
    public void setRowCount(int count, boolean isExact) {
    }
}
