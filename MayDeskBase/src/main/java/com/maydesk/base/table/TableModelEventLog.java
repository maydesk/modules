/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.table;

import nextapp.echo.app.table.TableModel;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 8, 2007
 */
public class TableModelEventLog extends nextapp.echo.app.event.TableModelEvent {

	private Object oldValue;
	private Object newValue;

	public TableModelEventLog(TableModel source, int column, int firstRow, int lastRow, int type, Object oldValue, Object newValue) {
		super(source, column, firstRow, lastRow, type);
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public Object getNewValue() {
		return newValue;
	}

	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}

	public Object getOldValue() {
		return oldValue;
	}

	public void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}
}