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
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDContext;

/**
 * @author chrismay
 */
public class PDContextPeer extends AbstractComponentSynchronizePeer {

	private static final String PD_CONTEXT = "PDContext";

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(PD_CONTEXT, PDUtil.BASE_PATH + "js/PDContext.js"));
	}

	public PDContextPeer() {
		addEvent(new AbstractComponentSynchronizePeer.EventPeer("action", PDContext.PROPERTY_ACTION_EVENT) {
			@Override
			public boolean hasListeners(Context context, Component component) {
				return true;
			}
		});
		addEvent(new AbstractComponentSynchronizePeer.EventPeer("mouseUp", PDContext.PROPERTY_ACTION_MOUSE_UP) {
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
		return PD_CONTEXT;
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getComponentClass()
	 */
	@Override
	public Class getComponentClass() {
		return PDContext.class;
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(nextapp.echo.app.util.Context,
	 *      Component)
	 */
	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(PD_CONTEXT);
	}

	/**
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getInputPropertyClass(java.lang.String)
	 */
	@Override
	public Class getInputPropertyClass(String propertyName) {
		if (PDContext.PROPERTY_POSITION_X.equals(propertyName)) {
			return Extent.class;
		} else if (PDContext.PROPERTY_POSITION_Y.equals(propertyName)) {
			return Extent.class;
		} else if (PDContext.PROPERTY_WIDTH.equals(propertyName)) {
			return Extent.class;
		} else if (PDContext.PROPERTY_HEIGHT.equals(propertyName)) {
			return Extent.class;
		} else {
			return null;
		}
	};

	/**
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#storeInputProperty(nextapp.echo.app.util.Context,
	 *      nextapp.echo.app.Component, java.lang.String, int, java.lang.Object)
	 */
	@Override
	public void storeInputProperty(Context context, Component component, String propertyName, int index, Object newValue) {
		ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
		if (PDContext.PROPERTY_POSITION_X.equals(propertyName)) {
			clientUpdateManager.setComponentProperty(component, PDContext.PROPERTY_POSITION_X, newValue);
		} else if (PDContext.PROPERTY_POSITION_Y.equals(propertyName)) {
			clientUpdateManager.setComponentProperty(component, PDContext.PROPERTY_POSITION_Y, newValue);
		} else if (PDContext.PROPERTY_WIDTH.equals(propertyName)) {
			clientUpdateManager.setComponentProperty(component, PDContext.PROPERTY_WIDTH, newValue);
		} else if (PDContext.PROPERTY_HEIGHT.equals(propertyName)) {
			clientUpdateManager.setComponentProperty(component, PDContext.PROPERTY_HEIGHT, newValue);
		}
	}

	/**
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#processEvent(nextapp.echo.app.util.Context,
	 *      nextapp.echo.app.Component, java.lang.String, java.lang.Object)
	 */
	@Override
	public void processEvent(Context context, Component component, String eventType, Object eventData) {
		ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
		if (PDContext.PROPERTY_ACTION_EVENT.equals(eventType)) {
			clientUpdateManager.setComponentAction(component, PDContext.PROPERTY_ACTION_EVENT, null);
		} else if (PDContext.PROPERTY_ACTION_MOUSE_UP.equals(eventType)) {
			clientUpdateManager.setComponentAction(component, PDContext.PROPERTY_ACTION_MOUSE_UP, null);
		}
	}
}
