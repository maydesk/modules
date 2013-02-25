package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.context.widget.MDImage;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 3, 2013
 */
public class MDImagePeer extends MDAbstractFigurePeer {

	public static final String COMPONENT = MDImage.class.getSimpleName();

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(COMPONENT, "js/figures/MDImage.js"));
	}

	public MDImagePeer() {
		addOutputProperty(MDImage.PROPERTY_SRC);
	}
	
	public String getClientComponentType(boolean shortType) {
		return COMPONENT;
	}

	@Override
	public Class<MDImage> getComponentClass() {
		return MDImage.class;
	}

	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(COMPONENT);
	}

	@Override
	public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
		MDImage image = (MDImage) component;

		if (propertyName.equals(MDImage.PROPERTY_SRC)) {
			return image.getImage();
		}

		return super.getOutputProperty(context, component, propertyName, propertyIndex);
	}
}