package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.context.widget.MDNewsTicker;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 3, 2013
 */
public class MDNewsTickerPeer extends MDAbstractFigurePeer {

	private static final String COMPONENT = MDNewsTicker.class.getSimpleName();

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(COMPONENT, "js/figures/MDNewsTicker.js"));
	}

	public MDNewsTickerPeer() {
		addOutputProperty(MDNewsTicker.PROPERTY_TEXT);
		addOutputProperty(MDNewsTicker.PROPERTY_AUTHOR);
	}
	
	public String getClientComponentType(boolean shortType) {
		return COMPONENT;
	}

	@Override
	public Class<MDNewsTicker> getComponentClass() {
		return MDNewsTicker.class;
	}

	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(COMPONENT);
	}
	
	@Override
	public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
		MDNewsTicker newsTicker = (MDNewsTicker) component;

		if (propertyName.equals(MDNewsTicker.PROPERTY_TEXT)) {
			return newsTicker.getText();
		}
		
		if (propertyName.equals(MDNewsTicker.PROPERTY_AUTHOR)) {
			return newsTicker.getAuthor();
		}
		
		return super.getOutputProperty(context, component, propertyName, propertyIndex);
	}
}