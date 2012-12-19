/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
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
