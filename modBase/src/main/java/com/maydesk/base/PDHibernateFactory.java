/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/
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
		PDHibernateMasterFactory factory = (PDHibernateMasterFactory) instance.getContextProperty(MDServlet.HIBERNATE_FACTORY);
		if (factory == null) {
			// might be the case for Home server
			ContainerContext context = (ContainerContext) instance.getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
			factory = (PDHibernateMasterFactory) context.getSession().getAttribute(MDServlet.HIBERNATE_FACTORY);
		}
		return factory.getSession();
	}

	public static void doCommit() {
		getSession().getTransaction().commit();
		getSession().beginTransaction();
	}
}