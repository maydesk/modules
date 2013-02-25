package com.maydesk.context.widget;

import nextapp.echo.app.ImageReference;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 3, 2013
 */
public class MDAvatar extends MDAbstractFigure {

	public static final String PROPERTY_TEXT = "text";
	public static final String PROPERTY_SRC = "src";

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
		firePropertyChange(PROPERTY_SRC, oldVal, image);
	}

	@Override
	public void syncClone(MDAbstractFigure figClone) {
		super.syncClone(figClone);
		MDAvatar avatarClone = (MDAvatar) figClone;
		avatarClone.setText(getText());
		avatarClone.setImage(getImage());
	}
}