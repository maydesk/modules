package com.maydesk.context.widget;

import nextapp.echo.app.ImageReference;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 3, 2013
 */
public class MDAvatar extends MDAbstractFigure {

	public static final String PROPERTY_TEXT = "text";
	public static final String PROPERTY_IMAGE = "image";
	
	private String text;
	private ImageReference image;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		String oldVal = this.text;
		this.text = text;
		firePropertyChange(PROPERTY_TEXT, oldVal, text);
	}
	
	public ImageReference getImage() {
		return image;
	}
	
	public void setImage(ImageReference image) {
		ImageReference oldVal = this.image;
		this.image = image;
		firePropertyChange(PROPERTY_IMAGE, oldVal, image);
	}
}