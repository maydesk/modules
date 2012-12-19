package com.maydesk.dvratio.cmd;

import lombok.soplets.Sop;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.ResourceImageReference;

import com.maydesk.base.PDUserSession;
import com.maydesk.base.aspects.Descriptable;
import com.maydesk.base.aspects.Iconable;
import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MShortcut;
import com.maydesk.base.model.MUser;
import com.maydesk.base.util.ShortcutType;

/**
 * @author chrismay
 */
@Sop(aspects = { Translatable.class, Descriptable.class, Iconable.class })
public enum SopShortcut implements ShortcutType {

	@Soplet(textEN = "Show User", descriptionEN = "", icon = "img/web-browser.png", modelClass = MUser.class)
	openUser {
		@Override
		public void openShortcut(MShortcut shortcut) {
		}

		@Override
		public String getTitle(MShortcut shortcut) {
			return getUser(shortcut).toString();
		}

		@Override
		public ImageReference getIcon(MShortcut shortcut) {
			return PDUserSession.getInstance().getImage(getUser(shortcut).getJabberId());
		}

		private MUser getUser(MShortcut shortcut) {
			return MBase.loadById(MUser.class, shortcut.getModelId());
		}
	};

	@Override
	public abstract void openShortcut(MShortcut shortcut);

	@Override
	public String getTitle(MShortcut shortcut) {
		return textEN();
	}

	public ImageReference icon(MShortcut shortcut) {
		return new ResourceImageReference(this.icon());
	}
}
