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
import java.util.Date;

import nextapp.echo.app.Component;
import nextapp.echo.app.Table;
import nextapp.echo.app.table.TableCellRenderer;

import com.maydesk.base.table.PDTableModel;
import com.maydesk.base.util.PDFormat;
import com.maydesk.base.widgets.PDDateField;
import com.maydesk.base.widgets.PDLabel;

/**
 * @author Alejandro Salas <br>
 *         Created on Jul 5, 2007
 */
public class CalendarRenderer implements TableCellRenderer {

	private boolean editable;
	private String renderId;

	public CalendarRenderer(boolean editable, String renderId) {
		this.editable = editable;
		this.renderId = renderId;
	}

	@Override
	public Component getTableCellRendererComponent(Table table, Object value, final int col, final int row) {

		Date date = ((Calendar) value).getTime();
		if (!editable) {
			return new PDLabel(PDFormat.getDefaultDateTimeFormat().format(date), PDLabel.STYLE.FIELD_LABEL);
		}

		final PDTableModel tableModel = (PDTableModel) table.getModel();
		final PDDateField dateField = new PDDateField(renderId + col + "x" + row);
		dateField.setDate(date);
		// dateField.setDateFormat(PDFormat.getDefaultDateTimeFormat());
		dateField.setEnabled(editable);
		// dateField.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent evt) {
		// dateFieldChanged(tableModel, dateField, col, row, editable);
		// }
		// });

		return dateField;
	}

	private void dateFieldChanged(final PDTableModel tableModel, PDDateField dateField, final int col, final int row, final boolean editable) {
		tableModel.setValueAt(dateField.getDate(), col, row);
	}
}
