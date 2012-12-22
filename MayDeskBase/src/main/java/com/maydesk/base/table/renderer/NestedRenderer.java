/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.table.renderer;

import java.util.ArrayList;
import java.util.List;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Row;
import nextapp.echo.app.Table;
import nextapp.echo.app.table.TableCellRenderer;

/**
 * @author Alejandro Salas
 */
public class NestedRenderer implements TableCellRenderer {

	protected List<TableCellRenderer> tableCellRendererList = new ArrayList<TableCellRenderer>();

	public NestedRenderer() {
		// Empty
	}

	@Override
	public Component getTableCellRendererComponent(final Table table, final Object object, final int col, final int row) {
		Row ret = new Row();
		ret.setCellSpacing(new Extent(5));

		for (TableCellRenderer tableCellRenderer : tableCellRendererList) {
			Component component = tableCellRenderer.getTableCellRendererComponent(table, object, col, row);

			if (component != null) {
				ret.add(component);
			}
		}

		return ret;
	}

	public void setTableCellRendererList(List<TableCellRenderer> tableCellRendererList) {
		this.tableCellRendererList = tableCellRendererList;
	}

	public List<TableCellRenderer> getTableCellRendererList() {
		return tableCellRendererList;
	}
}
