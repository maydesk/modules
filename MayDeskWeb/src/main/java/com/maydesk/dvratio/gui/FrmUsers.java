/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.dvratio.gui;

import nextapp.echo.app.Extent;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.gui.PDMasterDataView;
import com.maydesk.base.gui.user.PnlUserForWizard;
import com.maydesk.base.gui.user.PnlUserMoods;
import com.maydesk.base.gui.user.PnlUserRoles;
import com.maydesk.base.internal.PDTitleBar;
import com.maydesk.base.model.MUser;
import com.maydesk.base.model.MWire;
import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.table.PDPageableFactory;
import com.maydesk.base.util.HeaderValue;
import com.maydesk.base.util.IPlugTarget;
import com.maydesk.web.MDUserRoleFactory;

public class FrmUsers extends PDMasterDataView<MUser> implements IPlugTarget {

	public FrmUsers() {
		super(true, PDMasterDataView.DISPLAY_MODE.SIDE_SCROLL, MUser.class);
		setWidth(new Extent(700));
		setHeight(new Extent(450));
		addToolButton("Neuer User", EImage16.add, true, "").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewUserClicked();				
        	}			
		});
		
		addEditor("User", new PnlUserForWizard());
		addEditor("Image", new PnlUserMoods());
		addEditor("Roles", new PnlUserRoles(MDUserRoleFactory.class));
	}

	protected void assignBaseModel(Object baseModel) {		
		//factoryClass = (Class)baseModel;
	}
	
	private void btnNewUserClicked() {
		MUser user = new MUser();
		PDHibernateFactory.getSession().save(user);
		addNewItem(user);
		readFromModel();
	}
	
	protected class MyTitleBar extends PDTitleBar {
		public MyTitleBar(HeaderValue headerValues) {
			super(FrmUsers.this, headerValues, true);
		}
		protected void addCommands() {
		}
	}

	@Override
    protected PDPageableFactory getFactory(final PDMasterDataView masterDataView) {
		return new PDPageableFactory(masterDataView, MUser.class) {

			@Override
			public Criteria getCriteria(Session session) {
				return session.createCriteria(MUser.class);
			}

			public PDTitleBar getHeaderComponent(HeaderValue headerValues) {
				 return new MyTitleBar(headerValues);
			}
		};
    }

	@Override
    public void initWire(MWire parentWire) {
    }
}
