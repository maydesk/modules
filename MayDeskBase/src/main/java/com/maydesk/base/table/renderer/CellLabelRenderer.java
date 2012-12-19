/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table.renderer;

import nextapp.echo.app.Component;
import nextapp.echo.app.Label;
import nextapp.echo.app.Table;
import nextapp.echo.app.table.TableCellRenderer;

import com.maydesk.base.util.PDFormat;

/**
 * @author Alejandro Salas <br>
 *         Created on Jul 5, 2007
 */
public class CellLabelRenderer implements TableCellRenderer {

	public CellLabelRenderer() {
		// Empty
	}

	@Override
	public Component getTableCellRendererComponent(final Table table, Object value, final int col, final int row) {
		if (value == null) {
			value = " ";
		}

		Label lblView = new Label(PDFormat.format(value));
		lblView.setLineWrap(true);
		// lblView.setHeight(new Extent(20)); // DMI: Don't like, but echo compress labels if text is ""
		return lblView;
	}
}
