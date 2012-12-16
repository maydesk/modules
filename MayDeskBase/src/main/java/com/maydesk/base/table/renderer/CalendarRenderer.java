/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table.renderer;

import java.util.Calendar;
import java.util.Date;

import com.maydesk.base.table.PDTableModel;
import com.maydesk.base.util.PDFormat;
import com.maydesk.base.widgets.PDDateField;
import com.maydesk.base.widgets.PDLabel;

import nextapp.echo.app.Component;
import nextapp.echo.app.Table;
import nextapp.echo.app.table.TableCellRenderer;

/** 
 * @author Alejandro Salas 
 * Created on Jul 5, 2007
 */
public class CalendarRenderer implements TableCellRenderer {

	private boolean editable;
	private String renderId;

	public CalendarRenderer(boolean editable, String renderId) {
		this.editable = editable;
		this.renderId = renderId;
	}

	public Component getTableCellRendererComponent(Table table, Object value, final int col, final int row) {

		Date date = ((Calendar) value).getTime();
		if (!editable) {
			return new PDLabel(PDFormat.getDefaultDateTimeFormat().format(date), PDLabel.STYLE.FIELD_LABEL);
		}

		final PDTableModel tableModel = (PDTableModel) table.getModel();
		final PDDateField dateField = new PDDateField(renderId + col + "x" + row);
		dateField.setDate(date);
		//dateField.setDateFormat(PDFormat.getDefaultDateTimeFormat());
		dateField.setEnabled(editable);
//		dateField.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent evt) {
//				dateFieldChanged(tableModel, dateField, col, row, editable);
//			}
//		});
		
		
		return dateField;
	}

	private void dateFieldChanged(final PDTableModel tableModel, PDDateField dateField, final int col, final int row,
			final boolean editable) {
		tableModel.setValueAt(dateField.getDate(), col, row);
	}
}
