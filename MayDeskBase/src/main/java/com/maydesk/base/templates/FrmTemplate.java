/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.templates;

import nextapp.echo.app.Extent;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.gui.PDMasterDataView;
import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MUser;
import com.maydesk.base.table.PDPageableFactory;

/**
 * @author chrismay
 */
public class FrmTemplate extends PDMasterDataView {

	public FrmTemplate() {
		super(true, PDMasterDataView.DISPLAY_MODE.SIDE_SCROLL, MUser.class);
	}

	@Override
	protected void initGUI2() {
		// setWidth(new Extent(660));
		// setHeight(new Extent(500));
		// setResizable(false);
		splitListAndDetails.setSeparatorPosition(new Extent(200));

		// addToolButton("Test Button", "tango16/actions/list-add.png", "btnTest").addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent arg0) {
		// btnNewClicked();
		// }
		// });
	}

	private void btnNewClicked() {
		// MUser user = new MUser();
		// HibernateFactory.getSession().save(user);
		// addNewItem(user);
		// readFromModel();
	}

	@Override
	protected PDPageableFactory getFactory(PDMasterDataView masterDataView) {
		return new PDPageableFactory(masterDataView, MUser.class) {

			@Override
			public Criteria getCriteria(Session session) {
				return PDHibernateFactory.getSession().createCriteria(MBase.class);
			}
		};
	}
}
