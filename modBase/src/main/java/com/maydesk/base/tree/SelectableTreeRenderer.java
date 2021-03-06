/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.tree;

import nextapp.echo.app.Component;
import nextapp.echo.app.Label;
import nextapp.echo.extras.app.Tree;
import nextapp.echo.extras.app.tree.TreeCellRenderer;
import nextapp.echo.extras.app.tree.TreePath;

/**
 * @author Alejandro Salas
 */
public class SelectableTreeRenderer implements TreeCellRenderer {
	@Override
	public Component getTreeCellRendererComponent(Tree tree, TreePath path, Object value, int row, int col, boolean selected) {
		if (value instanceof SelectableTreeNode) {
			SelectableTreeNode node = (SelectableTreeNode) value;
			return node.getComponent();
		} else {
			return new Label(value + "");
		}
	}
}
