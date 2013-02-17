package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.base.widgets.PDDesktopItem;
import com.maydesk.context.widget.MDAbstractFigure;
import com.maydesk.context.widget.MDText;
import com.maydesk.context.widget.MDVideo;

public class MDVideoPeer extends MDAbstractFigurePeer {

	public static final String COMPONENT = MDVideo.class.getSimpleName();
	
	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(COMPONENT, "js/figures/MDVideo.js"));
	}
	
	public MDVideoPeer() {
		addEvent(new AbstractComponentSynchronizePeer.EventPeer("connect", MDVideo.ACTION_CONNECT) {
			@Override
			public boolean hasListeners(Context context, Component component) {
				return true;
			}
		});
	}
	
    public String getClientComponentType(boolean shortType) {
        return COMPONENT;
    }
    
    @Override
    public Class getComponentClass() {
        return MDVideo.class;
    }

    @Override
    public void init(Context context, Component component) {
        super.init(context, component);
        ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
        serverMessage.addLibrary(COMPONENT);
    }
    
	@Override
	public Class getInputPropertyClass(String propertyName) {
		if (MDVideo.PROPERTY_URL.equals(propertyName)) {
			return String.class;
		} else {
			return null;
		}
	};
	
	@Override
	public void storeInputProperty(Context context, Component component, String propertyName, int index, Object newValue) {
		ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
		if (MDVideo.PROPERTY_URL.equals(propertyName)) {
			clientUpdateManager.setComponentProperty(component, MDVideo.PROPERTY_URL, newValue);
		} else {
			super.storeInputProperty(context, component, propertyName, index, newValue);
		}
	}
	
    @Override
    public void processEvent(Context context, Component component, String eventType, Object eventData) {
        ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
        if (MDVideo.ACTION_CONNECT.equals(eventType)) {
            clientUpdateManager.setComponentAction(component, MDVideo.ACTION_CONNECT, null);
        }
    }
}
