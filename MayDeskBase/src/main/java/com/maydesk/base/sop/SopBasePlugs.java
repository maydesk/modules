/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.sop;

import lombok.soplets.Sop;

import com.maydesk.base.gui.BtnLogout;
import com.maydesk.base.gui.user.FrmUsers;
import com.maydesk.base.sop.plug.Plugable;
import com.maydesk.base.sop.plug.SopSocketType;
import com.maydesk.base.widgets.MDMenu;
import com.maydesk.base.widgets.PDRecycleBin;

/**
 * @author chrismay
 */
@Sop(aspects = Plugable.class)
public enum SopBasePlugs {

	@Soplet(plugTitle = "", plugType = SopSocketType.base)
	NULL,

	@Soplet(plugTitle = "Desktop", plugType = SopSocketType.desktop)
	desktop,

	@Soplet(plugTitle = "Desktop", plugType = SopSocketType.panel, editorClass = MDMenu.class)
	menu,

	@Soplet(plugTitle = "Footer Left", plugType = SopSocketType.panel)
	footerLeft,

	@Soplet(plugTitle = "Footer Right", plugType = SopSocketType.panel)
	footerRight,

	@Soplet(plugTitle = "Top Right", plugType = SopSocketType.panel)
	topRight,

	@Soplet(plugTitle = "Recycle Bin", plugType = SopSocketType.panel, editorClass = PDRecycleBin.class)
	recycleBin,

	@Soplet(plugTitle = "Show user admin", plugType = SopSocketType.action, editorClass = FrmUsers.class)
	openFrmUser,

	@Soplet(plugTitle = "User status", plugType = SopSocketType.panel, editorClass = BtnLogout.class)
	btnLogout;
}