package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.context.widget.MDCanvas;

public class MDCanvasPeer extends Draw2dAbstractPeer {

	private static final String COMPONENT_NAME = MDCanvas.class.getSimpleName();
	private static final String[] SERVICE_FILES = { "js/MDCanvas.js" };
	private static final Service COMPONENT_SERVICE = JavaScriptService.forResources(COMPONENT_NAME, SERVICE_FILES);

	static {		
		WebContainerServlet.getServiceRegistry().add(COMPONENT_SERVICE);
	}

	public MDCanvasPeer() {
		addEvent(new AbstractComponentSynchronizePeer.EventPeer(MDCanvas.INPUT_CLICK, MDCanvas.ACTION_LISTENERS_CHANGED_PROPERTY) {
            public boolean hasListeners(Context context, Component component) {
                return ((MDCanvas) component).hasActionListeners();
            }
        });
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
	public Class<MDCanvas> getComponentClass() {
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
	
	@Override
	public Class<?> getInputPropertyClass(String propertyName) {
		if (MDCanvas.PROPERTY_CLICK_X.equals(propertyName) || MDCanvas.PROPERTY_CLICK_Y.equals(propertyName)) {
			return Double.class;
		}
		return super.getInputPropertyClass(propertyName);
	}
	
	@Override
	public void storeInputProperty(Context context, Component component, String propertyName, int propertyIndex, Object newValue) {
		if (propertyName.equals(MDCanvas.PROPERTY_CLICK_X) || propertyName.equals(MDCanvas.PROPERTY_CLICK_Y)) {
			ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
			clientUpdateManager.setComponentProperty(component, propertyName, newValue);
		}
		super.storeInputProperty(context, component, propertyName, propertyIndex, newValue);
	}
	
//	@Override
//	public void processEvent(Context context, Component component, String eventType, Object eventData) {
//		super.processEvent(context, component, eventType, eventData);
//		ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
//		if (MDCanvas.INPUT_CLICK.equals(eventType)) {
//			clientUpdateManager.setComponentAction(component, MDCanvas.INPUT_CLICK, null);
//		}
//	}
}