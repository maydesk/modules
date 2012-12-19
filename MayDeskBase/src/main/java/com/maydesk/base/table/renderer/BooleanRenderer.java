/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table.renderer;

import nextapp.echo.app.CheckBox;
import nextapp.echo.app.Component;
import nextapp.echo.app.Table;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.table.PDTableModel3;

/**
 * @author Alejandro Salas <br>
 *         Created on Jul 5, 2007
 */
public class BooleanRenderer extends PDCellRenderer {

	public boolean editable;
	protected boolean isHeader;

	public BooleanRenderer(boolean editable) {
		this.editable = editable;
	}

	public BooleanRenderer(boolean editable, boolean isHeader) {
		this.editable = editable;
		this.isHeader = isHeader;
	}

	@Override
	public Component getTableCellRendererComponent(final Table table, Object value, final int col, final int row) {
		final PDTableModel3 model = (PDTableModel3) table.getModel();

		final CheckBox checkBox = new CheckBox();
		checkBox.setVisible(isEnable(row));
		checkBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				setSelected(table, model, checkBox.isSelected(), col, row);
			}
		});
		if (value instanceof Boolean) {
			checkBox.setSelected((Boolean) value);
		}
		setBackground(checkBox, row);
		return checkBox;
	}

	// Override in children to have custom behaving
	protected boolean isEnable(int value) {
		return true;
	}

	protected void setSelected(Table table, PDTableModel3 model, boolean selected, int col, int row) {
		if (isHeader) {
			table.getColumnModel().getColumn(col).setHeaderValue(selected);
			model.setSelected(selected);
			model.fireTableDataChanged();
		} else {
			model.setSelected(selected, row);
		}
	}
}