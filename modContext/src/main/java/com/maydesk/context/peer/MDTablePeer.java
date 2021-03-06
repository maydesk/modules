package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.context.widget.MDTable;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 3, 2013
 */
public class MDTablePeer extends MDAbstractFigurePeer {

	private static final String COMPONENT = MDTable.class.getSimpleName();

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(COMPONENT, "js/figures/MDTable.js"));
	}

	public MDTablePeer() {
		addOutputProperty(MDTable.PROPERTY_DATA);
	}

	public String getClientComponentType(boolean shortType) {
		return COMPONENT;
	}

	@Override
	public Class<MDTable> getComponentClass() {
		return MDTable.class;
	}

	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(COMPONENT);
	}

	@Override
	public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
		if (propertyName.equals(MDTable.PROPERTY_DATA)) {
			MDTable table = (MDTable) component;
			if (table.getDataMap() != null) {
				return table.getDataMap();
			}
		}

		return super.getOutputProperty(context, component, propertyName, propertyIndex);
	}
}