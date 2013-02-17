package com.maydesk.context.widget;

import nextapp.echo.app.ImageReference;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 3, 2013
 */
public class MDImage extends MDAbstractFigure {

	public static final String PROPERTY_IMAGE = "image";
	
	private ImageReference image;
	
	public ImageReference getImage() {
		return image;
	}
	
	public void setImage(ImageReference image) {
		ImageReference oldVal = this.image;
		this.image = image;
		firePropertyChange(PROPERTY_IMAGE, oldVal, image);
	}
}