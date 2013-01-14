package com.bradrydzewski.gwt.calendar.client;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum AppointmentStyle implements Serializable, IsSerializable {
	BLUE, RED, PINK, PURPLE, DARK_PURPLE, STEELE_BLUE, LIGHT_BLUE,
	TEAL, LIGHT_TEAL, GREEN, LIGHT_GREEN, YELLOW_GREEN, YELLOW,
	ORANGE, RED_ORANGE, LIGHT_BROWN, LIGHT_PURPLE, GREY, BLUE_GREY,
	YELLOW_GREY, BROWN, DEFAULT, CUSTOM
}
