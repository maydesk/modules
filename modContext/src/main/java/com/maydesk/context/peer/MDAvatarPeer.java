package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.context.widget.MDAvatar;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 3, 2013
 */
public class MDAvatarPeer extends MDAbstractFigurePeer {

	private static final String COMPONENT = MDAvatar.class.getSimpleName();

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(COMPONENT, "js/figures/MDAvatar.js"));
	}

	public MDAvatarPeer() {
		addOutputProperty(MDAvatar.PROPERTY_TEXT);
		addOutputProperty(MDAvatar.PROPERTY_IMAGE);
	}
	
	public String getClientComponentType(boolean shortType) {
		return COMPONENT;
	}

	@Override
	public Class<MDAvatar> getComponentClass() {
		return MDAvatar.class;
	}

	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(COMPONENT);
	}
	
	@Override
	public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
		MDAvatar avatar = (MDAvatar) component;
		if (propertyName.equals(MDAvatar.PROPERTY_TEXT)) {
			return avatar.getText();
		}

		if (propertyName.equals(MDAvatar.PROPERTY_IMAGE)) {
			return avatar.getImage();
		}

		return super.getOutputProperty(context, component, propertyName, propertyIndex);
	}
}