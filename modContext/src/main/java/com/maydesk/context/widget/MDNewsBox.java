package com.maydesk.context.widget;

import nextapp.echo.app.Extent;
import nextapp.echo.app.ImageReference;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 3, 2013
 */
public class MDNewsBox extends MDAbstractFigure {

	public static final String PROPERTY_WIDTH = "width";
	public static final String PROPERTY_HEIGHT = "height";
	public static final String PROPERTY_TEXT = "text";
	public static final String PROPERTY_TITLE = "title";
	public static final String PROPERTY_ICON = "icon";
	
	private String text;
	private String title;
	
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
	
	public ImageReference getIcon() {
		return (ImageReference) get(PROPERTY_ICON);
	}
	
	public void setIcon(ImageReference icon) {
		set(PROPERTY_ICON, icon);
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		String oldVal = this.text;
		this.text = text;
		firePropertyChange(PROPERTY_TEXT, oldVal, text);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		String oldVal = this.title;
		this.title = title;
		firePropertyChange(PROPERTY_TITLE, oldVal, title);
	}
}