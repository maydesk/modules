package com.maydesk.context.widget;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 3, 2013
 */
public class MDNewsTicker extends MDAbstractFigure {

	public static final String PROPERTY_AUTHOR = "author";
	public static final String PROPERTY_TEXT = "text";

	private String text;
	private String author;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		String oldVal = this.text;
		this.text = text;
		firePropertyChange(PROPERTY_TEXT, oldVal, text);
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		String oldVal = this.author;
		this.author = author;
		firePropertyChange(PROPERTY_AUTHOR, oldVal, author);
	}
}