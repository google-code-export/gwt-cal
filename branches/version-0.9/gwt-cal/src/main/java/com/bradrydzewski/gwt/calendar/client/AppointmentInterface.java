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

import java.util.Date;

/**
 *
 * @author Brad Rydzewski
 */
public interface AppointmentInterface extends Comparable<AppointmentInterface> {//implements SourcesMouseEvents {

	public String getTitle();
	public String getDescription();
	public void setTitle(String title);
	public void setDescription(String desc);
	public Date getStart();
	public void setStart(Date start);
	public Date getEnd();
	public void setEnd(Date end);
	public boolean isSelected();
	public void setSelected(boolean isSelected);
	public void formatTimeline(float top, float height);
        public boolean isMultiDay();
	public void setMultiDay(boolean isMultiDay);

    float getTop();
    float getLeft();
    float getWidth();
    float getHeight();
    void setTop(float top);
    void setLeft(float left);
    void setWidth(float width);
    void setHeight(float height); 
}
