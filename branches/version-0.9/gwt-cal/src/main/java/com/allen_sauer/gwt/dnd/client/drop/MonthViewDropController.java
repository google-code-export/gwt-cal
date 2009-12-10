package com.allen_sauer.gwt.dnd.client.drop;

import java.util.Date;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.CalendarWidget;
import com.bradrydzewski.gwt.calendar.client.MonthView.AppointmentWidget;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;

public class MonthViewDropController extends AbsolutePositionDropController {

	private int daysPerWeek;
	private int weeksPerMonth;
	private Date firstDateDisplayed;

	/**
	 * Flextable that displays a Month in grid format.
	 */
	private FlexTable monthGrid;

	/**
	 * List of all cells currently highlighted as an appointment
	 * is being dragged.
	 */
	private Element[] highlightedCells;

	public MonthViewDropController(AbsolutePanel dropTarget,
			FlexTable monthGrid) {

		super(dropTarget);
		this.monthGrid = monthGrid;
	}

	public void setDaysPerWeek(int daysPerWeek) {
		this.daysPerWeek = daysPerWeek;
	}

	public void setWeeksPerMonth(int weeksPerMonth) {
		this.weeksPerMonth = weeksPerMonth;
	}

	public Date getFirstDateDisplayed() {
		return firstDateDisplayed;
	}

	public void setFirstDateDisplayed(Date firstDateDisplayed) {
		this.firstDateDisplayed = firstDateDisplayed;
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
		int x = context.desiredDraggableX - dropTargetOffsetX + draggable.relativeX;
		int y = context.desiredDraggableY - dropTargetOffsetY + draggable.relativeY;
		
		//Now we need to figure out which cell to highlight based
		// on the X,Y coordinates
		int col = (int)Math.floor( x / (monthGrid.getOffsetWidth() / daysPerWeek) );
		int row = (int)Math.floor( y / (monthGrid.getOffsetHeight() / weeksPerMonth) )+1;
		
		
		//Get element for cell
		Element currHoveredCell =
			monthGrid.getFlexCellFormatter().getElement(row, col);
		
		//If this cell isn't already highlighted, we need to highlight
		if(highlightedCells==null || highlightedCells.length<0 || !currHoveredCell.equals(highlightedCells[0])) {
		
			if(highlightedCells!=null) {
				for(Element elem : highlightedCells) {
					if(elem!=null)
						DOM.setStyleAttribute(elem, "backgroundColor", "#FFFFFF");
				}
			}

			//here I hard-code 5 as the number of cells an appointment
			// should span. This, however, should be calculated.
			//Beware, we need to be very careful about memory here.
			// I tried to do a date diff calculation and got 
			// out of memory exceptions in the JVM AND in the chrome browser
			Date startDate = ((AppointmentWidget)draggable.widget).getAppointment().getStart();
			Date endDate = ((AppointmentWidget)draggable.widget).getAppointment().getEnd();
			
			int dateDiff = getDateDiff(startDate, endDate);
			dateDiff = (dateDiff <=0)?1:dateDiff;
			highlightedCells = getCells(row, col, dateDiff);
			
			//alter its style as "highlighted"
			//TODO: month view highlighted cell style be moved to the css style sheet
			for(Element elem : highlightedCells) {
				if(elem!=null)
					DOM.setStyleAttribute(elem, "backgroundColor", "#C3D9FF");
			}
		}
	}

	/**
	 * Callback method executed once the drag has completed.
	 * We need to reset the background color of all previously highlighted
	 * cells. Also need to actually change the appointment's start / end date
	 * here (code doesn't exist yet).
	 */
	@Override
	public void onDrop(DragContext context) {

		super.onDrop(context);
		
		for(Element elem : highlightedCells) {
			if(elem!=null)
				DOM.setStyleAttribute(elem, "backgroundColor", "#FFFFFF");
		}

		//reset highlighted cells to null
		highlightedCells = null;
		
		//get the draggable item
		Draggable draggable = draggableList.get(0);

		//get the appointment
		Appointment appt = ((AppointmentWidget)context.draggable).getAppointment();
		
		//get the date difference for the appointment
		int dateDiff = getDateDiff(appt.getStart(), appt.getEnd())-1;
		//dateDiff = (dateDiff <=0)?1:dateDiff;
		
		//get the column and row for the draggable widget
		int row = getRow(context, draggable)-1;
		int col = getColumn(context, draggable);
		int cell = row * daysPerWeek + col;
		
		//calculate the new start & end dates
		Date newStart = (Date)firstDateDisplayed.clone();
		newStart.setDate(newStart.getDate() + cell);
		newStart.setHours(appt.getStart().getHours());
		newStart.setMinutes(appt.getStart().getMinutes());
		Date newEnd = (Date)newStart.clone();
		newEnd.setDate(newEnd.getDate()+dateDiff);
		
		//Set the appointment's new start & end dates
		appt.setStart(newStart);
		appt.setEnd(newEnd);
	}
	
	/**
	 * Gets all the cells (as DOM Elements) that an appointment spans.
	 * Note: It only includes cells in the table. If an appointment
	 * ends in the following month the last cell in the list will
	 * be the last cell in the table.
	 * @param row Appointment's starting row
	 * @param col Appointment's starting column
	 * @param days Number of days an appointment spans
	 * @return Cell elements that an appointment spans
	 */
	protected Element[] getCells(int row, int col, int days) {
		
		Element[] elems = new Element[days];
		
		for(int i=0;i<days; i++) {
			
			if(col>daysPerWeek-1) {
				col = 0;
				row ++;
			}
			
			//Cheap code here. If the row / cell throw an out of index exception
			// we just break. THis kind of sucks because we have to 
			// now account for null items in the Element[] array.
			try {
				elems[i] = monthGrid.getFlexCellFormatter().getElement(row, col);
			}catch(Exception ex) {
				break;
			}
			
			col++;
		}
		
		return elems;
	}



	/**
	 * Gets the difference in days between two Dates.
	 * TODO: not correctly calculating when end date's month != start date's month
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public int getDateDiff(Date startDate, Date endDate) {
		
		if(startDate.getMonth()==endDate.getMonth()) {
			return endDate.getDate() - startDate.getDate()+1;
		}else
			return (int) Math.ceil(((endDate.getTime() - startDate.getTime())/(1000*60*60*24)))+1;
		
		
		//return (int) Math.ceil(((endDate.getTime() - startDate.getTime())/(1000*60*60*24)))+1;
	}
	
	
	
	public int getRow(DragContext context, Draggable draggable) {

		//get the mouse/drag coordinates
		int y = context.desiredDraggableY - dropTargetOffsetY + draggable.relativeY;
		
		//Now we need to figure out which cell to highlight based
		// on the X,Y coordinates
		int row = (int)Math.floor( y / (monthGrid.getOffsetHeight() / weeksPerMonth) )+1;

		return row;
	}
	
	public int getColumn(DragContext context, Draggable draggable) {

		//get the mouse/drag coordinates
		int x = context.desiredDraggableX - dropTargetOffsetX + draggable.relativeX;

		//Now we need to figure out which cell to highlight based
		// on the X,Y coordinates
		int col = (int)Math.floor( x / (monthGrid.getOffsetWidth() / daysPerWeek) );

		return col;
	}
}
