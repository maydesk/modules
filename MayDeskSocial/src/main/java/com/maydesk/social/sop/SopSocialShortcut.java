/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.social.sop;

import lombok.soplets.Sop;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.ResourceImageReference;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.aspects.Descriptable;
import com.maydesk.base.aspects.Iconable;
import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MShortcut;
import com.maydesk.base.model.MUser;
import com.maydesk.base.util.ShortcutType;
import com.maydesk.social.gui.FrmShowNews;
import com.maydesk.social.model.MAnnouncement;

/**
 * @author chrismay
 */
@Sop(aspects = { Translatable.class, Descriptable.class, Iconable.class })
public enum SopSocialShortcut implements ShortcutType {

	@Soplet(textEN = "Show User", descriptionEN = "", icon = "img/announcement.png", modelClass = MUser.class)
	openIntraNews {
		@Override
		public void openShortcut(MShortcut shortcut) {
			MAnnouncement news = MBase.loadById(MAnnouncement.class, shortcut.getModelId());
			FrmShowNews frm = new FrmShowNews(news);
			PDDesktop.getInstance().addWindow(frm);
		}

		@Override
		public String getTitle(MShortcut shortcut) {
			MAnnouncement news = MBase.loadById(MAnnouncement.class, shortcut.getModelId());
			return news.getTitle();
		}
	};

	@Override
	public abstract void openShortcut(MShortcut shortcut);

	@Override
	public String getTitle(MShortcut shortcut) {
		return textEN();
	}

	@Override
	public ImageReference getIcon(MShortcut shortcut) {
		return new ResourceImageReference(this.icon());
	}

}
