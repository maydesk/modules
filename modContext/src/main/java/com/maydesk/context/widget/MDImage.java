package com.maydesk.context.widget;

import nextapp.echo.app.Extent;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.ResourceImageReference;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 3, 2013
 */
public class MDImage extends MDAbstractFigure {

	public static final String PROPERTY_SRC = "src";

	private ImageReference image;

	public MDImage() {
		// Empty
	}

	public MDImage(int x, int y, int width, int height, String src) {
		setPositionX(x);
		setPositionY(y);
		setWidth(new Extent(width));
		setHeight(new Extent(height));
		setImage(new ResourceImageReference(src));
	}

	public ImageReference getImage() {
		return image;
	}

	public void setImage(ImageReference image) {
		ImageReference oldVal = this.image;
		this.image = image;
		firePropertyChange(PROPERTY_SRC, oldVal, image);
	}

	@Override
	public void syncClone(MDAbstractFigure figClone) {
		super.syncClone(figClone);
		((MDImage) figClone).setImage(getImage());
	}
}