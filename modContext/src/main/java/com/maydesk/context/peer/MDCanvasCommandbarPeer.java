package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.context.widget.MDCanvasCommandbar;
import com.maydesk.context.widget.MDCanvasToolbar;

public class MDCanvasCommandbarPeer extends MDAbstractFigurePeer {

	public static final String COMPONENT = MDCanvasCommandbar.class.getSimpleName();

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(COMPONENT, "js/MDCanvasCommandbar.js"));
	}
	
    public String getClientComponentType(boolean shortType) {
        return COMPONENT;
    }
    
    @Override
    public Class getComponentClass() {
        return MDCanvasCommandbar.class;
    }

    @Override
    public void init(Context context, Component component) {
        super.init(context, component);
        ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
        serverMessage.addLibrary(COMPONENT);
    }
}
