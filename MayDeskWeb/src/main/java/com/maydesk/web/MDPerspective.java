/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.web;

import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.extras.app.menu.DefaultMenuModel;
import nextapp.echo.extras.app.menu.DefaultOptionModel;
import nextapp.echo.extras.app.menu.MenuModel;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.PDMenuProvider;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.gui.DlgMailSettings;
import com.maydesk.base.gui.DlgPassword;
import com.maydesk.base.gui.user.DlgAddFriend;
import com.maydesk.base.gui.user.DlgUserSettings;
import com.maydesk.base.gui.user.FrmAvatar;
import com.maydesk.base.model.MUser;
import com.maydesk.dvratio.gui.FrmUsers;
import com.maydesk.social.gui.WzdAnnouncement;

/**
 * @author chrismay
 */
public class MDPerspective extends PDMenuProvider {

	public final static String PROJECT = "PROJECT"; //$NON-NLS-1$
	public final static int PROJECT_SERIAL = 986571165;

	@Override
	public MenuModel updateMenu() {

		DefaultMenuModel m = new DefaultMenuModel();

		DefaultMenuModel mnuUser = new DefaultMenuModel("1", "Settings");
		m.addItem(mnuUser);
		mnuUser.addItem(new DefaultOptionModel("1a", "My Data", null));
		mnuUser.addItem(new DefaultOptionModel("1b", "My Avatar", null));
		mnuUser.addItem(new DefaultOptionModel("1c", "Add friend/collegue", null));
		mnuUser.addItem(new DefaultOptionModel("1d", "New Announcement", null));
		mnuUser.addItem(new DefaultOptionModel("1e", "Change password", null));

		MUser user = PDUserSession.getInstance().getUser();

		if (user != null && user.getJabberId().equals("aoeu@jabber.de")) {
			DefaultMenuModel mnuAdmin = new DefaultMenuModel("2", "Admin");
			m.addItem(mnuAdmin);
			mnuAdmin.addItem(new DefaultOptionModel("2a", "Mail Settings", null));
			mnuAdmin.addItem(new DefaultOptionModel("2b", "User Administration", null));
		}

		return m;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String id = e.getActionCommand();
		if ("1a".equals(id)) {
			MUser user = PDUserSession.getInstance().getUser();
			PDDesktop.getInstance().addWindow(new DlgUserSettings(user, true));
		} else if ("1b".equals(id)) {
			PDDesktop.getInstance().addWindow(new FrmAvatar());
		} else if ("1c".equals(id)) {
			PDDesktop.getInstance().addWindow(new DlgAddFriend());
		} else if ("1d".equals(id)) {
			PDDesktop.getInstance().addWindow(new WzdAnnouncement());
		} else if ("1e".equals(id)) {
			PDDesktop.getInstance().addWindow(new DlgPassword());
		} else if ("2a".equals(id)) {
			PDDesktop.getInstance().addWindow(new DlgMailSettings());
		} else if ("2b".equals(id)) {
			PDDesktop.getInstance().addWindow(new FrmUsers());
		}
	}

	@Override
	protected void onInit() {
		// TODO Auto-generated method stub

	}
}