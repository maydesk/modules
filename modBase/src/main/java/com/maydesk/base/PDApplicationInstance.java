/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/
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

/**
 * @author chrismay
 */
public abstract class PDApplicationInstance extends ApplicationInstance {

	protected Window window;
	protected String environment; 
	protected String project;
	
	
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
		tqh = ApplicationInstance.getActive().createTaskQueue();
		ContainerContext ctx = (ContainerContext) ApplicationInstance.getActive().getContextProperty(ContainerContext.CONTEXT_PROPERTY_NAME);
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
		ApplicationInstance.getActive().enqueueTask(tqh, r);
	}

	public void addListener(IMessageListener messageListener) {
		messageListeners.add(messageListener);
	}

	public static PDApplicationInstance getActivePD() {
		return (PDApplicationInstance) getActive();
	}

	public String getEnvironment() {
		return environment;
	}
	
	public String getProject() {
		return project;
	}
}
