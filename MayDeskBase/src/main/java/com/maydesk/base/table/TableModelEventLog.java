/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table;

import nextapp.echo.app.table.TableModel;

/**
 * @author Alejandro Salas <br>
 * Created on Feb 8, 2007
 */
public class TableModelEventLog extends nextapp.echo.app.event.TableModelEvent {

	private Object oldValue;
	private Object newValue;

	public TableModelEventLog(TableModel source, int column, int firstRow, int lastRow, int type, Object oldValue, Object newValue) {
		super(source, column, firstRow, lastRow, type);
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public Object getNewValue() {
		return newValue;
	}

	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}

	public Object getOldValue() {
		return oldValue;
	}

	public void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}
}