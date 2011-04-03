/*
 * This file is part of gwt-cal
 * Copyright (C) 2010-2011  Scottsdale Software LLC
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.gwtcal.client;

import java.util.Date;

/**
 * Defines access operations for properties required to render an appointment
 * in gwt-cal. Implementations of this interface should provide their own
 * type to be used as the source for appointment details.
 *
 * @param <T>
 * @author Brad Rydzewski
 * @author Carlos Morales
 */
public interface AppointmentProvider<T> {

    /**
     * Returns appointment start date.
     *
     * @param value The original source object from which the start date/time can be extracted from
     * @return The start date of an appointment
     */
    public Date getStart(T value);

    /**
     * Returns appointment end date.
     *
     * @param value The original source object from which the end date/time can be extracted from
     * @return The end date/time of an appointment
     */
    public Date getEnd(T value);

    /**
     * The title (short description).
     *
     * @param value The original source object from which the title can be extracted from
     * @return The appointment title
     */
    public String getTitle(T value);

    /**
     * Returns a textual description of the provided appointment.
     *
     * @param value The original source object from which the description can be extracted from
     * @return The appointment description
     */
    public String getDescription(T value);


    /**
     * Indicates whether the appointment should be handled as 'all day'
     * for the purposes of rendering it in a screen.
     *
     * @param value The original source object from which the boolean value can be extracted from
     * @return <code>true</code> if the appointment should be considered 'all day',
     * <code>false</code> otherwise
     */
    public boolean isAllDay(T value);

    /**
     * Indicates whether the passed appointment should be handled as multi-day
     * (spanning multiple days).
     *
     * @param value The source object from which the boolean value can be extracted from
     * @return <code>true</code> if the appointment should be rendered as spanning
     * multiple days, <code>false</code> otherwise
     */
    public boolean isMultiDay(T value);
}
