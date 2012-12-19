/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table;

import java.util.List;
import java.util.Vector;

import com.maydesk.base.util.SelectableObjectBean;

/**
 * 
 * Use PDTableModel3 instead
 * 
 * @author chrismay
 */
@Deprecated
public class PDTableModel2<T> extends PDTableModel {

	protected List<T> values = new Vector<T>();
	protected List<String> columns = new Vector<String>();
	protected int currentPos = 0;

	@Override
	public int getColumnCount() {
		return columns.size();
	}

	@Override
	public int getRowCount() {
		if (values.size() > 20)
			return 20;
		return values.size();
	}

	@Override
	public Object getValueAt(int col, int row) {
		if (values.size() <= currentPos + row) {
			return null;
		}
		return values.get(currentPos + row);
	}

	public void setValues(List values) {
		this.values = values;
	}

	@Override
	public String getColumnName(int col) {
		return columns.get(col);
	}

	public void addColumn(String title) {
		columns.add(title);
	}

	public int getCurrentPos() {
		return currentPos;
	}

	public void setCurrentPos(int currentPos) {
		this.currentPos = currentPos;
	}

	@Override
	public int getTotalRowCount() {
		return values.size();
	}

	@Override
	public void setValueAt(Object value, int col, int row) {
		Object o = values.get(row);
		if (o instanceof SelectableObjectBean && col == 0) {
			((SelectableObjectBean) o).setSelected((Boolean) value);
		}
	}

	public List<T> getValues() {
		return values;
	}
}
