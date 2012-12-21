/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import nextapp.echo.app.Extent;
import nextapp.echo.app.ImageReference;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.model.MShortcut;

/**
 * A <code>Component</code> which renders its contents in a floating, movable window.
 * 
 * @author chrismay
 */
public class PDShortcut extends PDDesktopItem {

	public static final String PROPERTY_ACTION_EVENT = "action";
	public static final String PROPERTY_ACTION_MOUSE_UP = "mouseUp";
	public static final String PROPERTY_ICON = "icon";

	protected MShortcut shortcut;

	public PDShortcut(MShortcut shortcut) {
		super(shortcut.getTitle(), shortcut.getPositionX(), shortcut.getPositionY());
		this.shortcut = shortcut;
		try {
			ImageReference img = shortcut.getIcon();
			set(PROPERTY_ICON, img);
		} catch (Exception e) {
			System.out.println("Icon for shortcut " + shortcut + " was invalid: " + shortcut.getIcon());
		}
	}

	@Override
	public void processInput(String inputName, Object inputValue) {
		if (PROPERTY_ACTION_MOUSE_UP.equals(inputName)) {
			if (shortcut.getPositionX() < 50 && shortcut.getPositionY() < 55) {
				// sent to recycle bin
				setVisible(false);
				if (shortcut.getId() > 0) {
					PDHibernateFactory.getSession().delete(shortcut);
				}
			} else {
				// store the new x/y position
				if (shortcut.getOwner() != null) {
					PDHibernateFactory.getSession().saveOrUpdate(shortcut);
				}
			}
			// shortcut.onMouseUp(this);
		} else if (PROPERTY_POSITION_X.equals(inputName)) {
			shortcut.setPositionX(((Extent) inputValue).getValue());
		} else if (PROPERTY_POSITION_Y.equals(inputName)) {
			shortcut.setPositionY(((Extent) inputValue).getValue());
		} else if (PROPERTY_ACTION_EVENT.equals(inputName)) {
			shortcut.executeTask();
		}
	}

	public MShortcut getModel() {
		return shortcut;
	}
}
