/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.TaskQueueHandle;
import nextapp.echo.app.Window;
import nextapp.echo.webcontainer.ContainerContext;

import org.hibernate.Session;

import com.maydesk.base.util.IMessageListener;

public abstract class PDApplicationInstance extends ApplicationInstance {

	protected Window window;
	
	public void reset() {
		window.setContent(getDesktop());
    }
	
	@Override
    public Window init() {
		Locale.setDefault(Locale.GERMAN);
		window = new Window();
		window.setTitle(getTitle());
		window.setContent(getDesktop());
        return window;
    }

    protected abstract PDDesktop getDesktop();
    protected abstract String getTitle();


    private List<IMessageListener> messageListeners = new CopyOnWriteArrayList<IMessageListener>();
    private TaskQueueHandle tqh;

	public void startPoller() {
		tqh = PDApplicationInstance.getActive().createTaskQueue();
		ContainerContext ctx = (ContainerContext)PDApplicationInstance.getActive().getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
		ctx.setTaskQueueCallbackInterval(tqh, 1000);	
		startMessagePolling();
	}
	
	private void startMessagePolling() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				PDHibernateMasterFactory factory = new PDHibernateMasterFactory();
				Session session = factory.getSession();
				for (IMessageListener messageListener : messageListeners) {
					messageListener.doPoll(session);
				}				
				factory.closeSession();
				startMessagePolling();
			}
		};
		PDApplicationInstance.getActive().enqueueTask(tqh, r);
	}

	public void addListener(IMessageListener messageListener) {
		messageListeners.add(messageListener);
	}

	public static PDApplicationInstance getActivePD() {
	    return (PDApplicationInstance)getActive();
    }
}
