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
import nextapp.echo.webcontainer.ContentType;
import nextapp.echo.webcontainer.ResourceRegistry;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDPort;

/**
 * @author chrismay
 */
public class PDPortPeer extends PDDesktopItemPeer {

	private static final String PD_PORT = "PDPort";

	static {
		// TODO not very elegant to register PDDesktopItem.js here...
		JavaScriptService SERVICE = JavaScriptService.forResources(PD_PORT, new String[] { PDUtil.BASE_PATH + "js/PDPort.js" });
		WebContainerServlet.getServiceRegistry().add(SERVICE);
		ResourceRegistry resources = WebContainerServlet.getResourceRegistry();
		resources.addPackage(PD_PORT, "img/");
		resources.add(PD_PORT, "timeline/highlight1.png", ContentType.IMAGE_PNG);
		resources.add(PD_PORT, "timeline/highlight2.png", ContentType.IMAGE_PNG);
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getClientComponentType(boolean)
	 */
	@Override
	public String getClientComponentType(boolean shortType) {
		return PD_PORT;
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getComponentClass()
	 */
	@Override
	public Class getComponentClass() {
		return PDPort.class;
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(nextapp.echo.app.util.Context,
	 *      Component)
	 */
	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(PD_PORT);

	}
}
