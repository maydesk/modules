package com.maydesk.context.peer;


import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.Service;

public abstract class Draw2dAbstractPeer extends AbstractComponentSynchronizePeer {

	/** Register the core services */
	static {
		MDCommonResources.install();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#init
	 */
	@Override
	public void init(final Context context, final Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		for (Service service : MDCommonService.SERVICES) {
			serverMessage.addLibrary(service.getId());
		}
	}
}
