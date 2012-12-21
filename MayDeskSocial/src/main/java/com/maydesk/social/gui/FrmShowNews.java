/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.social.gui;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;

import com.maydesk.base.gui.PDSimpleDialog;
import com.maydesk.base.widgets.PDGrid;
import com.maydesk.base.widgets.PDLabel;
import com.maydesk.base.widgets.PDTextArea;
import com.maydesk.social.model.MAnnouncement;
import com.maydesk.social.sop.SopAnnouncement;

/**
 * @author chrismay
 */
public class FrmShowNews extends PDSimpleDialog {

	private PDGrid grd;
	private MAnnouncement intraNews;

	public FrmShowNews(MAnnouncement intraNews) {
		super(intraNews.getTitle(), 400, 250);
		this.intraNews = intraNews;
		init2();
	}

	private void init2() {
		grd.addLabel(SopAnnouncement.title);
		grd.add(new PDLabel(intraNews.getTitle(), PDLabel.STYLE.FIELD_VALUE));

		grd.addLabel(SopAnnouncement.text);
		PDTextArea txt = new PDTextArea();
		txt.setText(intraNews.getText());
		txt.setWidth(new Extent(300));
		txt.setHeight(new Extent(125));
		txt.setEditable(false);
		grd.add(txt);
	}

	@Override
	protected Component getMainContainer() {
		grd = new PDGrid(2);
		return grd;
	}
}
