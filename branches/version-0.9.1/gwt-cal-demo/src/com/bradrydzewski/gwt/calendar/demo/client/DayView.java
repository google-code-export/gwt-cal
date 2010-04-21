package com.bradrydzewski.gwt.calendar.demo.client;

import com.bradrydzewski.gwt.calendar.client.CalendarFormat;
import com.bradrydzewski.gwt.calendar.client.util.FormattingUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class DayView extends ResizeComposite {

	private static DayViewUiBinder uiBinder = GWT.create(DayViewUiBinder.class);

	interface DayViewUiBinder extends UiBinder<Widget, DayView> {
	}

//	@UiField DockLayoutPanel dockScrollPanel;
	@UiField FlowPanel timelinePanel;
	@UiField FlowPanel grid;
	@UiField FlowPanel gridOverlay;

	public DayView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		buildTimeline();
		buildSingleDayCanvas();
	}

	protected void buildDayHeaders() {
		
	}
	
	protected void buildMultiDayCanvas() {
		
	}
	
	protected void buildSingleDayCanvas() {
		int workingHourStart = 8;
		int workingHourStop = 17;
		int days = 3;

		grid.clear();
	                

		int intervalsPerHour = 2;
		float intervalSize = 30;
		grid.getElement().getStyle().setPosition(Position.RELATIVE);
		grid.setHeight( ((intervalsPerHour * (intervalSize)+1) * 24 ) +"px" );
			//+1 stands for 1px for border offset
			//it would probably be better to just get the timeline offset height??
						
			float dayWidth = 100f / days;
			float dayLeft = 0f;

			// for(int days=0;days<3;days++) {

			for (int i = 0; i < 24; i++) {
				
				boolean isWorkingHours = (i >= workingHourStart && i <= workingHourStop);
				
				//create major interval
				SimplePanel sp1 = new SimplePanel();
				sp1.setStyleName("major-time-interval");
				sp1.setHeight(intervalSize+"px");
				
				//if working hours set
				if (isWorkingHours) {
					sp1.addStyleName("working-hours");
				}
				
				//add to body
				grid.add(sp1);
				
				for(int x=0;x<intervalsPerHour-1;x++) {
					SimplePanel sp2 = new SimplePanel();
					sp2.setStyleName("minor-time-interval");
					
					sp2.setHeight(intervalSize+FormattingUtil.getBorderOffset()+"px");
					if (isWorkingHours) {
						sp2.addStyleName("working-hours");
					}
					// sp1.add(new Label("-"));
					grid.add(sp2);
				}
				
				
			}
			// }

			for (int day = 0; day < days; day++) {

				dayLeft = dayWidth * day;

				SimplePanel dayPanel = new SimplePanel();
				dayPanel.setStyleName("day-separator");
				grid.add(dayPanel);
				DOM.setStyleAttribute(dayPanel.getElement(), "left", dayLeft
						+ "%");
			}
	                
	                
//	                gridOverlay.setHeight("100%");
//	                gridOverlay.setWidth("100%");
//	                DOM.setStyleAttribute(gridOverlay.getElement(), "position", "absolute");
//	                DOM.setStyleAttribute(gridOverlay.getElement(), "left", "0px");
//	                DOM.setStyleAttribute(gridOverlay.getElement(), "top", "0px");
//	                grid.add(gridOverlay);
		
	}

	
    protected void buildTimeline() {
        timelinePanel.clear();
        float labelHeight = 2*30;
        int i = 0;

        while (i < CalendarFormat.HOURS_IN_DAY) {

            String hour = CalendarFormat.INSTANCE.getHourLabels()[i];
            i++;

            SimplePanel hourWrapper = new SimplePanel();
            hourWrapper.setStylePrimaryName("time-strip");
            hourWrapper.setHeight((labelHeight + FormattingUtil.getBorderOffset()) + "px");

            FlowPanel flowPanel = new FlowPanel();
            flowPanel.setStyleName("hour-layout");
            

            
            String amPm = " ";
            if(i<13)
                amPm += CalendarFormat.INSTANCE.getAm();
            else if(i>13)
                amPm += CalendarFormat.INSTANCE.getPm();
            else {
            	hour = CalendarFormat.INSTANCE.getNoon();
            	amPm = "";
            }
            
            Label hourLabel = new Label(hour);
            hourLabel.setStylePrimaryName("hour-text");
            flowPanel.add(hourLabel);
            
            Label ampmLabel = new Label(amPm);
            ampmLabel.setStylePrimaryName("ampm-text");
            //if(includeAMPM)
            	flowPanel.add(ampmLabel);

            hourWrapper.add(flowPanel);
            
            timelinePanel.add(hourWrapper);
        }
    }
	
	
	
//
//	@UiHandler("button")
//	void onClick(ClickEvent e) {
//		Window.alert("Hello!");
//	}

}
