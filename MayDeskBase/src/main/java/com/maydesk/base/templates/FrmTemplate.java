/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

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
