package com.bradrydzewski.gwt.calendar.client;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Attendee implements Serializable {

	private String name;
	private String email;
	private Attending attending = Attending.Maybe;
	private String imageUrl;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Attending getAttending() {
		return attending;
	}
	public void setAttending(Attending attending) {
		this.attending = attending;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
