/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.table.renderer;

import nextapp.echo.app.Button;
import nextapp.echo.app.Component;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Row;
import nextapp.echo.app.Table;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.table.TableCellRenderer;

import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.table.PDTableModel;

/**
 * @author Alejandro Salas <br>
 *         Created on Jul 5, 2007
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

	@Override
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
			@Override
			public void actionPerformed(ActionEvent evt) {
				editListener.btnEditClicked(new ActionEvent(CommandBarRenderer.this, null));
			}
		});
		add(btnEdit);

		btnUp = new Button(EImage16.upe.getImage());
		btnUp.setDisabledIcon(EImage16.upd.getImage());
		btnUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				editListener.btnUpClicked(new ActionEvent(CommandBarRenderer.this, null));
			}
		});
		btnUp.setEnabled(row != 0);
		add(btnUp);

		btnDown = new Button(EImage16.dwe.getImage());
		btnDown.setDisabledIcon(EImage16.dwd.getImage());
		btnDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				editListener.btnDownClicked(new ActionEvent(CommandBarRenderer.this, null));
			}
		});
		btnDown.setEnabled(row < rowCount - 1);
		add(btnDown);

		btnDelete = new Button(EImage16.deletee.getImage());
		btnDelete.setDisabledIcon(EImage16.deleted.getImage());
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				editListener.btnDeleteClicked(new ActionEvent(CommandBarRenderer.this, null));
			}
		});
		add(btnDelete);

		setInsets(new Insets(5, 0));
	}
}