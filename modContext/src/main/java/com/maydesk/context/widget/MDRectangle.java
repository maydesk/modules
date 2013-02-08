package com.maydesk.context.widget;

import nextapp.echo.app.Extent;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 6, 2013
 */
public class MDRectangle extends MDAbstractFigure {

	public static final String PROPERTY_WIDTH = "width";
	public static final String PROPERTY_HEIGHT = "height";

	public int getWidth() {
		return (Integer) get(PROPERTY_WIDTH);
	}

	public void setWidth(Extent width) {
		set(PROPERTY_WIDTH, width);
	}

	public Extent getHeight() {
		return (Extent) get(PROPERTY_HEIGHT);
	}

	public void setHeight(Extent height) {
		set(PROPERTY_HEIGHT, height);
	}
}