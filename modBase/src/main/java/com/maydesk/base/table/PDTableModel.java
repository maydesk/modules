/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.table;

import nextapp.echo.app.event.TableModelListener;
import nextapp.echo.app.table.DefaultTableModel;

/**
 * @author Alejandro Salas
 */
public abstract class PDTableModel extends DefaultTableModel {

	protected TableModelListener cellChangeListener;

	@Override
	public abstract void setValueAt(Object value, int column, int row);

	/**
	 * Set the listener which fires an event when the content of a cell changes
	 * This is meant for updating the model, and save the change to the
	 * database. This is in order to avoid that fireTableDataChanged() is called
	 * which would re-draw the whole table, and mess up with the focus tab order
	 */
	public void setCellChangeListener(TableModelListener cellChangeListener) {
		this.cellChangeListener = cellChangeListener;
	}

	public int getTotalRowCount() {
		return getRowCount();
	}
}
