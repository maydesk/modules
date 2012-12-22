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
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;
import nextapp.echo.webcontainer.sync.component.WindowPanePeer;

import com.maydesk.base.gui.PDWindowPane;
import com.maydesk.base.util.PDUtil;

/**
 * @author chrismay
 */
public class PDWindowPanePeer extends WindowPanePeer {

	private static final String PD_WINDOW_PANE = "PDWindowPane";

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(PD_WINDOW_PANE, PDUtil.BASE_PATH + "js/PDWindowPane.js"));
	}

	public PDWindowPanePeer() {
	}

	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(PD_WINDOW_PANE);
		serverMessage.addLibrary(CommonService.INSTANCE.getId());
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getClientComponentType(boolean)
	 */
	@Override
	public String getClientComponentType(boolean shortType) {
		return PD_WINDOW_PANE;
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getComponentClass()
	 */
	@Override
	public Class getComponentClass() {
		return PDWindowPane.class;
	}
}
