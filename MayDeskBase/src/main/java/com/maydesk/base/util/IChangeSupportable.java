package com.maydesk.base.util;

import nextapp.echo.app.Border;


public interface IChangeSupportable<T> {

	public void setValue(T value);
	
	public T getValue();

	public String getPropertyName();

	public void setChangeSupport(PDBinding support);

	public PDBinding getChangeSupport();

	public void setBorder(Border border);

	public void setEditable(boolean editable);
	
}
