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