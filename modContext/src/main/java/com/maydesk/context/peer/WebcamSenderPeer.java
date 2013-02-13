package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.context.widget.WebcamSender;

public class WebcamSenderPeer extends AbstractComponentSynchronizePeer {

	private static final String COMPONENT = "WebcamSender";

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(COMPONENT, "js/WebcamSender.js"));
	}

	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(COMPONENT);
		addOutputProperty(WebcamSender.PROPERTY_VALUE);
		addEvent(new AbstractComponentSynchronizePeer.EventPeer("startsending", WebcamSender.ACTION_START_SENDING, String.class) {
			@Override
			public boolean hasListeners(Context context, Component component) {
				return true;
			}
		});
		addEvent(new AbstractComponentSynchronizePeer.EventPeer("stopsending", WebcamSender.ACTION_STOP_SENDING, String.class) {
			@Override
			public boolean hasListeners(Context context, Component component) {
				return true;
			}
		});
		addEvent(new AbstractComponentSynchronizePeer.EventPeer("onCandidate", WebcamSender.ACTION_ON_CANDIDATE, String.class) {
			@Override
			public boolean hasListeners(Context context, Component component) {
				return true;
			}
		});
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getClientComponentType(boolean)
	 */
	@Override
	public String getClientComponentType(boolean shortType) {
		return COMPONENT;
	}

	/**
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getComponentClass()
	 */
	@Override
	public Class getComponentClass() {
		return WebcamSender.class;
	}
	
	/**
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#storeInputProperty(nextapp.echo.app.util.Context,
	 *      nextapp.echo.app.Component, java.lang.String, int, java.lang.Object)
	 */
	@Override
	public void storeInputProperty(Context context, Component component, String propertyName, int index, Object newValue) {
		ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
		clientUpdateManager.setComponentProperty(component, propertyName, newValue);
	}
	
	@Override
	public Class getInputPropertyClass(String propertyName) {
		if (WebcamSender.PROPERTY_VALUE.equals(propertyName)) {
			return String.class;
		}
		return super.getInputPropertyClass(propertyName);
	};
}