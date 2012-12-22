/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/
package com.maydesk.base;

import nextapp.echo.app.event.ActionListener;
import nextapp.echo.extras.app.menu.DefaultOptionModel;
import nextapp.echo.extras.app.menu.ItemModel;
import nextapp.echo.extras.app.menu.MenuModel;

/**
 * An abstract implementation for providing a perspective Extends this class to
 * provide a personalized view of your desktop
 * 
 * @author chrismay
 */
public abstract class PDMenuProvider implements ActionListener {

	public PDMenuProvider() {
	}

	public ItemModel getMenuItem(String name, String actionId) {
		DefaultOptionModel mit = new DefaultOptionModel(name, actionId, null);
		return mit;
	}

	protected abstract void onInit();

	public abstract MenuModel updateMenu();

	protected void loggingOut() {
		// Empty
	}
}
