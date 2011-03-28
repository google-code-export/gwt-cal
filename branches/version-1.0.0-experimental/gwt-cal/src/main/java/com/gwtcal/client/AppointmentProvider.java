/*
 * This file is part of gwt-cal
 * Copyright (C) 2010  Scottsdale Software LLC
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
 *
 * @author Brad Rydzewski
 * @author Carlos Morales
 */
public interface AppointmentProvider<T> {

    public Date getStart(T value);

    public Date getEnd(T value);

    public String getTitle(T value);

    public String getDescription(T value);

    public boolean isAllDay(T value);
}
