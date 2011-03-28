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
package com.gwtcal.client;

import java.util.Collections;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
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

/**
 *
 * @author Brad Rydewski
 * @author Carlos Morales
 */
public class MonthCalendar<T> extends Composite implements HasData<T>, IsWidget {

    interface Template extends SafeHtmlTemplates {

        @Template("<div __index='{0}'>{1} -- {2}</div>")
        SafeHtml appointment(int id, String start, String title);
    }
    private static Template TEMPLATE =
            GWT.create(Template.class);

    private final Scheduler.ScheduledCommand redrawCommand =
            new Scheduler.ScheduledCommand() {

        @Override
        public void execute() {
            redraw();
        }
    };

    private static String NOT_SUPPORTED = "Not suppoted by gwt-cal";
    private final HTMLPanel root;
    private final AppointmentProvider<T> provider;
    private SelectionModel<? super T> selectionModel;
    private List<? extends T> values;

    public MonthCalendar(AppointmentProvider<T> provider) {
        this.provider = provider;
        this.root = new HTMLPanel("");
        initWidget(root);

        this.sinkEvents(Event.ONCLICK);// | Event.ONMOUSEDOWN | Event.ONMOUSEMOVE);
    }

    @Override
    public SelectionModel<? super T> getSelectionModel() {
        return selectionModel;
    }

    @Override
    public void setRowData(int start, List<? extends T> values) {
        this.values = values;
//        Collections.sort(values);
        redrawCommand.execute();
    }

    @Override
    public void setSelectionModel(SelectionModel<? super T> selectionModel) {
        this.selectionModel = selectionModel;
    }

    @Override
    public int getRowCount() {
        return (values == null) ? 0 : values.size();
    }

    @Override
    public void onLoad() {
        redrawCommand.execute();
        super.onLoad();
    }

    @Override
    public void onBrowserEvent(Event event) {
        switch(event.getTypeInt()) {
            case Event.ONMOUSEDOWN:break;
            case Event.ONMOUSEUP:break;
            case Event.ONMOUSEMOVE:break;
            case Event.ONMOUSEOUT:break;
            case Event.ONMOUSEOVER:break;
            case Event.ONCLICK:break;
        }


        EventTarget eventTarget = event.getEventTarget();
        if (!Element.is(eventTarget)) {
          return;
        }

        final Element target = event.getEventTarget().cast();
        

        
        //get index of appointment, and retrieve from list of values
        int index = target.getPropertyInt("__index");
        T value = values.get(index);

        //set item as selected
        if(selectionModel!=null)
            selectionModel.setSelected(value, true);

        //what about mouse over events?
        //what about mouse out events?


        super.onBrowserEvent(event);
    }



    private void redraw() {

        //provider should not be null
        assert(provider != null);

        //exit if list of values is null
        if(values==null) {
        	System.out.println("values are null");
            return;
        }

        System.out.println("drawing "+values.size());

        //string builder for html
        SafeHtmlBuilder sb = new SafeHtmlBuilder();

        //for each appointment, render
        for (int i=0;i<values.size();i++) {
            T value = values.get(i);
            sb.append(TEMPLATE.appointment(i,
                    provider.getStart(value).toString(),
                    provider.getTitle(value)));
        }

        //set inner html
        root.getElement().setInnerHTML(sb.toSafeHtml().asString());
    }





    //Unsupported method required by HasData interface. Not used by gwt-cal
    @Override
    public HandlerRegistration addCellPreviewHandler(CellPreviewEvent.Handler<T> handler) {
    	return super.addHandler(handler, CellPreviewEvent.getType());
    }

    @Override
    public void setVisibleRangeAndClearData(Range range, boolean forceRangeChangeEvent) {
//        throw new UnsupportedOperationException(NOT_SUPPORTED);
    	if(forceRangeChangeEvent)
    		redrawCommand.execute();
    }

    @Override
    public HandlerRegistration addRangeChangeHandler(Handler handler) {
    	return super.addHandler(handler, RangeChangeEvent.getType());
    }

    @Override
    public HandlerRegistration addRowCountChangeHandler(RowCountChangeEvent.Handler handler) {
        return super.addHandler(handler, RowCountChangeEvent.getType());
    }

    @Override
    public T getVisibleItem(int indexOnPage) {
        return values.get(indexOnPage);
    }

    @Override
    public int getVisibleItemCount() {
        return (values==null)?0:values.size();
    }

    @SuppressWarnings("unchecked")
	@Override
    public Iterable<T> getVisibleItems() {
        return (Iterable<T>)values.iterator();
    }

    @Override
    public Range getVisibleRange() {
    	return new Range(0, Integer.MAX_VALUE);
//        return new Range(0,(values==null)?0:values.size());
    }

    @Override
    public void setVisibleRange(int start, int length) {
//        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public void setVisibleRange(Range range) {
//        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public boolean isRowCountExact() {
        return true;
    }

    @Override
    public void setRowCount(int count) {
//        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }

    @Override
    public void setRowCount(int count, boolean isExact) {
//        throw new UnsupportedOperationException(NOT_SUPPORTED);
    }
}
