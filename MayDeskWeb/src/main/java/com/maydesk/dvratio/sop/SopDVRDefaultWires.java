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

import com.maydesk.base.aspects.Translatable;

/**
 * @author chrismay
 */
@Sop(aspects = { Wireable.class, Translatable.class })
public enum SopDVRDefaultWires {

	@Soplet(parent = SopDVRPlugs.NULL, plug = SopDVRPlugs.desktop)
	desktop,

	@Soplet(parent = SopDVRPlugs.NULL, plug = SopDVRPlugs.footerLeft)
	footerLeft,

	@Soplet(parent = SopDVRPlugs.NULL, plug = SopDVRPlugs.footerRight)
	footerRight,

	@Soplet(parent = SopDVRPlugs.NULL, plug = SopDVRPlugs.topRight)
	topRight,

	// @Soplet(
	// parent=SopDVRPlugs.desktop,
	// plug=SopDVRPlugs.createButton)
	// createButton,

	@Soplet(parent = SopDVRPlugs.desktop, plug = SopDVRPlugs.recycleBin)
	recycleBin,

	// @Soplet(
	// parent=SopDVRPlugs.createButton,
	// plug=SopDVRPlugs.createNews,
	// textEN="Create Announcement")
	// createNews,

	@Soplet(parent = SopDVRPlugs.desktop, plug = SopDVRPlugs.displayAnnouncement)
	displayAnnouncement,

	// @Soplet(
	// parent=SopDVRPlugs.createButton,
	// plug=SopDVRPlugs.openFrmUser,
	// textEN="Show user admin")
	// openFrmUser,
	//
	@Soplet(parent = SopDVRPlugs.footerLeft, plug = SopDVRPlugs.userStatus, textEN = "User status")
	userStatus,

	@Soplet(parent = SopDVRPlugs.footerRight, plug = SopDVRPlugs.btnLogout, textEN = "Logout")
	btnLogout,

	// @Soplet(
	// parent=SopDVRPlugs.topRight,
	// plug=SopDVRPlugs.omniBar,
	// textEN="OmniBar")
	// omnibar,
}