package com.bradrydzewski.gwt.calendar.theme.google.client;


class GoogleAppointmentStyle {

	public GoogleAppointmentStyle(String border, String background) {
		super();
		
		//set the border
		this.border = background;
		this.selectedBorder = border;
		
		//set the body text
		this.text = "#FFFFFF";
		this.selectedText = text;
		
		//set the header text
		this.headerText = text;
		this.selectedHeaderText = text;
		
		//set the background colors
		this.background = background;
		this.selectedBackground = background;
		
		//set the header colors to the same color as the border
		this.backgroundHeader = border;
		this.selectedBackgroundHeader = border;
		
	}
	
    protected String selectedBorder;
    protected String selectedBackground;
    protected String selectedBackgroundImage;
    protected String selectedBackgroundHeader;
    protected String selectedBackgroundFooter;
    protected String selectedText;
    protected String selectedHeaderText;
    protected String border;
    protected String background;
    protected String backgroundImage;
    protected String backgroundHeader;
    protected String backgroundFooter;
    protected String text;
    protected String headerText;

    public String getSelectedBorder() {
        return selectedBorder;
    }

    public String getSelectedBackground() {
        return selectedBackground;
    }

    public String getSelectedBackgroundHeader() {
        return selectedBackgroundHeader;
    }

    public String getSelectedBackgroundFooter() {
        return selectedBackgroundFooter;
    }

    public String getSelectedText() {
        return selectedText;
    }

    public String getSelectedHeaderText() {
        return selectedHeaderText;
    }

    public String getBorder() {
        return border;
    }

    public String getBackground() {
        return background;
    }

    public String getBackgroundHeader() {
        return backgroundHeader;
    }

    public String getBackgroundFooter() {
        return backgroundFooter;
    }

    public String getText() {
        return text;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setSelectedBorder(String selectedBorder) {
        this.selectedBorder = selectedBorder;
    }

    public void setSelectedBackground(String selectedBackground) {
        this.selectedBackground = selectedBackground;
    }

    public void setSelectedBackgroundHeader(String selectedBackgroundHeader) {
        this.selectedBackgroundHeader = selectedBackgroundHeader;
    }

    public void setSelectedBackgroundFooter(String selectedBackgroundFooter) {
        this.selectedBackgroundFooter = selectedBackgroundFooter;
    }

    public void setSelectedText(String selectedText) {
        this.selectedText = selectedText;
    }

    public void setSelectedHeaderText(String selectedHeaderText) {
        this.selectedHeaderText = selectedHeaderText;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public void setBackgroundHeader(String backgroundHeader) {
        this.backgroundHeader = backgroundHeader;
    }

    public void setBackgroundFooter(String backgroundFooter) {
        this.backgroundFooter = backgroundFooter;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public String getSelectedBackgroundImage() {
        return selectedBackgroundImage;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setSelectedBackgroundImage(String selectedBackgroundImage) {
        this.selectedBackgroundImage = selectedBackgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
