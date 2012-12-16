/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.peers;

import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDDesktopItem;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

public abstract class PDDesktopItemPeer extends AbstractComponentSynchronizePeer {

    /** The associated client-side JavaScript module <code>Service</code>. */
    private static final JavaScriptService DESKTOP_ITEM_SERVICE = 
    		JavaScriptService.forResource("PDDesktopItem", 
			PDUtil.BASE_PATH + "js/PDDesktopItem.js");

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
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(nextapp.echo.app.util.Context, Component)
     */
    @Override
    public void init(Context context, Component component) {
        super.init(context, component);
        ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
        serverMessage.addLibrary("PDDesktopItem");

    }
}
