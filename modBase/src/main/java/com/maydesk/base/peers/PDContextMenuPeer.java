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
import nextapp.echo.app.util.Context;
import nextapp.echo.extras.webcontainer.service.CommonService;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDContextMenu;

/**
 * @author Alejandro Salas <br>
 *         Created on Sep 27, 2012
 */
public class PDContextMenuPeer extends AbstractComponentSynchronizePeer {

	private static final String PD_CONTEXT_PANE = "PDContextMenu";

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(PD_CONTEXT_PANE, "js/PDContextMenu.js"));
	}

	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(PD_CONTEXT_PANE);
		serverMessage.addLibrary(CommonService.INSTANCE.getId());
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getClientComponentType(boolean)
	 */
	@Override
	public String getClientComponentType(boolean shortType) {
		return PD_CONTEXT_PANE;
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getComponentClass()
	 */
	@Override
	public Class<PDContextMenu> getComponentClass() {
		return PDContextMenu.class;
	}
}