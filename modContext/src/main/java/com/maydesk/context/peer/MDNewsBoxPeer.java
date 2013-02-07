package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.context.widget.MDNewsBox;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 3, 2013
 */
public class MDNewsBoxPeer extends MDAbstractFigurePeer {

	private static final String COMPONENT = MDNewsBox.class.getSimpleName();

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(COMPONENT, "js/figures/MDNewsBox.js"));
	}

	public MDNewsBoxPeer() {
		addOutputProperty(MDNewsBox.PROPERTY_TEXT);
		addOutputProperty(MDNewsBox.PROPERTY_TITLE);
	}
	
	public String getClientComponentType(boolean shortType) {
		return COMPONENT;
	}

	@Override
	public Class<MDNewsBox> getComponentClass() {
		return MDNewsBox.class;
	}

	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(COMPONENT);
	}
	
	@Override
	public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
		MDNewsBox newsBox = (MDNewsBox) component;

		if (propertyName.equals(MDNewsBox.PROPERTY_TEXT)) {
			return newsBox.getText();
		}
		
		if (propertyName.equals(MDNewsBox.PROPERTY_TITLE)) {
			return newsBox.getTitle();
		}

		return super.getOutputProperty(context, component, propertyName, propertyIndex);
	}
}