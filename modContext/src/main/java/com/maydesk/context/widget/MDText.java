package com.maydesk.context.widget;

import com.maydesk.context.ExternalContextUpdater;

public class MDText extends MDAbstractFigure {

	public static final String PROPERTY_TEXT = "text";
	public static final String PROPERTY_TYPE = "type";
	public static final String PROPERTY_SIZE = "size";

	public enum Type {
		BANNER, HEADER, PLAIN;

		public String toString() {
			return super.toString().toLowerCase();
		}
	}

	private String text;
	private Type type = Type.PLAIN;

	public MDText() {
		setSize(12);
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		String oldVal = this.text;
		this.text = text;
		firePropertyChange(PROPERTY_TEXT, oldVal, this.text);
	}

	public String getType() {
		return type.toString().toLowerCase();
	}

	public void setType(String type) {
		Type oldVal = this.type;
		this.type = Type.valueOf(type.toUpperCase());
		firePropertyChange(PROPERTY_TYPE, oldVal, this.type);
	}
	
	public int getSize() {
		return (Integer) get(PROPERTY_SIZE);
	}

	public void setSize(int width) {
		set(PROPERTY_SIZE, width);
	}

	@Override
	public void processInput(String inputName, Object inputValue) {
		super.processInput(inputName, inputValue);
		if (PROPERTY_TEXT.equals(inputName)) {
			setText(inputValue.toString());
		} else if (PROPERTY_TYPE.equals(inputName)) {
			setType(inputValue.toString());
		} else if (PROPERTY_SIZE.equals(inputName)) {
			setSize((Integer) inputValue);
		}

		ExternalContextUpdater.updateTextProps(this);
	}
}