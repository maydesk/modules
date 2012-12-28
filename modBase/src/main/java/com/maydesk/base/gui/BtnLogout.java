/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui;

import nextapp.echo.app.Insets;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.PDUserSession;
import com.maydesk.base.config.IPlugTarget;
import com.maydesk.base.config.XmlBaseEntry;
import com.maydesk.base.widgets.PDButton;

/**
 * @author chrismay
 */
public class BtnLogout extends PDButton implements IPlugTarget {

	public BtnLogout() {
		super("Logout", PDButton.STYLE.TRANSPARENT);
		setInsets(new Insets(10, 3, 3, 0));
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PDUserSession.getInstance().doLogout();
			}
		});
	}

	@Override
	public void initWire(XmlBaseEntry parentWire) {
	}
}