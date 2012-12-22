/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.table.renderer;

import nextapp.echo.app.Component;
import nextapp.echo.app.Label;
import nextapp.echo.app.Table;
import nextapp.echo.app.table.TableCellRenderer;

import com.maydesk.base.util.PDFormat;

/**
 * @author Alejandro Salas <br>
 *         Created on Jul 5, 2007
 */
public class CellLabelRenderer implements TableCellRenderer {

	public CellLabelRenderer() {
		// Empty
	}

	@Override
	public Component getTableCellRendererComponent(final Table table, Object value, final int col, final int row) {
		if (value == null) {
			value = " ";
		}

		Label lblView = new Label(PDFormat.format(value));
		lblView.setLineWrap(true);
		// lblView.setHeight(new Extent(20)); // DMI: Don't like, but echo compress labels if text is ""
		return lblView;
	}
}
