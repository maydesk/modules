/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.table.renderer;

import java.util.Calendar;

import nextapp.echo.app.Component;
import nextapp.echo.app.Table;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.table.TableCellRenderer;

import com.maydesk.base.table.PDTableModel;
import com.maydesk.base.widgets.PDTimeSpinner;

/**
 * @author Alejandro Salas
 */
public class TimeRenderer implements TableCellRenderer {

	private boolean editable;

	public TimeRenderer(boolean editable) {
		this.editable = editable;
	}

	@Override
	public Component getTableCellRendererComponent(Table table, Object value, final int col, final int row) {
		final PDTableModel tableModel = (PDTableModel) table.getModel();

		final PDTimeSpinner editor = new PDTimeSpinner(editable);
		editor.setModel((Calendar) value);
		editor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				tableModel.setValueAt(editor.getModel(), col, row);
			}
		});
		return editor;
	}
}
