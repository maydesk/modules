/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.context.peer;

import com.maydesk.base.peers.PDDesktopItemPeer;
import com.maydesk.base.widgets.PDDesktopItem;
import com.maydesk.context.widget.MDContext;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.extras.webcontainer.service.CommonService;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;
import nextapp.echo.webcontainer.sync.component.SelectFieldPeer;

public class MDContextPeer extends AbstractComponentSynchronizePeer {

	private static final String MD_CONTEXT = "MDContext";
    
    static {
        WebContainerServlet.getServiceRegistry().add(
        		JavaScriptService.forResource(MD_CONTEXT,
        		"js/MDContext.js"));        
    }

    public MDContextPeer() {
	    addEvent(new AbstractComponentSynchronizePeer.EventPeer("action", MDContext.PROPERTY_ACTION_EVENT) {
	        public boolean hasListeners(Context context, Component component) {
	            return true;
	        }
	    });
	    addEvent(new AbstractComponentSynchronizePeer.EventPeer("mouseUp", MDContext.PROPERTY_ACTION_MOUSE_UP) {
	        public boolean hasListeners(Context context, Component component) {
	            return true;
	        }
	    });
    }
    
    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getClientComponentType(boolean)
     */
    public String getClientComponentType(boolean shortType) {
        return MD_CONTEXT;
    }
    
    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getComponentClass()
     */
    @Override
    public Class getComponentClass() {
        return MDContext.class;
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(nextapp.echo.app.util.Context, Component)
     */
    @Override
    public void init(Context context, Component component) {
        super.init(context, component);
        ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
        serverMessage.addLibrary(CommonService.INSTANCE.getId());
        serverMessage.addLibrary(PDDesktopItemPeer.DESKTOP_ITEM_SERVICE.getId());
        serverMessage.addLibrary(MD_CONTEXT);
        serverMessage.addLibrary("Echo.ListComponent");
    }
    
    /**
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getInputPropertyClass(java.lang.String)
     */
    @Override
    public Class getInputPropertyClass(String propertyName) {
        if (MDContext.PROPERTY_POSITION_X.equals(propertyName)) {
            return Extent.class;
        } else if (MDContext.PROPERTY_POSITION_Y.equals(propertyName)) {
            return Extent.class;
        } else if (MDContext.PROPERTY_WIDTH.equals(propertyName)) {
            return Extent.class;
        } else if (MDContext.PROPERTY_HEIGHT.equals(propertyName)) {
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
        if (MDContext.PROPERTY_POSITION_X.equals(propertyName)) {
            clientUpdateManager.setComponentProperty(component, MDContext.PROPERTY_POSITION_X, newValue);
        } else if (MDContext.PROPERTY_POSITION_Y.equals(propertyName)) {
            clientUpdateManager.setComponentProperty(component, MDContext.PROPERTY_POSITION_Y, newValue);
        } else if (MDContext.PROPERTY_WIDTH.equals(propertyName)) {
            clientUpdateManager.setComponentProperty(component, MDContext.PROPERTY_WIDTH, newValue);
        } else if (MDContext.PROPERTY_HEIGHT.equals(propertyName)) {
            clientUpdateManager.setComponentProperty(component, MDContext.PROPERTY_HEIGHT, newValue);
        }
    }

    /**
     * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#processEvent(nextapp.echo.app.util.Context, 
     *      nextapp.echo.app.Component, java.lang.String, java.lang.Object)
     */
    @Override
    public void processEvent(Context context, Component component, String eventType, Object eventData) {
        ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
        if (MDContext.PROPERTY_ACTION_EVENT.equals(eventType)) {
            clientUpdateManager.setComponentAction(component, MDContext.PROPERTY_ACTION_EVENT, null);
        } else if (MDContext.PROPERTY_ACTION_MOUSE_UP.equals(eventType)) {
            clientUpdateManager.setComponentAction(component, MDContext.PROPERTY_ACTION_MOUSE_UP, null);
        }
    }
}
