package com.maydesk.context.peer;

import com.maydesk.context.widget.MDCanvas;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

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

	//
	//
	//
	//
	//
	//
	// private static final String MD_CANVAS = "MDCanvas";
	//
	// static {
	// WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResources(MD_CANVAS,
	// new String[] {
	// "js/MDCanvas.js",
	// "js/draw2d/Class.js",
	// "js/draw2d/canvg.js",
	// "js/draw2d/json2.js",
	// "js/draw2d/rgbcolor.js",
	// "js/draw2d/shifty.js",
	// // "js/draw2d/jquery-1.8.1.js",
	// // "js/draw2d/jquery-1.8.1.min.js", //ILLEGAL
	// // "js/draw2d/jquery-touch_punch.js",
	// // "js/draw2d/jquery-ui-1.8.23.custom.min.js",
	// // "js/draw2d/jquery.autoresize.js",
	// // "js/draw2d/jquery.contextmenu.js",
	// // "js/draw2d/jquery.layout.js",
	// "js/draw2d/raphael.js",
	// "js/draw2d/draw2d.js"}
	// ));
	// CommonResources.install();
	//
	// Service ECHOPOINT_SERVICE = JavaScriptService.forResource(
	// "echopoint.Boot", "resource/js/Echopoint.js" );
	// Service JQUERY_SERVICE = JavaScriptService.forResource( "jq",
	// "resource/js/jquery/jquery.js" );
	//
	// }
	//
	// public String getClientComponentType(boolean shortType) {
	// return MD_CANVAS;
	// }
	//
	// @Override
	// public Class getComponentClass() {
	// return MDCanvas.class;
	// }
	//
	// @Override
	// public void init(Context context, Component component) {
	// super.init(context, component);
	// ServerMessage serverMessage = (ServerMessage)
	// context.get(ServerMessage.class);
	// serverMessage.addLibrary(MD_CANVAS);
	// serverMessage.addLibrary( CommonService.JQUERY_SERVICE.getId() );
	// }
	//
}
