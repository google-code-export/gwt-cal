package com.gwtcal.demo.client;

import java.util.Date;

/**
 * Simple JavaBean to hold state and properties of an appointment.
 * 
 * @author Brad Rydzewski
 * @author Carlos D. Morales
 */
public class Appointment {
	
	private Date start;
	private Date end;
	private String Title;
	private String Description;

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
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
}
