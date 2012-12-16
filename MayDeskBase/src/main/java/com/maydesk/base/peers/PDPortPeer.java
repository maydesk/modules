/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
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

public class PDPortPeer extends PDDesktopItemPeer {

	private static final String PD_PORT = "PDPort";
        
    static {
    	//TODO not very elegant to register PDDesktopItem.js here...
    	JavaScriptService SERVICE = JavaScriptService.forResources(PD_PORT, 
    			new String[] {
    				PDUtil.BASE_PATH + "js/PDPort.js"});
    	WebContainerServlet.getServiceRegistry().add(SERVICE);    	
    	ResourceRegistry resources = WebContainerServlet.getResourceRegistry();
    	resources.addPackage(PD_PORT, "img/");
        resources.add(PD_PORT, "timeline/highlight1.png", ContentType.IMAGE_PNG);
        resources.add(PD_PORT, "timeline/highlight2.png", ContentType.IMAGE_PNG);
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getClientComponentType(boolean)
     */
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
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(nextapp.echo.app.util.Context, Component)
     */
    @Override
    public void init(Context context, Component component) {
        super.init(context, component);
        ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
        serverMessage.addLibrary(PD_PORT);

    }
}
