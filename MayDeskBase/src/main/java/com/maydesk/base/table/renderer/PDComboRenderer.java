/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table.renderer;

import java.util.List;

import com.maydesk.base.table.PDTableModel;
import com.maydesk.base.widgets.PDCombo;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Table;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.table.TableCellRenderer;

/**
 * Created on Oct 5, 2007
 */
public class PDComboRenderer implements TableCellRenderer {

	private List data;
		
	public PDComboRenderer(List data) {
		this.data = data;
	}

	public Component getTableCellRendererComponent(Table table, Object value, final int col, final int row) {
		final PDTableModel tableModel = (PDTableModel) table.getModel();
		final PDCombo cbo = new PDCombo(data, false);
		cbo.setWidth(new Extent(100, Extent.PERCENT));
		cbo.setSelectedItem(value);
		cbo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableModel.setValueAt(cbo.getSelectedItem(), col, row);				
			}			
		});
		return cbo;
	}
}