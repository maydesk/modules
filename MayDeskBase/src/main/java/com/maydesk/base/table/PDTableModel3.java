/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table;

import java.lang.reflect.Method;
import java.text.Format;
import java.util.List;
import java.util.Vector;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.util.SelectableObjectBean;

import nextapp.echo.app.table.AbstractTableModel;


public class PDTableModel3<T> extends AbstractTableModel {

	protected List<T> values = new Vector<T>();
	protected List<MyColumn> columns = new Vector<MyColumn>();
	protected int currentPos = 0;
	
	public void setSelected(boolean selected) {
		for (T t : values) {
			if (t instanceof SelectableObjectBean<?>) {
				((SelectableObjectBean<?>)t).setSelected(selected);
			}
		}
	}

	public void setSelected(boolean selected, int row) {
		T t = values.get(row);
		if (t instanceof SelectableObjectBean<?>) {
			((SelectableObjectBean<?>)t).setSelected(selected);
		}
	}

	public List<T> getValues() {
    	return values;
    }
	
	public int getColumnCount() {
	    return columns.size();
    }

	public int getRowCount() {
		if (values.size() > 20) return 20;
	    return values.size();
    }

	public Object getValueAt(int col, int row) {
		if (values.size() <= currentPos + row) {
			return null;
		}
		Object value = values.get(currentPos + row);
		if (value instanceof SelectableObjectBean<?>) {
			if (col == 0) {
				return value;
			}
			value = ((SelectableObjectBean<?>)value).getObject();
		}
		if (value == null) {
			return null;
		}
		MyColumn column = columns.get(col);
		String methodName = ((Enum)column.title).name();
		methodName = "get" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
		try {
	        Method method = value.getClass().getMethod(methodName, new Class[0]);
	        Object o = method.invoke(value, new Object[0]);
	        if (column.format != null) {
	        	return column.format.format(o);
	        } else {
	        	return o;
	        }
	        
        } catch (Exception e) {
	        e.printStackTrace();
        	return e.toString();
        }
    }
	
	public void setValues(List<T> values) {
		this.values = values;
	}

	public void addColumn(Translatable title) {
		addColumn(title, null); 
	}

	public void addColumn(Translatable title, Format format) {
		MyColumn column = new MyColumn();
		column.title = title;
		column.format = format;
		columns.add(column);	    
    }

	public int getCurrentPos() {
    	return currentPos;
    }

	public void setCurrentPos(int currentPos) {
    	this.currentPos = currentPos;
    }

	public int getTotalRowCount() {
	    return values.size();
    }
	
	private static class MyColumn {
		Translatable title;
		Format format;
	}
}
