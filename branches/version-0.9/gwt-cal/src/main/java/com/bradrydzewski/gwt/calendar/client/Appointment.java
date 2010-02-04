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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Appointment implements Comparable<Appointment>, Serializable {

    private String title;
    private String description;
    private Date start;
    private Date end;
    private String location;
    private String createdBy;
    private List<Attendee> attendees = new ArrayList<Attendee>();
    private AppointmentStyle appointmentStyle = AppointmentStyles.BLUE;
    private String style = BLUE;
    //private boolean selected;
    private boolean multiDay = false;
    private boolean allDay = false;

    protected static final String STYLE_PREFIX = "gwt-appointment-";
    public static final String BLUE = STYLE_PREFIX + "blue";
    public static final String RED = STYLE_PREFIX + "red";
    public static final String PINK = STYLE_PREFIX + "pink";
    public static final String PURPLE = STYLE_PREFIX + "purple";
    public static final String DARK_PURPLE = STYLE_PREFIX + "darkpurple";
    public static final String STEELE_BLUE = STYLE_PREFIX + "steelblue";
    public static final String LIGHT_BLUE = STYLE_PREFIX + "lightblue";
    public static final String TEAL = STYLE_PREFIX + "teal";
    public static final String LIGHT_TEAL = STYLE_PREFIX + "lightteal";
    public static final String GREEN = STYLE_PREFIX + "green";
    public static final String LIGHT_GREEN = STYLE_PREFIX + "light";
    public static final String YELLOW_GREEN = STYLE_PREFIX + "yellowgreen";
    public static final String YELLOW = STYLE_PREFIX + "yellow";
    public static final String ORANGE = STYLE_PREFIX + "orange";
    public static final String RED_ORANGE = STYLE_PREFIX + "redorange";
    public static final String LIGHT_BROWN = STYLE_PREFIX + "lightbrown";
    public static final String LIGHT_PURPLE = STYLE_PREFIX + "lightpurple";
    public static final String GREY = STYLE_PREFIX + "grey";
    public static final String BLUE_GREY = STYLE_PREFIX + "bluegrey";
    public static final String YELLOW_GREY = STYLE_PREFIX + "yellowgrey";
    public static final String BROWN = STYLE_PREFIX + "brown";

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

//    public boolean isSelected() {
//        return selected;
//    }
//
//    public void setSelected(boolean selected) {
//        this.selected = selected;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<Attendee> attendees) {
        this.attendees = attendees;
    }

    public int compareTo(Appointment appt) {
        int compare = this.getStart().compareTo(appt.getStart());

        if (compare == 0) {
            compare = appt.getEnd().compareTo(this.getEnd());
        }

        return compare;
    }

    public boolean isMultiDay() {
        return multiDay;
    }

    public boolean isMultiDayAppointment() {
        if (getEnd() != null && getStart() != null) {
            return !DateUtils.areOnTheSameDay(getEnd(), getStart());
        }
        throw new IllegalStateException(
                "Calculating isMultiDayAppointment with no start/end dates set");
    }

    public void setMultiDay(boolean isMultiDay) {
        this.multiDay = isMultiDay;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }
    
    public Appointment clone() {
    	
    	Appointment clone = new Appointment();
    	clone.setAllDay(this.allDay);
    	clone.setAttendees(this.attendees);
    	clone.setCreatedBy(this.createdBy);
    	clone.setDescription(this.description);
    	clone.setEnd(this.end);
    	clone.setLocation(this.location);
    	clone.setMultiDay(this.multiDay);
    	//clone.setSelected(this.selected);
    	clone.setStart(this.start);
    	clone.setStyle(this.style);
    	clone.setTitle(this.title);
    	clone.setStyle(this.style);
    	
    	return clone;
    }

	public AppointmentStyle getAppointmentStyle() {
		return appointmentStyle;
	}

	public void setAppointmentStyle(AppointmentStyle appointmentStyle) {
		this.appointmentStyle = appointmentStyle;
	}
    
    
}
