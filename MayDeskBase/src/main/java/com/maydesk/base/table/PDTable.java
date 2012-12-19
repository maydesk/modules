/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Table;
import nextapp.echo.app.table.DefaultTableModel;
import nextapp.echo.app.table.TableCellRenderer;
import nextapp.echo.app.table.TableColumn;
import nextapp.echo.app.table.TableColumnModel;
import nextapp.echo.app.table.TableModel;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.table.renderer.PDCellRenderer;
import com.maydesk.base.widgets.PDLabel;

/**
 * @author Demian Gutierrez
 */
public class PDTable extends Table {

	public PDTable() {
		init2();
	}

	public PDTable(TableModel tableModel, TableColumnModel tableColumnModel) {
		super(tableModel, tableColumnModel);
		init2();
	}

	public PDTable(TableModel model) {
		super(model);
		init2();
	}

	protected void init2() {
		setDefaultHeaderRenderer(new MyHeaderRenderer());
		setInsets(new Insets(5, 0, 0, 0));
		setDefaultRenderer(Object.class, new PDCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(Table table, Object value, int column, int row) {
				PDLabel lbl = new PDLabel(PDLabel.STYLE.FIELD_LABEL);
				if (value instanceof ImageReference) {
					lbl.setIcon((ImageReference) value);
				} else {
					if (value == null)
						value = "";
					lbl.setText(value + "");
				}
				setBackground(lbl, row);
				return lbl;
			}
		});
	}

	class MyHeaderRenderer implements TableCellRenderer {
		@Override
		public Component getTableCellRendererComponent(Table table, Object value, int column, int row) {
			return new PDLabel(value + "", PDLabel.STYLE.FIELD_LABEL);
		}
	}

	public void addColumn(Translatable title) {
		addColumn(title, -1);
	}

	public void addColumn(Translatable title, int width) {
		int colCount = getModel().getColumnCount();
		TableColumn col = new TableColumn(colCount);
		col.setHeaderValue(title.textEN());
		if (width >= 0) {
			col.setWidth(new Extent(width, Extent.PERCENT));
		}
		getColumnModel().addColumn(col);
		if (getModel() instanceof PDTableModel3) {
			((PDTableModel3) getModel()).addColumn(title);
		}
	}

	public void addColumn(String title, int width) {
		int colCount = getModel().getColumnCount();
		TableColumn col = new TableColumn(colCount); // , new Extent(width));
		col.setHeaderValue(title);
		col.setWidth(new Extent(width, Extent.PERCENT));
		getColumnModel().addColumn(col);
		if (getModel() instanceof PDTableModel2) {
			((PDTableModel2) getModel()).addColumn(title);
		} else if (getModel() instanceof DefaultTableModel) {
			((DefaultTableModel) getModel()).setColumnCount(colCount + 1);
			((DefaultTableModel) getModel()).setColumnName(colCount, title);
		}
	}
}
