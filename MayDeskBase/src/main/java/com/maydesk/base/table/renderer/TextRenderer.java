/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table.renderer;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Table;
import nextapp.echo.app.TextField;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.table.TableCellRenderer;

import com.maydesk.base.table.PDTableModel;
import com.maydesk.base.widgets.PDLabel;
import com.maydesk.base.widgets.PDNumericField;
import com.maydesk.base.widgets.PDTextField;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 5, 2007
 */
public class TextRenderer implements TableCellRenderer {

	private boolean editable;
	private boolean isNumber;
	private boolean isInteger;
	private int maxLength;
	private boolean bold;
	private Extent width;
	private Class<?> usedByClass;

	public TextRenderer(boolean editable, boolean isNumber, Class<?> usedByClass) {
		this(new Extent(100, Extent.PERCENT), editable, isNumber, 50, usedByClass);
	}

	public TextRenderer(int width, boolean editable, boolean isNumber, Class<?> usedByClass) {
		this(new Extent(width), editable, isNumber, 50, usedByClass);
	}

	public TextRenderer(boolean editable, boolean isNumber, boolean bold, Class<?> usedByClass) {
		this(new Extent(100, Extent.PERCENT), editable, isNumber, 50, usedByClass);
		this.bold = bold;
	}

	public TextRenderer(boolean editable, boolean isNumber, int maxLength, Class<?> usedByClass) {
		this(new Extent(100, Extent.PERCENT), editable, isNumber, maxLength, usedByClass);
	}

	public TextRenderer(Extent width, boolean editable, boolean isNumber, int maxLength, Class<?> usedByClass) {
		this.editable = editable;
		this.isNumber = isNumber;
		this.maxLength = maxLength;
		this.width = width;
		this.usedByClass = usedByClass;
	}

	@Override
	public Component getTableCellRendererComponent(Table table, Object value, final int col, final int row) {
		final PDTableModel tableModel = (PDTableModel) table.getModel();

		String strValue = (value == null ? "" : value.toString());

		if (editable) {
			final PDTextField txt;
			if (isNumber) {
				if (isInteger) {
					txt = new PDNumericField(0);
				} else {
					txt = new PDNumericField();
				}
				((PDNumericField) txt).setNumber(((Number) value).doubleValue());
			} else {
				txt = new PDTextField();
				txt.setText(strValue);
			}
			txt.setRenderId(usedByClass.getSimpleName() + col + "x" + row);
			txt.setWidth(width);
			txt.setMaximumLength(maxLength);
			txt.setKeyAction(true);
			txt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					txtChanged(txt, col, row, tableModel);
				}
			});
			return txt;
		}

		if (strValue.length() == 0) {
			strValue = "..."; // (otherwise it will not be shown properly in PnlHistory)
		}

		PDLabel ret = new PDLabel(strValue, bold ? PDLabel.STYLE.HEADER_3 : PDLabel.STYLE.FIELD_LABEL);
		if (isNumber) {
//			ret.setTextAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		}
		return ret;
	}

	protected boolean isValid(String value, int col, int row) {
		// Override if needed. Return true to keep the value, false to drop it
		return true;
	}

	public boolean isInteger() {
		return isInteger;
	}

	public void setInteger(boolean isInteger) {
		this.isInteger = isInteger;
	}

	private void txtChanged(TextField txt, int col, int row, PDTableModel tableModel) {
		if (isValid(txt.getText(), col, row)) {
			Object value = isNumber ? ((PDNumericField) txt).getNumber() : txt.getText();
			tableModel.setValueAt(value, col, row);
		} else {
			Object value = tableModel.getValueAt(col, row);
			if (value instanceof Number) {
				((PDNumericField) txt).setNumber(((Number) value).doubleValue());
			} else {
				txt.setText(value.toString());
			}
		}
	}
}