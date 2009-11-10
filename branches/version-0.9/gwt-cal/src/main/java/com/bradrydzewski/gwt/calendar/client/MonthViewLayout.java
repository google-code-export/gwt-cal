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

package com.bradrydzewski.gwt.calendar.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Panel;

public class MonthViewLayout {

    private static final int HORIZONTAL_DAYS = 7;

    static class Coordinate {

        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public String toString() {
            return "X coordinate: " + x + ", Y coordinate: " + y;
        }
    }

    public class AppointmentAdapter {

        private int row;
        private int columnStart;
        private int columnStop;
        private int order;
        private Appointment appointment;
        private Panel appointmentPanel;

        public AppointmentAdapter(Appointment appointment) {
            this.appointment = appointment;
        }

        public int getColumnStart() {
            return columnStart;
        }

        public void setColumnStart(int columnStart) {
            this.columnStart = columnStart;
        }

        public int getColumnStop() {
            return columnStop;
        }

        public void setColumnStop(int columnStop) {
            this.columnStop = columnStop;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }
        public Appointment getAppointment() {
        	return this.appointment;
        }

        public Panel getAppointmentPanel() {
			return appointmentPanel;
		}

		public void setAppointmentPanel(Panel appointmentPanel) {
			this.appointmentPanel = appointmentPanel;
		}

		public String toString() {
            return "row: " + row + ", col start: " + columnStart + ", col stop: " + columnStop + ", order: " + order;
        }
    }

    private HashMap<Integer, Map<Integer,Integer>> cellsSlots = new HashMap<Integer, Map<Integer,Integer>>();
    
    public List<AppointmentAdapter> doLayout(List<Appointment> appointments,
            Date startDate, Date endDate) {
        
        cellsSlots.clear();

        //list of adapters that take an appointment and map its coordinates
        List<AppointmentAdapter> adapters = new ArrayList<AppointmentAdapter>();

        //go through each appointment to place it on the screen
        for (Appointment appt : appointments) {
                List<AppointmentAdapter> tmpAdapterList = getAppointmentAdapters(appt,startDate);
                
                for(AppointmentAdapter adapter : tmpAdapterList) {
                    int order = calculateOrder(adapter);
                    adapter.setOrder(order);
                }
                
                adapters.addAll(tmpAdapterList);
            }

        return adapters;
    }

    private int calculateOrder(AppointmentAdapter adapter) {
        //check each cell to get the max adapter, start w/ 0 and increment until available
        int offset = adapter.getRow() * HORIZONTAL_DAYS;
        int start = adapter.getColumnStart()+offset;
        int stop = adapter.getColumnStop()+offset;
        int optimalSlot = 0;
        
        for(int i=start;i<=stop;i++) {
            //get list of appointments already in the cell
            Map<Integer,Integer> slotMap = cellsSlots.get(i);
            
            if(slotMap==null) {
                slotMap = new HashMap<Integer,Integer>();
                cellsSlots.put(i, slotMap);
            }
            
            int slot = 0;
            //need to figure out min available order
            while(slotMap.containsKey(slot)) {
                slot++;
            }
            
            optimalSlot = Math.max(optimalSlot, slot);
        }
        
        for(int i=start;i<=stop;i++) {
            cellsSlots.get(i).put(optimalSlot, optimalSlot);
        }
        
        return optimalSlot;
    }
    
    List<AppointmentAdapter> getAppointmentAdapters(Appointment appt, Date startDate) {
        List<AppointmentAdapter> adapters = new ArrayList<AppointmentAdapter>();

        //get the x,y coordinates for the cell the appointment will
        // start and end inside
        Coordinate startCoordinate = getGridCoordinate(appt.getStart(), startDate);
        Coordinate endCoordinate = getGridCoordinate(appt.getEnd(), startDate);
        boolean spansMultipleRows = startCoordinate.getX() != endCoordinate.getX();

        //an appointment may appear on multiple rows. for each row, 
        // a panel (gwt panel) must be created to represent the appointment.
        // for each panel we will have an adapter that plots it on the screen.
        for (int i = startCoordinate.getX(); i <= endCoordinate.getX(); i++) {
            int startCell = 0;
            int endCell = 0;

            //first row
            if (i == startCoordinate.getX()) {
                startCell = startCoordinate.getY();
                endCell = (spansMultipleRows) ? 6 : endCoordinate.getY();
            //last row
            } else if (i == endCoordinate.getX()) {
                startCell = 0;
                endCell = endCoordinate.getY();
            //middle row
            } else {
                startCell = 0;
                endCell = 6;
            }

            AppointmentAdapter adapter = new AppointmentAdapter(appt);
            adapter.setColumnStart(startCell);
            adapter.setColumnStop(endCell);
            adapter.setRow(i);
            adapters.add(adapter);
        }
        return adapters;
    }

    Coordinate getGridCoordinate(Date evalDate, Date startDate) {
        Coordinate c = new Coordinate();
        double cellNumber = DateUtils.differenceInDays(evalDate, startDate);

        double row = Math.floor(cellNumber / 7d);
        double col = Math.floor(cellNumber % 7d); //7 days (columns) per row

        c.setX((int) row);
        c.setY((int) col);
        return c;
    }
}