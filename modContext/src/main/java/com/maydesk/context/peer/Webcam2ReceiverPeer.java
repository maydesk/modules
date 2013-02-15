package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.context.widget.Webcam2Receiver;

public class Webcam2ReceiverPeer extends AbstractComponentSynchronizePeer {

	private static final String COMPONENT = "MDWebcam2Receiver";

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(COMPONENT, "js/MDWebcam2Receiver.js"));
	}

	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(COMPONENT);
		addOutputProperty(Webcam2Receiver.PROPERTY_VALUE);
		addEvent(new AbstractComponentSynchronizePeer.EventPeer("sendMessage", Webcam2Receiver.ACTION_SEND_MESSAGE, String.class) {
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
		return Webcam2Receiver.class;
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
		if (Webcam2Receiver.PROPERTY_VALUE.equals(propertyName)) {
			return String.class;
		}
		return super.getInputPropertyClass(propertyName);
	};
}