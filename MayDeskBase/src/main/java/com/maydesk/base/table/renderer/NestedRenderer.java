/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table.renderer;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Row;
import nextapp.echo.app.Table;
import nextapp.echo.app.table.TableCellRenderer;

public class NestedRenderer implements TableCellRenderer {

	protected List<TableCellRenderer> tableCellRendererList = new ArrayList<TableCellRenderer>();

	public NestedRenderer() {
		// Empty
	}

	public Component getTableCellRendererComponent(final Table table, final Object object, final int col, final int row) {
		Row ret = new Row();
		ret.setCellSpacing(new Extent(5));

		for (TableCellRenderer tableCellRenderer : tableCellRendererList) {
			Component component = tableCellRenderer.getTableCellRendererComponent(table, object, col, row);

			if (component != null) {
				ret.add(component);
			}
		}

		return ret;
	}

	public void setTableCellRendererList(List<TableCellRenderer> tableCellRendererList) {
		this.tableCellRendererList = tableCellRendererList;
	}

	public List<TableCellRenderer> getTableCellRendererList() {
		return tableCellRendererList;
	}
}
