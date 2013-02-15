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

	private List<IMessageListener> messageListeners = new CopyOnWriteArrayList<IMessageListener>();
	private TaskQueueHandle tqh;

	public void enqueueTask(Runnable r) {
		if (tqh == null) {
			tqh = createTaskQueue();
		}
		this.enqueueTask(tqh, r);
	}

	public void addListener(IMessageListener messageListener) {
		messageListeners.add(messageListener);
	}

	public static PDApplicationInstance getActivePD() {
		return (PDApplicationInstance) getActive();
	}
	
}
