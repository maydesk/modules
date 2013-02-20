package com.maydesk.context.widget;

import com.maydesk.context.ExternalContextUpdater;

import nextapp.echo.app.Color;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 6, 2013
 */
public class MDRectangle extends MDAbstractFigure {

	public static final String PROPERTY_BORDER = "border";

	public MDRectangle() {
		setBackground(Color.DARKGRAY);
		setBorder(0);
	}
	
	public int getBorder() {
		return (Integer) get(PROPERTY_BORDER);
	}

	public void setBorder(int border) {
		set(PROPERTY_BORDER, border);
	}

	@Override
	public void processInput(String inputName, Object inputValue) {
		super.processInput(inputName, inputValue);
		if (PROPERTY_BORDER.equals(inputName)) {
			setBorder((Integer) inputValue);
		} else if (PROPERTY_BACKGROUND.equals(inputName)) {
			setBackground((Color) inputValue);
		}
		
		ExternalContextUpdater.updateRectProps(this);
	}
}