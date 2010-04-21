package com.bradrydzewski.gwt.calendar.client;

import com.bradrydzewski.gwt.calendar.client.util.WindowUtils;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import java.util.Date;

/**
 *
 * @author Brad Rydzewski
 */
public class DayViewMultiDayBody extends Composite {

    private FlexTable header = new FlexTable();
    protected AbsolutePanel grid = new AbsolutePanel();
    private AbsolutePanel splitter = new AbsolutePanel();
    private HasSettings settings = null;
    private static final String TIMELINE_EMPTY_CELL_STYLE = "leftEmptyCell";
    private static final String SCROLLBAR_EMPTY_CELL_STYLE = "rightEmptyCell";
    private static final String DAY_CONTAINER_CELL_STYLE = "centerDayContainerCell";
    private static final String SPLITTER_STYLE = "splitter";
    protected SimplePanel gridOverlay = new SimplePanel();

    public DayViewMultiDayBody(HasSettings settings) {
        this.settings = settings;
        
        
        initWidget(header);

        this.header.setStyleName("multiDayBody");
        this.setWidth("100%");

        //insert two rows ... first row holds multi-day appointments
        // second row is just a splitter
        header.insertRow(0);
        header.insertRow(0);
        //insert 3 cells
        //1st cell is empty to align with the timeline
        //2nd cell holds appointments
        //3rd cell is empty, aligns with scrollbar
        header.insertCell(0, 0);
        header.insertCell(0, 0);
        header.insertCell(0, 0);

        //add panel to hold appointments
        header.setWidget(0, 1, grid);

        //set cell styles
        header.getCellFormatter().setStyleName(0, 0, TIMELINE_EMPTY_CELL_STYLE);
        header.getCellFormatter().setStyleName(0, 1, DAY_CONTAINER_CELL_STYLE);
        header.getCellFormatter().setStyleName(0, 2, SCROLLBAR_EMPTY_CELL_STYLE);
        header.getCellFormatter().setWidth(0, 2,
                WindowUtils.getScrollBarWidth(true) + "px");


        //default grid to 50px height
        grid.setHeight("30px");

        header.getFlexCellFormatter().setColSpan(1, 0, 3);
        header.setCellPadding(0);
        header.setBorderWidth(0);
        header.setCellSpacing(0);

        splitter.setStylePrimaryName(SPLITTER_STYLE);
        header.setWidget(1, 0, splitter);
    }

    public void setDays(Date date, int days) {

        grid.clear();
        float dayWidth = 100f / days;
        float dayLeft = 0f;

        for (int day = 0; day < days; day++) {

            dayLeft = dayWidth * day;

            SimplePanel dayPanel = new SimplePanel();
            dayPanel.setStyleName("day-separator");
            grid.add(dayPanel);
            DOM.setStyleAttribute(dayPanel.getElement(), "left", dayLeft + "%");
        }


        gridOverlay.setHeight("100%");
        gridOverlay.setWidth("100%");
        DOM.setStyleAttribute(gridOverlay.getElement(), "position", "absolute");
        DOM.setStyleAttribute(gridOverlay.getElement(), "left", "0px");
        DOM.setStyleAttribute(gridOverlay.getElement(), "top", "0px");
        grid.add(gridOverlay);
    }
}
