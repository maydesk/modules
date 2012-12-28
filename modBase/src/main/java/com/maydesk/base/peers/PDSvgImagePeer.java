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

import com.maydesk.base.gui.PDSvgImage;
import com.maydesk.base.util.PDUtil;

/**
 * @author chrismay
 */
public class PDSvgImagePeer extends AbstractComponentSynchronizePeer {

	private static final String PD_SVG_IMAGE = "PDSvgImage";
	private static final String JS_GRAPHICS = "wz_jsgraphics";
	private static final String MAPPER = "mapper";

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(PD_SVG_IMAGE, PDUtil.BASE_PATH + "js/PDSvgImage.js"));
		// WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(JS_GRAPHICS, "net/profidesk/pdw/js/wz_jsgraphics.js"));
		// WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(MAPPER, "net/profidesk/pdw/js/mapper.js"));
	}

	@Override
	public String getClientComponentType(boolean shortType) {
		return PD_SVG_IMAGE;
	}

	@Override
	public Class getComponentClass() {
		return PDSvgImage.class;
	}

	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(PD_SVG_IMAGE);
		// serverMessage.addLibrary(JS_GRAPHICS);
		// serverMessage.addLibrary(MAPPER);
	}

	@Override
	public Class getInputPropertyClass(String propertyName) {
		if (PDSvgImage.PROPERTY_SVG_DATA.equals(propertyName)) {
			return byte[].class;
		} else {
			return null;
		}
	};

	@Override
	public void storeInputProperty(Context context, Component component, String propertyName, int index, Object newValue) {
		ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
		if (PDSvgImage.PROPERTY_SVG_DATA.equals(propertyName)) {
			clientUpdateManager.setComponentProperty(component, PDSvgImage.PROPERTY_SVG_DATA, newValue);
		}
	}
}