package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.context.widget.MDText;

public class MDTextPeer extends MDAbstractFigurePeer {

	public static final String COMPONENT = MDText.class.getSimpleName();

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResources(COMPONENT, 
				new String[] {"js/figures/MDText.js", "js/MDLabelEditor.js"}));
	}

	public MDTextPeer() {
		addOutputProperty(MDText.PROPERTY_TEXT);
		addOutputProperty(MDText.PROPERTY_TYPE);
	}

	public String getClientComponentType(boolean shortType) {
		return COMPONENT;
	}

	@Override
	public Class<MDText> getComponentClass() {
		return MDText.class;
	}

	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(COMPONENT);
		
	}

	public Class<?> getInputPropertyClass(String propertyName) {
		if (MDText.PROPERTY_TEXT.equals(propertyName) || MDText.PROPERTY_TYPE.equals(propertyName)) {
			return String.class;
		} else if (MDText.PROPERTY_SIZE.equals(propertyName)) {
			return Integer.class;
		}

		return super.getInputPropertyClass(propertyName);
	}

	public void storeInputProperty(Context context, Component component, String propertyName, int propertyIndex, Object newValue) {
		if (propertyName.equals(MDText.PROPERTY_TEXT) || propertyName.equals(MDText.PROPERTY_TYPE) || propertyName.equals(MDText.PROPERTY_SIZE)) {
			ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
			clientUpdateManager.setComponentProperty(component, propertyName, newValue);
		}
		
		super.storeInputProperty(context, component, propertyName, propertyIndex, newValue);
	}

	public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
		MDText text = (MDText) component;
		if (propertyName.equals(MDText.PROPERTY_TEXT)) {
			return text.getText();
		}
		if (propertyName.equals(MDText.PROPERTY_TYPE)) {
			return text.getType();
		}

		return super.getOutputProperty(context, component, propertyName, propertyIndex);
	}
}