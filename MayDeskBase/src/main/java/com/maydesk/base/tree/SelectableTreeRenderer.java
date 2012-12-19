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
