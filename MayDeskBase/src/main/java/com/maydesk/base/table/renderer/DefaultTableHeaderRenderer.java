/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table.renderer;

import com.maydesk.base.widgets.PDLabel;

import nextapp.echo.app.Component;
import nextapp.echo.app.Table;
import nextapp.echo.app.table.TableCellRenderer;

public class DefaultTableHeaderRenderer implements TableCellRenderer {

	public Component getTableCellRendererComponent(final Table table, final Object value, final int col, final int row) {
		return new PDLabel(value.toString(), PDLabel.STYLE.HEADER_3);
	}
}
