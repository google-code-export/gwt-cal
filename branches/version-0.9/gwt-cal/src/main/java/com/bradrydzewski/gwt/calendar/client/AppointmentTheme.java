package com.bradrydzewski.gwt.calendar.client;

/**
 * Returns the available styles in a theme. Actual implementations to be configured and used
 * through Deferred Binding.
 * <br/>
 * Specific styles not available in a theme, should provide a sensible default.
 *
 * @author Brad Rydzewski
 * @author Carlos D. Morales
 */
public abstract class AppointmentTheme {

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>blue</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with blue as unifying color.
     */
	public abstract AppointmentStyle getBlueStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>red</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with red as unifying color.
     */
	public abstract AppointmentStyle getRedStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>ping</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with ping as unifying color.
     */
	public abstract AppointmentStyle getPinkStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>purple</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with purple as unifying color.
     */
	public abstract AppointmentStyle getPurpleStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>dark-purple</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with dark-purple as unifying color.
     */
	public abstract AppointmentStyle getDarkPurpleStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>steel-blue</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with red as unifying color.
     */
	public abstract AppointmentStyle getSteeleBlueStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>light-blue</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with light-blue as unifying color.
     */
	public abstract AppointmentStyle getLightBlueStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>teal</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with teal as unifying color.
     */
	public abstract AppointmentStyle getTealStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>light-teal</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with light-teal as unifying color.
     */
	public abstract AppointmentStyle getLightTealStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>green</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with green as unifying color.
     */
	public abstract AppointmentStyle getGreenStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>light-green</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with light-green as unifying color.
     */
	public abstract AppointmentStyle getLightGreenStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>yellow-green</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with yellow-green as unifying color.
     */
	public abstract AppointmentStyle getYellowGreenStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>yellow</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with yellow as unifying color.
     */
	public abstract AppointmentStyle getYellowStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>orange</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with orange as unifying color.
     */
	public abstract AppointmentStyle getOrangeStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>red-orange</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with red-orange as unifying color.
     */
	public abstract AppointmentStyle getRedOrangeStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>light-brown</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with light-brown as unifying color.
     */
	public abstract AppointmentStyle getLightBrownStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>light-purple</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with light-purple as unifying color.
     */
	public abstract AppointmentStyle getLightPurpleStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>grey</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with grey as unifying color.
     */
	public abstract AppointmentStyle getGreyStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>blue-grey</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with blue-grey as unifying color.
     */
	public abstract AppointmentStyle getBlueGreyStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>yellow-grey</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with yellow-grey as unifying color.
     */
	public abstract AppointmentStyle getYellowGreyStyle();

    /**
     * Returns the <code>AppointmentStyle</code> that has <em>brown</em> as the unifying color for display.
     *
     * @return An <code>AppointmentStyle</code> with brown as unifying color.
     */
	public abstract AppointmentStyle getBrownStyle();
}