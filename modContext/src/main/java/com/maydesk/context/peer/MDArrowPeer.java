package com.maydesk.context.peer;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.ContentType;
import nextapp.echo.webcontainer.ResourceRegistry;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.context.widget.MDArrow;

public class MDArrowPeer extends MDAbstractFigurePeer {

	private static final String COMPONENT = MDArrow.class.getSimpleName();

	static {
		WebContainerServlet.getServiceRegistry().add(JavaScriptService.forResource(COMPONENT, "js/figures/MDArrow.js"));
		ResourceRegistry resources = WebContainerServlet.getResourceRegistry();
		resources.addPackage(COMPONENT, "img/");
		resources.add(COMPONENT, "editor/back.gif", ContentType.IMAGE_GIF);
		resources.add(COMPONENT, "editor/forward.gif", ContentType.IMAGE_GIF);
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
}