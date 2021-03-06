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
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDDesktopItem;
import com.maydesk.base.widgets.PDShortcut;

/**
 * @author chrismay
 */
public class PDShortcutPeer extends PDDesktopItemPeer {

	private static final String PD_SHORTCUT = "PDShortcut";

	static {
		JavaScriptService SERVICE = JavaScriptService.forResources(PD_SHORTCUT, new String[] {"js/PDShortcut.js" });
		WebContainerServlet.getServiceRegistry().add(SERVICE);
	}

	public PDShortcutPeer() {
		addEvent(new AbstractComponentSynchronizePeer.EventPeer("action", PDShortcut.PROPERTY_ACTION_EVENT) {
			@Override
			public boolean hasListeners(Context context, Component component) {
				return true;
			}
		});
		addEvent(new AbstractComponentSynchronizePeer.EventPeer("mouseUp", PDShortcut.PROPERTY_ACTION_MOUSE_UP) {
			@Override
			public boolean hasListeners(Context context, Component component) {
				return true;
			}
		});
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getClientComponentType(boolean)
	 */
	@Override
	public String getClientComponentType(boolean shortType) {
		return PD_SHORTCUT;
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getComponentClass()
	 */
	@Override
	public Class getComponentClass() {
		return PDShortcut.class;
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(nextapp.echo.app.util.Context,
	 *      Component)
	 */
	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(PD_SHORTCUT);

	}

	/**
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#storeInputProperty(nextapp.echo.app.util.Context,
	 *      nextapp.echo.app.Component, java.lang.String, int, java.lang.Object)
	 */
	@Override
	public void storeInputProperty(Context context, Component component, String propertyName, int index, Object newValue) {
		ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
		if (PDDesktopItem.PROPERTY_POSITION_X.equals(propertyName)) {
			clientUpdateManager.setComponentProperty(component, PDDesktopItem.PROPERTY_POSITION_X, newValue);
		} else if (PDDesktopItem.PROPERTY_POSITION_Y.equals(propertyName)) {
			clientUpdateManager.setComponentProperty(component, PDDesktopItem.PROPERTY_POSITION_Y, newValue);
		}
	}

	/**
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#processEvent(nextapp.echo.app.util.Context,
	 *      nextapp.echo.app.Component, java.lang.String, java.lang.Object)
	 */
	@Override
	public void processEvent(Context context, Component component, String eventType, Object eventData) {
		ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
		if (PDShortcut.PROPERTY_ACTION_EVENT.equals(eventType)) {
			clientUpdateManager.setComponentAction(component, PDShortcut.PROPERTY_ACTION_EVENT, null);
		} else if (PDShortcut.PROPERTY_ACTION_MOUSE_UP.equals(eventType)) {
			clientUpdateManager.setComponentAction(component, PDShortcut.PROPERTY_ACTION_MOUSE_UP, null);
		}
	}
}
