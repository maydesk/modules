package com.maydesk.context.widget;

import nextapp.echo.app.Extent;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.ResourceImageReference;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 3, 2013
 */
public class MDNewsBox extends MDAbstractFigure {

	public static final String PROPERTY_TEXT = "text";
	public static final String PROPERTY_TITLE = "title";
	public static final String PROPERTY_ICON = "icon";
	
	private String text;
	private String title;
	
	public MDNewsBox() {
		// Empty
	}

	public MDNewsBox(int x, int y, int width, int height, String icon, String title, String text) {
		setPositionX(x);
		setPositionY(y);
		setWidth(new Extent(width));
		setHeight(new Extent(height));
		setIcon(new ResourceImageReference(icon));
		setTitle(title);
		setText(text);
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