/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.util;

import java.util.List;
import java.util.Vector;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

/** 
 * @author Alejandro Salas 
 * <br> Created on Jul 6, 2007
 */
public class SelectableObjectBean<T> {

	private boolean selected;
	private T object;
	private List<ActionListener> listeners = new Vector<ActionListener>();
	public SelectableObjectBean(T object) {
		this.object = object;
	}
	
	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		ActionEvent e = new ActionEvent(this, null);
		for (ActionListener l : listeners) {
			l.actionPerformed(e);
		}
	}

	public void addActionListener(ActionListener l) {
		listeners.add(l);	    
    }
}
