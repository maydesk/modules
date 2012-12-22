/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.peers;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDDesktopItem;

/**
 * @author chrismay
 */
public abstract class PDDesktopItemPeer extends AbstractComponentSynchronizePeer {

	/** The associated client-side JavaScript module <code>Service</code>. */
	private static final JavaScriptService DESKTOP_ITEM_SERVICE = JavaScriptService.forResource("PDDesktopItem", PDUtil.BASE_PATH + "js/PDDesktopItem.js");

	static {
		WebContainerServlet.getServiceRegistry().add(DESKTOP_ITEM_SERVICE);
	}

	/**
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getInputPropertyClass(java.lang.String)
	 */
	@Override
	public Class getInputPropertyClass(String propertyName) {
		if (PDDesktopItem.PROPERTY_POSITION_X.equals(propertyName)) {
			return Extent.class;
		} else if (PDDesktopItem.PROPERTY_POSITION_Y.equals(propertyName)) {
			return Extent.class;
		} else {
			return null;
		}
	};

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(nextapp.echo.app.util.Context,
	 *      Component)
	 */
	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary("PDDesktopItem");

	}
}
