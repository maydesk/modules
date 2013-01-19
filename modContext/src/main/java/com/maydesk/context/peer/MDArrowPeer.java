package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.context.widget.MDArrow;

public class MDArrowPeer extends Draw2dAbstractPeer {

	private static final String MD_ARROW = MDArrow.class.getSimpleName();

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(MD_ARROW, "js/MDArrow.js"));
	}
	
    public String getClientComponentType(boolean shortType) {
        return MD_ARROW;
    }
    
    @Override
    public Class getComponentClass() {
        return MDArrow.class;
    }

    @Override
    public void init(Context context, Component component) {
        super.init(context, component);
        ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
        serverMessage.addLibrary(MD_ARROW);
    }
}
