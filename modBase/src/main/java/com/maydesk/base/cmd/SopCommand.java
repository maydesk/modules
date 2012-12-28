package com.maydesk.base.cmd;

import lombok.soplets.Sop;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.gui.DlgMailSettings;
import com.maydesk.base.gui.DlgPassword;
import com.maydesk.base.gui.user.DlgUserSettings;
import com.maydesk.base.gui.user.FrmUsers;

@Sop(aspects=Translatable.class)
public enum SopCommand implements ICommand {

	@Soplet(textEN="My Data")
    userSettings {
		@Override
		public void doCommand() {
			PDDesktop.getInstance().add(new DlgUserSettings(PDUserSession.getInstance().getUser(), true));
		}
	},
	
	@Soplet(textEN="Password")
	password {
		@Override
		public void doCommand() {
			PDDesktop.getInstance().add(new DlgPassword());
		}
	},

	@Soplet(textEN="Mail Settings")
	mailSettings {
		@Override
		public void doCommand() {
			PDDesktop.getInstance().addWindow(new DlgMailSettings());
		}
	},
	
	@Soplet(textEN="User Admin")
	userAdmin {
		@Override
		public void doCommand() {
			PDDesktop.getInstance().addWindow(new FrmUsers());
		}
	};
    
}