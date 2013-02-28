package com.maydesk.context.peer;

import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.context.widget.MDRectangle;

public class MDRectanglePeer extends MDAbstractFigurePeer {

	public static final String COMPONENT = MDRectangle.class.getSimpleName();

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(COMPONENT, "js/figures/MDRectangle.js"));
	}

	public String getClientComponentType(boolean shortType) {
		return COMPONENT;
	}

	@Override
	public Class<MDRectangle> getComponentClass() {
		return MDRectangle.class;
	}

	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(COMPONENT);
	}

	/**
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getInputPropertyClass(java.lang.String)
	 */
	@Override
	public Class<?> getInputPropertyClass(String propertyName) {
		if (MDRectangle.PROPERTY_BORDER.equals(propertyName)) {
			return Integer.class;
		} else if (MDRectangle.PROPERTY_BACKGROUND.equals(propertyName)) {
			return Color.class;
		} else if (MDRectangle.PROPERTY_ALPHA.equals(propertyName)) {
			return Double.class;
		} else {
			return super.getInputPropertyClass(propertyName);
		}
	}

	@Override
	public void storeInputProperty(Context context, Component component, String propertyName, int propertyIndex, Object newValue) {
		if (propertyName.equals(MDRectangle.PROPERTY_BACKGROUND) && newValue == null) {
			return;
		}

		if (propertyName.equals(MDRectangle.PROPERTY_BORDER) || propertyName.equals(MDRectangle.PROPERTY_BACKGROUND)) {
			ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
			clientUpdateManager.setComponentProperty(component, propertyName, newValue);
		}

		super.storeInputProperty(context, component, propertyName, propertyIndex, newValue);
	}
}