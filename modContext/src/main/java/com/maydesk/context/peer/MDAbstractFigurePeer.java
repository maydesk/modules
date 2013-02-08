/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDAvatar;
import com.maydesk.base.widgets.PDDesktopItem;
import com.maydesk.context.widget.MDAbstractFigure;
import com.maydesk.context.widget.MDArrow;
import com.maydesk.context.widget.MDContext;

/**
 * @author chrismay
 */
public abstract class MDAbstractFigurePeer extends Draw2dAbstractPeer {

	public static final String COMPONENT = MDAbstractFigure.class.getSimpleName();

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(COMPONENT, "js/figures/MDAbstractFigure.js"));
	}

	public MDAbstractFigurePeer() {
		addEvent(new AbstractComponentSynchronizePeer.EventPeer("move", MDAbstractFigure.ACTION_MOVE) {
			@Override
			public boolean hasListeners(Context context, Component component) {
				return true;
			}
		});
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
		serverMessage.addLibrary("MDAbstractFigure");
		
	}
	
	/**
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#storeInputProperty(nextapp.echo.app.util.Context,
	 *      nextapp.echo.app.Component, java.lang.String, int, java.lang.Object)
	 */
	@Override
	public void storeInputProperty(Context context, Component component, String propertyName, int index, Object newValue) {
		ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
		if (MDAbstractFigure.PROPERTY_POSITION_X.equals(propertyName)) {
			clientUpdateManager.setComponentProperty(component, MDAbstractFigure.PROPERTY_POSITION_X, newValue);
		} else if (MDAbstractFigure.PROPERTY_POSITION_Y.equals(propertyName)) {
			clientUpdateManager.setComponentProperty(component, MDAbstractFigure.PROPERTY_POSITION_Y, newValue);
		} 
	}
	
    /**
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#processEvent(nextapp.echo.app.util.Context, 
     *      nextapp.echo.app.Component, java.lang.String, java.lang.Object)
     */
    @Override
    public void processEvent(Context context, Component component, String eventType, Object eventData) {
        ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
        if (MDAbstractFigure.ACTION_MOVE.equals(eventType)) {
            clientUpdateManager.setComponentAction(component, MDAbstractFigure.ACTION_MOVE, null);
        }
    }
}
