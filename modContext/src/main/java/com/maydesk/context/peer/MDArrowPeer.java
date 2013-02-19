package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ContentType;
import nextapp.echo.webcontainer.ResourceRegistry;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.context.widget.MDArrow;

public class MDArrowPeer extends MDAbstractFigurePeer {

	public static final String COMPONENT = MDArrow.class.getSimpleName();

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(COMPONENT, "js/figures/MDArrow.js"));
		ResourceRegistry resources = WebContainerServlet.getResourceRegistry();
		resources.addPackage(COMPONENT, "img/");
		resources.add(COMPONENT, "editor/back.gif", ContentType.IMAGE_GIF);
		resources.add(COMPONENT, "editor/forward.gif", ContentType.IMAGE_GIF);
		resources.add(COMPONENT, "editor/minus2.png", ContentType.IMAGE_PNG);
		resources.add(COMPONENT, "editor/plus2.png", ContentType.IMAGE_PNG);
	}

	public String getClientComponentType(boolean shortType) {
		return COMPONENT;
	}

	@Override
	public Class<MDArrow> getComponentClass() {
		return MDArrow.class;
	}

	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(COMPONENT);
	}

	@Override
	public Class<?> getInputPropertyClass(String propertyName) {
		if (MDArrow.PROPERTY_SIZE.equals(propertyName)) {
			return Integer.class;
		} else if (MDArrow.PROPERTY_START_POSITION_X.equals(propertyName) || MDArrow.PROPERTY_START_POSITION_Y.equals(propertyName) || MDArrow.PROPERTY_END_POSITION_X.equals(propertyName)
				|| MDArrow.PROPERTY_END_POSITION_Y.equals(propertyName)) {
			return Integer.class;
		}

		return super.getInputPropertyClass(propertyName);
	}

	@Override
	public void storeInputProperty(Context context, Component component, String propertyName, int propertyIndex, Object newValue) {
		if (propertyName.equals(MDArrow.PROPERTY_SIZE) || propertyName.equals(MDArrow.PROPERTY_START_POSITION_X) || propertyName.equals(MDArrow.PROPERTY_START_POSITION_Y)
				|| propertyName.equals(MDArrow.PROPERTY_END_POSITION_X) || propertyName.equals(MDArrow.PROPERTY_END_POSITION_Y)) {
			ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
			clientUpdateManager.setComponentProperty(component, propertyName, newValue);
		}

		super.storeInputProperty(context, component, propertyName, propertyIndex, newValue);
	}
}