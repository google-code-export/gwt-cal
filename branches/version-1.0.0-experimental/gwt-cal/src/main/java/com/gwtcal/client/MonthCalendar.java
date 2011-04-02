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
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.RangeChangeEvent;
import com.google.gwt.view.client.RangeChangeEvent.Handler;
import com.google.gwt.view.client.RowCountChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.gwtcal.client.util.Assert;

/**
 * GWT-Cal Widget to display a list of appointments in a month.
 *
 * @author Brad Rydewski
 * @author Carlos Morales
 */
public class MonthCalendar<T> extends Composite implements HasData<T>, IsWidget {

    interface RenderAppointmentTemplate extends SafeHtmlTemplates {

        @Template("<div __index='{0}'>{1} -- {2}</div>")
        SafeHtml appointment(int id, String start, String title);
    }

    private static RenderAppointmentTemplate APPOINTMENT_TEMPLATE = GWT.create(RenderAppointmentTemplate.class);

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

    public MonthCalendar(AppointmentProvider<T> provider) {
        this.provider = provider;
        this.root = new HTMLPanel("");
        initWidget(root);

        this.sinkEvents(Event.ONCLICK);// | Event.ONMOUSEDOWN | Event.ONMOUSEMOVE);
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
        //TODO: This is doing nothing yet...
        switch (event.getTypeInt()) {
            case Event.ONMOUSEDOWN:
                break;
            case Event.ONMOUSEUP:
                break;
            case Event.ONMOUSEMOVE:
                break;
            case Event.ONMOUSEOUT:
                break;
            case Event.ONMOUSEOVER:
                break;
            case Event.ONCLICK:
                break;
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

        SafeHtmlBuilder widgetMarkup = new SafeHtmlBuilder();

        for (int i = 0; i < appointments.size(); i++) {
            T value = appointments.get(i);
            widgetMarkup.append(
                    APPOINTMENT_TEMPLATE.appointment(i, provider.getStart(value).toString(), provider.getTitle(value)));
        }

        root.getElement().setInnerHTML(widgetMarkup.toSafeHtml().asString());
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
