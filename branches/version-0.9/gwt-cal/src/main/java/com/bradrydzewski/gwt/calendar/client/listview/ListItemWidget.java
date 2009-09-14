package com.bradrydzewski.gwt.calendar.client.listview;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;

public class ListItemWidget extends Composite {

	private AbsolutePanel rootPanel = new AbsolutePanel();

	public ListItemWidget() {
		initWidget(rootPanel);
		
	}
}
