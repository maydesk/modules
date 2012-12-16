/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import java.util.List;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.Insets;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.GridLayoutData;

import org.hibernate.Session;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.dao.DaoUser;
import com.maydesk.base.model.MTask;
import com.maydesk.base.model.MTaskAssign;
import com.maydesk.base.model.MUser;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.sop.gui.StandardTerms;
import com.maydesk.base.util.KIT_CONST;
import com.maydesk.base.widgets.PDCombo;
import com.maydesk.base.widgets.PDGrid;
import com.maydesk.base.widgets.PDLabel;

public class WzdAssignTask extends PDWizard {

	private static enum FILTER {
		//BY_ROLE, 
		BY_PROJECT, 
		ALL;

		public String toString() {
			switch(this) {
			//case BY_ROLE: return "By applicable role in this project";
			case BY_PROJECT: return nls(PDBeanTerms.By_related_project);
			}
			return nls(PDBeanTerms.All_reachable_users);
		}
	}
	
	private MTask task;
	
	public WzdAssignTask(MTask task) {
		setTitle(nls(PDBeanTerms.Assign_task));
		
		this.task = task;
		
		addPanel(new Panel1());
		addPanel(new Panel2());
		addPanel(new Panel3());
		
		showPage();
	}
	
	class Panel1 extends PDWizardPanel {
		
		Panel1() {
			super(PDBeanTerms.Task_Overview, null, StandardTerms.Next);
			initGui();
		}
		
		private void initGui() {
			setInfo(PDBeanTerms.General_info_about_this_task);
			
			PDGrid grid = new PDGrid(2);
			grid.setInsets(new Insets(3));
			add(grid);
			
			grid.addLabel(PDBeanTerms.Task);
			
			PDLabel lbl = new PDLabel(PDLabel.STYLE.FIELD_VALUE);
			//lbl.setWidth(new Extent(400));
			lbl.setText(task.toString());
			grid.add(lbl);			
		}
	}
	
	
	class Panel2 extends PDWizardPanel {
		
		private PDCombo<FILTER> cboFilter;
		private Column colUsers;
		
		Panel2() {
			super(PDBeanTerms.Assign_to_user, StandardTerms.Back, PDBeanTerms.Assign£);
			initGui();
		}
		
		private void initGui() {
			setInfo(nls(PDBeanTerms.Assign_this_task_to_a_user));
			
			PDGrid grid = new PDGrid(2);
			add(grid);
			
			grid.addLabel(StandardTerms.Filter, KIT_CONST.Colon);
	
			cboFilter = new PDCombo<FILTER>(FILTER.values(), false);
			cboFilter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fillUserList();
				}
			});
			grid.add(cboFilter);
			
			PDLabel lbl = new PDLabel(StandardTerms.Users, KIT_CONST.Colon);
			GridLayoutData gld = new GridLayoutData();
			gld.setAlignment(Alignment.ALIGN_TOP);
			lbl.setLayoutData(gld);
			grid.add(lbl);
			
			colUsers = new Column();
			grid.add(colUsers);			
		}
		
		private void fillUserList() {
			colUsers.removeAll();
			List<MUser> users = null;
			if (FILTER.ALL == cboFilter.getSelectedItem()) {
				//users = DaoUser.findReachableUsersList(PDUserSession.getInstance().getUser(), null, task);
			} else {
				//users = DaoUser.findReachableUsersList(PDUserSession.getInstance().getUser(), task.getTenant(), task);				
			}
			
			for (MUser user : users) {
				PDCheckBox chk = new PDCheckBox(user.getJabberId());
				//chk.setAttribute("user", user);
				colUsers.add(chk);
			}
			
			if (colUsers.getComponentCount() == 0) {
				colUsers.add(new PDLabel(PDBeanTerms.No_users_available));
			}
		}

		@Override
		public void readFromModel() {
			fillUserList();
		}

		@Override
		public void applyToModel2() {
			Session session = PDHibernateFactory.getSession();
			for (Component c : colUsers.getComponents()) {
				PDCheckBox chk = (PDCheckBox)c;
				if (!chk.isSelected()) continue;
				//MUser user = (MUser)chk.getAttribute("user");
				MTaskAssign ut = new MTaskAssign();
				//ut.setUserRef(user);
				ut.setTask(task);
				session.save(ut);
			}
			PDDesktop.getInstance().refreshTaskDisplay(true);
		}
	}
	
	class Panel3 extends PDWizardPanel {		
		Panel3() {
			super(PDBeanTerms.Users_succesfully_assigned, null, StandardTerms.Close);
			setInfo(PDBeanTerms.Users_have_been_succesfully_assigned);
		}		
	}
}
