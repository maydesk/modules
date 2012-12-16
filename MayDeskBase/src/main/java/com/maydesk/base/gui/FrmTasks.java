/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import java.util.List;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.dao.DaoTask;
import com.maydesk.base.dao.DaoUser;
import com.maydesk.base.model.MTask;
import com.maydesk.base.model.MTaskAssign;
import com.maydesk.base.model.MUser;
import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.sop.gui.StandardTerms;
import com.maydesk.base.tree.PDTreeModel;
import com.maydesk.base.widgets.PDButtonGroup;
import com.maydesk.base.widgets.PDCombo;
import com.maydesk.base.widgets.PDRadioButton;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.WindowPaneEvent;
import nextapp.echo.app.event.WindowPaneListener;
import nextapp.echo.extras.app.Tree;
import nextapp.echo.extras.app.tree.TreeCellRenderer;
import nextapp.echo.extras.app.tree.TreePath;
import echopoint.tree.DefaultMutableTreeNode;

public class FrmTasks extends PDSimpleDialog {

	private static enum OPTIONS {
		OPTION_TASKS, OPTION_USERS, OPTION_ISSUES;
	}
	public static enum OPEN_MODE {
		GENERIC, UNASSIGNED, PENDING;
	}
	public static enum TASK_FILTER {
		OPEN, UNASSIGNED, ASSIGNED, DONE, ALL;
	}	
	
	private DefaultMutableTreeNode rootNode;
	private PDButtonGroup<OPTIONS> buttonGroup;
	private Tree tree;
	private PDCombo<TASK_FILTER> cboTaskFilter;
	
	public FrmTasks(OPEN_MODE openMode) {
		super(nls(PDBeanTerms.Task_Managment), 500, 400);
		initGui(openMode);
	}
	
	protected Component getMainContainer() {
		SplitPane split = new SplitPane(SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM);
		split.setAutoPositioned(true);
		return split;
	}

