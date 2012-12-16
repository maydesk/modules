/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table.renderer;

import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.table.PDTableModel;

import nextapp.echo.app.Button;
import nextapp.echo.app.Component;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Row;
import nextapp.echo.app.Table;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.table.TableCellRenderer;

/** 
 * @author Alejandro Salas 
 * Created on Jul 5, 2007
 */
public class CommandBarRenderer extends Row implements TableCellRenderer {

	private Button btnDelete;
	private Button btnDown;
	private Button btnEdit;
	private Button btnUp;
	private IRowEditListener editListener;
	private int row;

	public CommandBarRenderer(IRowEditListener editListener) {
		this.editListener = editListener;
	}

	public CommandBarRenderer(IRowEditListener editListener, int row, int rowCount) {
		this.editListener = editListener;
		this.row = row;
		initGUI(rowCount);
	}

	public Button getBtnDelete() {
		return btnDelete;
	}

	public Button getBtnDown() {
		return btnDown;
	}

	public Button getBtnEdit() {
		return btnEdit;
	}

	public Button getBtnUp() {
		return btnUp;
	}

	public int getRow() {
		return row;
	}

	public Component getTableCellRendererComponent(Table table, Object value, final int col, final int row) {
		final PDTableModel tableModel = (PDTableModel) table.getModel();
		CommandBarRenderer ret = new CommandBarRenderer(editListener, row, tableModel.getRowCount());
		init(tableModel, ret, col, row);
		return ret;
	}

	protected void init(PDTableModel tableModel, CommandBarRenderer cbr, int col, int row) {
		// To be overridden
	}

	private void initGUI(int rowCount) {
		btnEdit = new Button(EImage16.edite.getImage());
		btnEdit.setDisabledIcon(EImage16.editd.getImage());
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				editListener.btnEditClicked(new ActionEvent(CommandBarRenderer.this, null));
			}
		});
		add(btnEdit);

		btnUp = new Button(EImage16.upe.getImage());
		btnUp.setDisabledIcon(EImage16.upd.getImage());
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				editListener.btnUpClicked(new ActionEvent(CommandBarRenderer.this, null));
			}
		});
		btnUp.setEnabled(row != 0);
		add(btnUp);

		btnDown = new Button(EImage16.dwe.getImage());
		btnDown.setDisabledIcon(EImage16.dwd.getImage());
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				editListener.btnDownClicked(new ActionEvent(CommandBarRenderer.this, null));
			}
		});
		btnDown.setEnabled(row < rowCount - 1);
		add(btnDown);

		btnDelete = new Button(EImage16.deletee.getImage());
		btnDelete.setDisabledIcon(EImage16.deleted.getImage());
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				editListener.btnDeleteClicked(new ActionEvent(CommandBarRenderer.this, null));
			}
		});
		add(btnDelete);

		setInsets(new Insets(5, 0));
	}	
}