package com.maydesk.base.peers;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;
import nextapp.echo.webcontainer.sync.component.TextComponentPeer;

import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDTextArea;

/**
 * Synchronization peer for <code>TextArea</code>s.
 * 
 * @author chrismay
 */
public class PDTextAreaPeer extends TextComponentPeer {

	private static final String PD_TEXT_AREA = "PD.RTA";

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(PD_TEXT_AREA, PDUtil.BASE_PATH + "js/PDTextArea.js"));
	}

	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(PD_TEXT_AREA);
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getClientComponentType(boolean)
	 */
	@Override
	public String getClientComponentType(boolean shortType) {
		return PD_TEXT_AREA;
	}

	/**
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getComponentClass()
	 */
	@Override
	public Class getComponentClass() {
		return PDTextArea.class;
	}
}
