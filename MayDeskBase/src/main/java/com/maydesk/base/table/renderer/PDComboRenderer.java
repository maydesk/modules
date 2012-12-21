/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.table.renderer;

import java.util.List;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Table;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.table.TableCellRenderer;

import com.maydesk.base.table.PDTableModel;
import com.maydesk.base.widgets.PDCombo;

/**
 * @author Alejandro Salas 
 * Created on Oct 5, 2007
 */
public class PDComboRenderer implements TableCellRenderer {

	private List data;

	public PDComboRenderer(List data) {
		this.data = data;
	}

	@Override
	public Component getTableCellRendererComponent(Table table, Object value, final int col, final int row) {
		final PDTableModel tableModel = (PDTableModel) table.getModel();
		final PDCombo cbo = new PDCombo(data, false);
		cbo.setWidth(new Extent(100, Extent.PERCENT));
		cbo.setSelectedItem(value);
		cbo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.setValueAt(cbo.getSelectedItem(), col, row);
			}
		});
		return cbo;
	}
}