/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.tree;

import java.util.Enumeration;

import nextapp.echo.app.Component;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import echopoint.tree.DefaultMutableTreeNode;

/**
 * @author Alejandro Salas
 */
public class SelectableTreeNode<T> extends DefaultMutableTreeNode<T> {

	private SelectableTreeNodeComponent component;

	public SelectableTreeNode(T base) {
		super(base);
		allowsChildren = true;
		component = new SelectableTreeNodeComponent(this, base.toString());
		component.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectionToggled(SelectableTreeNode.this);
			}
		});
	}

	private void selectionToggled(SelectableTreeNode node) {
		Enumeration enu = node.children();
		while (enu.hasMoreElements()) {
			SelectableTreeNode childNode = (SelectableTreeNode) enu.nextElement();
			childNode.setSelected(node.isSelected());
			selectionToggled(childNode);
		}
	}

	public boolean isSelected() {
		return component.isSelected();
	}

	public void setSelected(boolean selected) {
		component.setSelected(selected);
	}

	public void setEnabled(boolean enabled) {
		component.setEnabled(enabled);
	}

	public Component getComponent() {
		return component;
	}
}
