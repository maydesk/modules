package com.maydesk.context.widget;

import nextapp.echo.app.Extent;
import nextapp.echo.app.ImageReference;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 3, 2013
 */
public class MDImage extends MDAbstractFigure {

	public static final String PROPERTY_IMAGE = "image";
	public static final String PROPERTY_WIDTH = "width";
	public static final String PROPERTY_HEIGHT = "height";
	
	private ImageReference image;
	
	public ImageReference getImage() {
		return image;
	}
	
	public void setImage(ImageReference image) {
		ImageReference oldVal = this.image;
		this.image = image;
		firePropertyChange(PROPERTY_IMAGE, oldVal, image);
	}
	
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