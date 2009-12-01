package com.allen_sauer.gwt.dnd.client.drop;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;

public class MonthViewDropController extends AbsolutePositionDropController {

	private int weekdayHeaderHeight;
	private int dayHeaderHeight;
	private int daysPerWeek;
	private int weeksPerMonth;
	private FlexTable monthGrid;

	private Element highlightedCell;

	public MonthViewDropController(AbsolutePanel dropTarget, FlexTable monthGrid) {

		super(dropTarget);
		this.monthGrid = monthGrid;
	}

	public void setWeekdayHeaderHeight(int weekdayHeaderHeight) {
		this.weekdayHeaderHeight = weekdayHeaderHeight;
	}

	public void setDayHeaderHeight(int dayHeaderHeight) {
		this.dayHeaderHeight = dayHeaderHeight;
	}

	public void setDaysPerWeek(int daysPerWeek) {
		this.daysPerWeek = daysPerWeek;
	}

	public void setWeeksPerMonth(int weeksPerMonth) {
		this.weeksPerMonth = weeksPerMonth;
	}

	@Override
	public void onMove(DragContext context) {
		super.onMove(context);
		
		//get the draggable object
		Draggable draggable = draggableList.get(0);
		
		//make sure it isn't null (shouldn't ever be)
		if(draggable==null)
			return;
		
		//get the mouse/drag coordinates
		int x = draggable.desiredX;
		int y = draggable.desiredY;
		
		//Now we need to figure out which cell to highlight based
		// on the X,Y coordinates
		Element currHoveredCell =
			monthGrid.getFlexCellFormatter().getElement(2, 0);
		
		//If this cell isn't already highlighted, we need to highlight
		if(!currHoveredCell.equals(highlightedCell)) {
		
			highlightedCell = currHoveredCell;
			
			//alter its style as "highlighted"
			DOM.setStyleAttribute(highlightedCell, "backgroundColor", "#000999");
		}
	}

	@Override
	public void onDrop(DragContext context) {
		// TODO Auto-generated method stub
		super.onDrop(context);
		DOM.setStyleAttribute(highlightedCell, "backgroundColor", "#FFFFFF");
		highlightedCell = null;
	}
}
