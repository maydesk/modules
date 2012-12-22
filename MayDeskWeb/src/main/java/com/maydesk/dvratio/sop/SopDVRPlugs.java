/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.dvratio.sop;

import lombok.soplets.Sop;

import com.maydesk.base.sop.plug.Plugable;
import com.maydesk.base.sop.plug.SopSocketType;
import com.maydesk.base.widgets.PDRecycleBin;
import com.maydesk.dvratio.gui.FrmUsers;
import com.maydesk.social.gui.BtnLogout;
import com.maydesk.social.gui.PnlAnnouncement;
import com.maydesk.social.gui.PnlUserStatus;

/**
 * @author chrismay
 */
@Sop(aspects = Plugable.class)
public enum SopDVRPlugs {

	@Soplet(plugTitle = "", plugType = SopSocketType.base)
	NULL,

	@Soplet(plugTitle = "Desktop", plugType = SopSocketType.desktop)
	desktop,

	@Soplet(plugTitle = "Footer Left", plugType = SopSocketType.panel)
	footerLeft,

	@Soplet(plugTitle = "Footer Right", plugType = SopSocketType.panel)
	footerRight,

	@Soplet(plugTitle = "Top Right", plugType = SopSocketType.panel)
	topRight,

	// @Soplet(
	// plugTitle="Creation Button",
	// plugType=SopSocketType.panel,
	// editorClass=PDNewButton.class)
	// createButton,

	@Soplet(plugTitle = "Recycle Bin", plugType = SopSocketType.panel, editorClass = PDRecycleBin.class)
	recycleBin,

	// @Soplet(
	// plugTitle="Create Announcement",
	// plugType=SopSocketType.action,
	// editorClass=WzdAnnouncement.class)
	// createNews,

	@Soplet(plugTitle = "Display Announcement", plugType = SopSocketType.panel, editorClass = PnlAnnouncement.class)
	displayAnnouncement,

	@Soplet(plugTitle = "Show user admin", plugType = SopSocketType.action, editorClass = FrmUsers.class)
	openFrmUser,

	@Soplet(plugTitle = "User status", plugType = SopSocketType.panel, editorClass = PnlUserStatus.class)
	userStatus,

	@Soplet(plugTitle = "User status", plugType = SopSocketType.panel, editorClass = BtnLogout.class)
	btnLogout,

	// @Soplet(
	// plugTitle="OmniBar",
	// plugType=SopSocketType.panel,
	// editorClass=DVROmniBar.class)
	// omniBar,
	;
}