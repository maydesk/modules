package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;
import nextapp.echo.webcontainer.sync.component.RowPeer;

import org.w3c.dom.Element;

import com.maydesk.context.widget.MDCanvas;

public class MDCanvasPeer extends Draw2dAbstractPeer {

	private static final String COMPONENT_NAME = MDCanvas.class.getSimpleName();
	private static final String[] SERVICE_FILES = { "js/MDCanvas.js" };
	private static final Service COMPONENT_SERVICE = JavaScriptService.forResources(COMPONENT_NAME, SERVICE_FILES);

	static {		
		WebContainerServlet.getServiceRegistry().add(COMPONENT_SERVICE);
	}

	public MDCanvasPeer() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(final Context context, final Component component) {
		super.init(context, component);
		final ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(COMPONENT_NAME);
		serverMessage.addLibrary("Echo.ArrayContainer");
		serverMessage.addLibrary("Echo.Button");
		serverMessage.addLibrary(MDAbstractFigurePeer.COMPONENT);
		serverMessage.addLibrary(MDRectanglePeer.COMPONENT);
		serverMessage.addLibrary(MDArrowPeer.COMPONENT);
		serverMessage.addLibrary(MDTextPeer.COMPONENT);
		serverMessage.addLibrary(MDImagePeer.COMPONENT);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getComponentClass
	 */
	@Override
	public Class getComponentClass() {
		return MDCanvas.class;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getClientComponentType
	 */
	public String getClientComponentType(final boolean shortType) {
		return COMPONENT_NAME;
	}
}
