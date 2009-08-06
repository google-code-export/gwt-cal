/*
 * This file is part of gwt-cal
 * Copyright (C) 2009  Brad Rydzewski
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
import java.util.List;

/**
 * The Appointment Adapter is used to track the layout of an
 * AppointmentInterface. It adds additional fields required to
 * calculate layout that are used by the Layout Strategy classes.
 * 
 * This adapter allows us to keep these fields outside of the
 * main AppointmentInterface and implementations hiding
 * the layout complexity from the user.
 * @author Brad Rydzewski
 */
public class AppointmentAdapter {

    private AppointmentInterface appointment;
    private int cellStart;
    private int cellSpan;
    private int columnStart = -1;
    private int columnSpan;
    private int appointmentStart;
    private int appointmentEnd;
    private float cellPercentFill;
    private float cellPercentStart;
    private List<TimeBlock> intersectingBlocks;

    public AppointmentAdapter(AppointmentInterface appointment) {
        this.appointment = appointment;
        this.appointmentStart = calculateDateInMinutes(appointment.getStart());
        this.appointmentEnd = calculateDateInMinutes(appointment.getEnd());
        this.intersectingBlocks = new ArrayList<TimeBlock>();
    }

    public int getCellStart() {
        return cellStart;
    }

    public void setCellStart(int cellStart) {
        this.cellStart = cellStart;
    }

    public int getCellSpan() {
        return cellSpan;
    }

    public void setCellSpan(int cellSpan) {
        this.cellSpan = cellSpan;
    }

    public int getColumnStart() {
        return columnStart;
    }

    public void setColumnStart(int columnStart) {
        this.columnStart = columnStart;
    }

    public int getColumnSpan() {
        return columnSpan;
    }

    public void setColumnSpan(int columnSpan) {
        this.columnSpan = columnSpan;
    }

    public int getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(int appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public int getAppointmentEnd() {
        return appointmentEnd;
    }

    public void setAppointmentEnd(int appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    public List<TimeBlock> getIntersectingBlocks() {
        return intersectingBlocks;
    }

    public void setIntersectingBlocks(List<TimeBlock> intersectingBlocks) {
        this.intersectingBlocks = intersectingBlocks;
    }

    public AppointmentInterface getAppointment() {
        return appointment;
    }

    protected int calculateDateInMinutes(Date date) {
        return date.getHours() * 60 + date.getMinutes();
    }

    public float getCellPercentFill() {
        return cellPercentFill;
    }

    public void setCellPercentFill(float cellPercentFill) {
        this.cellPercentFill = cellPercentFill;
    }

    public float getCellPercentStart() {
        return cellPercentStart;
    }

    public void setCellPercentStart(float cellPercentStart) {
        this.cellPercentStart = cellPercentStart;
    }
}
