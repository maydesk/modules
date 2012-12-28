package com.maydesk.social.sop;

import lombok.soplets.Sop;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.cmd.ICommand;
import com.maydesk.base.gui.user.DlgAddFriend;
import com.maydesk.base.gui.user.FrmAvatar;
import com.maydesk.social.gui.WzdAnnouncement;

@Sop(aspects=Translatable.class)
public enum SopSocialCommand implements ICommand {

	@Soplet(textEN="Add friend/collegue")
    addFriend {
		@Override
		public void doCommand() {
			PDDesktop.getInstance().add(new DlgAddFriend());
		}
	},

	@Soplet(textEN="My Data")
	editAvatar {
		@Override
		public void doCommand() {
			PDDesktop.getInstance().add(new FrmAvatar());
		}
	},

	@Soplet(textEN="New Announcement")
    newAnnouncement {
		@Override
		public void doCommand() {
			PDDesktop.getInstance().addWindow(new WzdAnnouncement());
		}
	};    
}
