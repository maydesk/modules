/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui.user;

import nextapp.echo.app.Extent;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.config.IPlugTarget;
import com.maydesk.base.config.XmlBaseEntry;
import com.maydesk.base.gui.PDMasterDataView;
import com.maydesk.base.internal.PDTitleBar;
import com.maydesk.base.model.MUser;
import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.table.PDPageableFactory;
import com.maydesk.base.util.HeaderValue;

/**
 * @author chrismay
 */
public class FrmUsers extends PDMasterDataView<MUser> implements IPlugTarget {

	public FrmUsers() {
		super(true, PDMasterDataView.DISPLAY_MODE.SIDE_SCROLL, MUser.class);
		setWidth(new Extent(700));
		setHeight(new Extent(450));
		addToolButton("Neuer User", EImage16.add, true, "").addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnNewUserClicked();
			}
		});

		addEditor("User", new PnlUserForWizard());
		addEditor("Image", new PnlUserMoods());
//XXX		addEditor("Roles", new PnlUserRoles(MDUserRoleFactory.class));
	}

	protected void assignBaseModel(Object baseModel) {
		// factoryClass = (Class)baseModel;
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

		@Override
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

			@Override
			public PDTitleBar getHeaderComponent(HeaderValue headerValues) {
				return new MyTitleBar(headerValues);
			}
		};
	}

	@Override
	public void initWire(XmlBaseEntry parentWire) {
	}
}
