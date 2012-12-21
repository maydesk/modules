/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.internal;

import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.PDUserSession;
import com.maydesk.base.model.MUser;
import com.maydesk.base.util.ILookAndFeel;
import com.maydesk.base.widgets.PDLabel;

import echopoint.Strut;

/**
 * @author chrismay
 */
public class PDLoginPanelOpenID extends Row {

	private final static String LOGIN_COOKIE_NAME = "PROFIDESK_LOGIN";

	private PDLabel lblStatus;

	public PDLoginPanelOpenID() {
		initGUI();
		PDUserSession.getInstance().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateStatus();
			}
		});
	}

	public void applyLookAndFeel(ILookAndFeel lookAndFeel) {
	}

	private void initGUI() {
		setCellSpacing(new Extent(6));
		setInsets(new Insets(3));

		// Font smallFont = new Font(Font.ARIAL, Font.PLAIN, new Extent(11));
		add(new Strut(3, 0));

		lblStatus = new PDLabel(PDLabel.STYLE.FIELD_LABEL);
		lblStatus.setForeground(Color.WHITE);
		add(lblStatus);

		updateStatus();
	}

	public void updateStatus() {
		PDUserSession us = PDUserSession.getInstance();
		MUser user = us.getUser();
		if (user != null) {
			lblStatus.setText("Status: You are logged in as: '" + user.getJabberId() + "'");
		} else {
			lblStatus.setText("Status: You are not logged in");
		}
	}
}