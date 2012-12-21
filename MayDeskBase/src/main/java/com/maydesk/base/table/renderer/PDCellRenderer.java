/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.table.renderer;

import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.layout.GridLayoutData;
import nextapp.echo.app.table.TableCellRenderer;

/**
 * @author Alejandro Salas
 */
public abstract class PDCellRenderer implements TableCellRenderer {

	protected void setBackground(Component c, int row) {
		if ((row % 2) == 0) {
			GridLayoutData gld = new GridLayoutData();
			gld.setBackground(new Color(232, 232, 232));
			c.setLayoutData(gld);
		}
	}
}