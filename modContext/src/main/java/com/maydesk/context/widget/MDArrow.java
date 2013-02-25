package com.maydesk.context.widget;

import nextapp.echo.app.Extent;

import com.maydesk.context.BoardManager;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 6, 2013
 */
public class MDArrow extends MDAbstractFigure {

	public static final String PROPERTY_SIZE = "size";
	public static final String PROPERTY_START_POSITION_X = "startPosX";
	public static final String PROPERTY_START_POSITION_Y = "startPosY";
	public static final String PROPERTY_END_POSITION_X = "endPosX";
	public static final String PROPERTY_END_POSITION_Y = "endPosY";

	public MDArrow() {
		setWidth(new Extent(100));
		setHeight(new Extent(100));
		setSize(10);
	}
	
	public int getSize() {
		return (Integer) get(PROPERTY_SIZE);
	}

	public void setSize(int width) {
		set(PROPERTY_SIZE, width);
	}
	
	public int getStartPosX() {
		return (Integer) get(PROPERTY_START_POSITION_X);
	}

	public void setStartPosX(int startPosX) {
		set(PROPERTY_START_POSITION_X, startPosX);
	}
	
	public int getStartPosY() {
		return (Integer) get(PROPERTY_START_POSITION_Y);
	}
	
	public void setStartPosY(int startPosY) {
		set(PROPERTY_START_POSITION_Y, startPosY);
	}
	
	public int getEndPosX() {
		return (Integer) get(PROPERTY_END_POSITION_X);
	}
	
	public void setEndPosX(int endPosX) {
		set(PROPERTY_END_POSITION_X, endPosX);
	}
	
	public int getEndPosY() {
		return (Integer) get(PROPERTY_END_POSITION_Y);
	}
	
	public void setEndPosY(int endPosY) {
		set(PROPERTY_END_POSITION_Y, endPosY);
	}

	@Override
	public void processInput(String inputName, Object inputValue) {
		if (inputValue == null) {
			return;
		}
		
		super.processInput(inputName, inputValue);
		if (PROPERTY_SIZE.equals(inputName)) {
			setSize((Integer) inputValue);
		} else if (PROPERTY_START_POSITION_X.equals(inputName)) {
			setStartPosX((Integer) inputValue);
		} else if (PROPERTY_START_POSITION_Y.equals(inputName)) {
			setStartPosY((Integer) inputValue);
		} else if (PROPERTY_END_POSITION_X.equals(inputName)) {
			setEndPosX((Integer) inputValue);
		} else if (PROPERTY_END_POSITION_Y.equals(inputName)) {
			setEndPosY((Integer) inputValue);
		}
		
		BoardManager.getInstance().updateProps(this);
	}
	
	@Override
	public void syncClone(MDAbstractFigure figClone) {
		super.syncClone(figClone);
		MDArrow arrowClone = (MDArrow)figClone;
		arrowClone.setSize(getSize());
		arrowClone.setStartPosX(getStartPosX());
		arrowClone.setStartPosY(getStartPosY());
		arrowClone.setEndPosX(getEndPosX());
		arrowClone.setEndPosY(getEndPosY());
	}

}