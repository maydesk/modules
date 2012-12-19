/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.webcontainer.ContainerContext;

import org.hibernate.Session;

/**
 * Factory class for the hibernate session for GUI triggered activities
 * 
 * This class implements the open-session-in-a-view pattern, see also
 * https://community.jboss.org/wiki/OpenSessionInView?_sscc=t
 * 
 * In the @see PDServlet class a PDHibernateFactory instance is created at the
 * beginning of each request from the browser. The factory returns a singleton
 * hibernate session instance during the whole duration of the current request.
 * Only when the request is going to finish is this session commited and closed.
 * 
 * In case no hibernate/database interaction takes place at all during that
 * request, no session is created (and closed) either.
 * 
 * @author chrismay
 */
public abstract class PDHibernateFactory {

	public static Session getSession() {
		ApplicationInstance instance = ApplicationInstance.getActive();
		PDHibernateMasterFactory factory = (PDHibernateMasterFactory) instance.getContextProperty(PDServlet.HIBERNATE_FACTORY);
		if (factory == null) {
			// might be the case for Home server
			ContainerContext context = (ContainerContext) instance.getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
			factory = (PDHibernateMasterFactory) context.getSession().getAttribute(PDServlet.HIBERNATE_FACTORY);
		}
		return factory.getSession();
	}

	public static void doCommit() {
		getSession().getTransaction().commit();
		getSession().beginTransaction();
	}
}