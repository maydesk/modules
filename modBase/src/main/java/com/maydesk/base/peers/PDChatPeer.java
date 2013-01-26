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
import com.maydesk.base.widgets.PDChat;

/**
 * Synchronization peer for <code>Chat</code>s.
 * 
 * @author Alejandro Salas <br>
 *         Created on Jan 4, 2013
 */
public class PDChatPeer extends AbstractComponentSynchronizePeer {

	private static final String PD_CHAT = "PDChat";

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(PD_CHAT, "js/PDChat.js"));
	}

	public PDChatPeer() {
		addOutputProperty(PDChat.PROPERTY_INCOMING_MESSAGES);
	}
	
	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(PD_CHAT);
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getClientComponentType(boolean)
	 */
	@Override
	public String getClientComponentType(boolean shortType) {
		return PD_CHAT;
	}

	/**
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getComponentClass()
	 */
	@Override
	public Class<PDChat> getComponentClass() {
		return PDChat.class;
	}

	public Class<?> getInputPropertyClass(String propertyName) {
		if (PDChat.PROPERTY_OUTGOING_MESSAGE.equals(propertyName)) {
			return String.class;
		}
		return null;
	}

	public void storeInputProperty(Context context, Component component, String propertyName, int propertyIndex, Object newValue) {
		if (propertyName.equals(PDChat.PROPERTY_OUTGOING_MESSAGE)) {
			ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
			clientUpdateManager.setComponentProperty(component, PDChat.PROPERTY_OUTGOING_MESSAGE, newValue);
		}
	}

	public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
		if (propertyName.equals(PDChat.PROPERTY_INCOMING_MESSAGES)) {
			PDChat pdChat = (PDChat) component;
			if (pdChat.getIncomingMessagesMap() != null) {
				return pdChat.getIncomingMessagesMap();
			}
		}
		
		return super.getOutputProperty(context, component, propertyName, propertyIndex);
	}
}
