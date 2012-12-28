/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import nextapp.echo.app.Color;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.extras.app.MenuBarPane;
import nextapp.echo.extras.app.menu.DefaultMenuModel;

import com.maydesk.base.config.IPlugTarget;
import com.maydesk.base.config.XmlBaseEntry;
import com.maydesk.base.config.XmlMenu;
import com.maydesk.base.util.PDUtil;

/**
 * @author chrismay
 */
public class MDMenu extends MenuBarPane implements IPlugTarget {
	
	@Override
	public void initWire(XmlBaseEntry wire) {
		
		setAnimationTime(600);
		setForeground(Color.WHITE);
		setBorder(PDUtil.emptyBorder());

		addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		DefaultMenuModel menuModel = new DefaultMenuModel();
		addEntries(menuModel, (XmlMenu)wire);
		setModel(menuModel);
	}
	
	private void addEntries(DefaultMenuModel parent, XmlMenu wire) {
		for (XmlMenu subWire : wire.getMenuEntries()) {
			String caption = subWire.getTextEN();
			DefaultMenuModel entry = new DefaultMenuModel("id", caption);
			parent.addItem(entry);
			addEntries(entry, subWire);
		}
	}
}
