package com.maydesk.context.widget;

import com.maydesk.context.BoardManager;

public class MDText extends MDAbstractFigure {

	public static final String PROPERTY_TEXT = "text";
	public static final String PROPERTY_TYPE = "type";
	public static final String PROPERTY_SIZE = "size";

	private String text;
	private int type = 0;

	public MDText(int x, int y, String text, int type, int size) {
		setPositionX(x);
		setPositionY(y);
		setText(text);
		setType(type);
		setSize(size);
	}
	
	public MDText() {
		setSize(16);
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		String oldVal = this.text;
		this.text = text;
		firePropertyChange(PROPERTY_TEXT, oldVal, this.text);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		int oldVal = this.type;
		this.type = type;
		firePropertyChange(PROPERTY_TYPE, oldVal, this.type);
	}
	
	public int getSize() {
		return (Integer) get(PROPERTY_SIZE);
	}

	public void setSize(int size) {
		set(PROPERTY_SIZE, size);
	}

	@Override
	public void processInput(String inputName, Object inputValue) {
		super.processInput(inputName, inputValue);
		if (PROPERTY_TEXT.equals(inputName)) {
			setText(inputValue.toString());
		} else if (PROPERTY_TYPE.equals(inputName)) {
			setType((Integer) inputValue);
		} else if (PROPERTY_SIZE.equals(inputName)) {
			setSize((Integer) inputValue);
		}
		super.processInput(inputName, inputValue);
	}
	
	@Override
	public void syncClone(MDAbstractFigure figClone) {
		super.syncClone(figClone);
		MDText textClone = (MDText)figClone;
		textClone.setSize(getSize());
		textClone.setText(getText());
		textClone.setType(getType());
	}
}