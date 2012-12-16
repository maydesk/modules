package com.maydesk.social.sop;

import lombok.soplets.Sop;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.ResourceImageReference;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.aspects.Descriptable;
import com.maydesk.base.aspects.Iconable;
import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.model.MShortcut;
import com.maydesk.base.model.MUser;
import com.maydesk.base.util.ShortcutType;
import com.maydesk.social.gui.FrmShowNews;
import com.maydesk.social.model.MAnnouncement;

@Sop(aspects={Translatable.class, Descriptable.class, Iconable.class}) 
public enum SopSocialShortcut implements ShortcutType {
	
	@Soplet(
		textEN = "Show User",
		descriptionEN = "",
		icon = "img/announcement.png",
		modelClass = MUser.class)
	openIntraNews {
		@Override
		public void openShortcut(MShortcut shortcut) {
			MAnnouncement news = MAnnouncement.loadById(MAnnouncement.class, shortcut.getModelId());
			FrmShowNews frm = new FrmShowNews(news);
			PDDesktop.getInstance().addWindow(frm);
		}
		@Override
		public String getTitle(MShortcut shortcut) {
			MAnnouncement news = MAnnouncement.loadById(MAnnouncement.class, shortcut.getModelId());
			return news.getTitle();
		}
	};
	

	public abstract void openShortcut(MShortcut shortcut);

	public String getTitle(MShortcut shortcut) {
		return textEN();
	}

	@Override
	public ImageReference getIcon(MShortcut shortcut) {
		return new ResourceImageReference(this.icon());
	}

}
