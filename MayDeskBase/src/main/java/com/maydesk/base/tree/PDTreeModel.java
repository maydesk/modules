package com.maydesk.base.tree;

import echopoint.tree.DefaultTreeModel;
import echopoint.tree.TreeNode;

/**
 * @author Alejandro Salas
 */
public class PDTreeModel extends DefaultTreeModel {

	public PDTreeModel(TreeNode rootNode) {
		super(rootNode);
	}

	@Override
	public Object getValueAt(final Object node, final int column) {
		return node;
	}
}
