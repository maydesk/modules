/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table;

import nextapp.echo.app.event.TableModelListener;
import nextapp.echo.app.table.DefaultTableModel;

public abstract class PDTableModel extends DefaultTableModel {

	protected TableModelListener cellChangeListener;

	public abstract void setValueAt(Object value, int column, int row);

	/**
	 * Set the listener which fires an event when the content of a cell changes
	 * This is meant for updating the model, and save the change to the
	 * database. This is in order to avoid that fireTableDataChanged() is called
	 * which would re-draw the whole table, and mess up with the focus tab order
	 */
	public void setCellChangeListener(TableModelListener cellChangeListener) {
		this.cellChangeListener = cellChangeListener;
	}
	
	public int getTotalRowCount() {
		return getRowCount();
    }
}
