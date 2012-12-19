package com.maydesk.base.peers;

import nextapp.echo.app.Command;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractCommandSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.base.util.BrowserRedirectParentCommand;
import com.maydesk.base.util.PDUtil;

/**
 * Synchronization peer for <code>BrowserRedirectCommand</code>.
 * 
 * @author chrismay
 */
public class BrowserRedirectParentCommandPeer extends AbstractCommandSynchronizePeer {

	/** The associated client-side JavaScript module <code>Service</code>. */
	private static final Service BROWSER_REDIRECT_PARENT_SERVICE = JavaScriptService.forResource("Echo.BrowserRedirectParent", PDUtil.BASE_PATH + "js/PDBrowserRedirectParent.js");

	static {
		WebContainerServlet.getServiceRegistry().add(BROWSER_REDIRECT_PARENT_SERVICE);
	}

	/**
	 * Default constructor.
	 */
	public BrowserRedirectParentCommandPeer() {
		super();
		addProperty("uri", new AbstractCommandSynchronizePeer.PropertyPeer() {
			@Override
			public Object getProperty(Context context, Command command) {
				return ((BrowserRedirectParentCommand) command).getUri();
			}
		});
	}

	/**
	 * @see nextapp.echo.webcontainer.AbstractCommandSynchronizePeer#init(nextapp.echo.app.util.Context)
	 */
	@Override
	public void init(Context context) {
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(BROWSER_REDIRECT_PARENT_SERVICE.getId());
	}

	/**
	 * @see nextapp.echo.webcontainer.CommandSynchronizePeer#getCommandClass()
	 */
	@Override
	public Class getCommandClass() {
		return BrowserRedirectParentCommand.class;
	}
}