	private void initGui(OPEN_MODE openMode) {
		Row headerRow = new Row();
		addMainComponent(headerRow);
		
		buttonGroup = new PDButtonGroup<OPTIONS>();
		PDRadioButton<OPTIONS> rdoUsers = buttonGroup.createButton(OPTIONS.OPTION_USERS, nls(StandardTerms.Users));
		customizeRadioButton(rdoUsers, EImage16.person);
		headerRow.add(rdoUsers);
		
		PDRadioButton<OPTIONS> rdoTasks = buttonGroup.createButton(OPTIONS.OPTION_TASKS, nls(PDBeanTerms.Tasks));
		customizeRadioButton(rdoTasks, EImage16.bulletlist);
		headerRow.add(rdoTasks);
		
		rootNode = new DefaultMutableTreeNode();
		PDTreeModel model = new PDTreeModel(rootNode);
		tree = new Tree(model);
		tree.setCellRenderer(new MyTreeRendered());
		addMainComponent(tree);
		
		cboTaskFilter = new PDCombo<TASK_FILTER>(TASK_FILTER.values(), false);
		cboTaskFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillFields();
			}			
		});
		getToolbar().add(cboTaskFilter);
		
		buttonGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cboTaskFilter.setVisible(buttonGroup.getSelectedValue() == OPTIONS.OPTION_TASKS);
				fillFields();
			}			
		});
		
		if (openMode == OPEN_MODE.UNASSIGNED) {
			buttonGroup.setSelectedValue(OPTIONS.OPTION_TASKS);
			cboTaskFilter.setSelectedItem(TASK_FILTER.UNASSIGNED);
		} else {
			buttonGroup.setSelectedValue(OPTIONS.OPTION_USERS);
			cboTaskFilter.setVisible(false);
		}
		fillFields();
	}

	private void customizeRadioButton(PDRadioButton rdo, EImage16 img) {
		rdo.setStateIcon(img.getImage());
		rdo.setSelectedStateIcon(img.getImage());
		rdo.setStatePosition(Alignment.ALIGN_TOP);
		rdo.setWidth(new Extent(32));
		rdo.setHeight(new Extent(32));
		rdo.setInsets(new Insets(0, 3, 0, 0));
		rdo.setStateMargin(new Extent(1));
		rdo.setStateAlignment(Alignment.ALIGN_CENTER);
		rdo.setAlignment(Alignment.ALIGN_CENTER);
		rdo.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(9)));
		rdo.setForeground(Color.DARKGRAY);
	}
	
	private void fillFields() {
		rootNode = new DefaultMutableTreeNode();
		PDTreeModel model = new PDTreeModel(rootNode);
		tree.setModel(model);
		int i = 0;
		switch (buttonGroup.getSelectedValue()) {
		case OPTION_USERS:
			List<MUser> users = null; //DaoUser.findReachableUsersList(PDUserSession.getInstance().getUser(), null, null);
			for (MUser user : users) {
				DefaultMutableTreeNode<MUser> nodeUser = new DefaultMutableTreeNode<MUser>(user);
				rootNode.add(nodeUser);
				List<MTaskAssign> tasks = DaoTask.findTasksByUser(user);
				for (MTaskAssign userTask : tasks) {
					DefaultMutableTreeNode<MTaskAssign> nodeTask = new DefaultMutableTreeNode<MTaskAssign>(userTask);
					nodeUser.add(nodeTask);
				}
				if (i++ > 20) break;
			}
			break;
		case OPTION_TASKS:
			List<MTask> tasks = DaoTask.findTasks(cboTaskFilter.getSelectedItem());
			for (MTask task : tasks) {
				DefaultMutableTreeNode<MTask> nodeTask = new DefaultMutableTreeNode<MTask>(task);
				rootNode.add(nodeTask);
				List<MTaskAssign> userTasks = DaoTask.findUserTasks(task);
				for (MTaskAssign userTask : userTasks) {
					DefaultMutableTreeNode<MTaskAssign> nodeUserTask = new DefaultMutableTreeNode<MTaskAssign>(userTask);
					nodeTask.add(nodeUserTask);
				}
				if (i++ > 20) break;
			}
			break;
		case OPTION_ISSUES:
//			criteria = session.createCriteria(MIssue.class);
//			List<MIssue> issues = criteria.list();
//			for (MIssue issue : issues) {
//				MyNode node = new MyNode(issue);
//				tasks = DaoTask.findTasksByIssue(issue);
//				for (MTask task : tasks) {
//					MyNode n2 = new MyNode(task);
//					node.addChild(n2);
//				}
//				nodes.add(node);
//			}
			break;			
		}
	}
	
	
	private class MyTreeRendered implements TreeCellRenderer {
		public Component getTreeCellRendererComponent(Tree arg0, TreePath arg1, Object value, int arg3, int arg4, boolean arg5) {
			if (!(value instanceof DefaultMutableTreeNode)) {
				return new Label(value + ""); //$NON-NLS-1$
			}
			DefaultMutableTreeNode node = (DefaultMutableTreeNode)value; 
			if (node.getUserObject() instanceof MTaskAssign) {
				final MTaskAssign userTask = (MTaskAssign)node.getUserObject();
				Row row = new Row();
				Label lbl = new Label();
				if (buttonGroup.getSelectedValue() == OPTIONS.OPTION_USERS) {
					lbl.setText(userTask.getTask().toString());
					lbl.setIcon(EImage16.circle2.getImage());
				} else {
					lbl.setText(userTask.getUserRef().getJabberId());
					lbl.setIcon(EImage16.person.getImage());
				}
				row.add(lbl);
				Button btnRemove = new Button(EImage16.deletee.getImage());
				btnRemove.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PDHibernateFactory.getSession().delete(userTask);
						PDHibernateFactory.getSession().flush();
						fillFields();
						PDDesktop.getInstance().refreshTaskDisplay(true);
					}					
				});
				row.add(btnRemove);
				return row;
			} else if (node.getUserObject() instanceof MTask) {
				Row row = new Row();
				final MTask task = (MTask)node.getUserObject();
				Label lbl = new Label(EImage16.add.getImage());
				lbl.setText(task.toString());
				row.add(lbl);
				Button btn = new Button(EImage16.compare4.getImage());
				btn.setToolTipText(nls(PDBeanTerms.Assign_task));
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						WzdAssignTask wzd = new WzdAssignTask(task);						
						PDDesktop.getInstance().addWindow(wzd);
						wzd.addWindowPaneListener(new WindowPaneListener() {
							public void windowPaneClosing(WindowPaneEvent e) {
								fillFields();								
							}							
						});
					}					
				});
				row.add(btn);
				return row;
			}
			return new Label(value.toString());
		}

		public Label getTreeCellRendererText(Tree tree, Object value, boolean selected, boolean expanded, boolean leaf) {
			if (value instanceof String) return new Label(value + ""); //$NON-NLS-1$
			return new Label("---"); //$NON-NLS-1$
		}		
	}
}
