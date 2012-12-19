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
