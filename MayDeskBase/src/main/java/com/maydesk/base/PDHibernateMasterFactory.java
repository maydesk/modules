/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/
package com.maydesk.base;

import java.io.Serializable;

import org.hibernate.Session;

import com.maydesk.base.util.CledaConnector;

/**
 * @author chrismay
 */
public class PDHibernateMasterFactory implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient Session hibernateSession;

	public Session getSession() {
		if (hibernateSession == null) {
			try {
				hibernateSession = CledaConnector.getInstance().createSession();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return hibernateSession;
	}

	public void closeSession() {
		if (hibernateSession == null)
			return;
		if (!hibernateSession.isOpen())
			return;
		try {
			hibernateSession.getTransaction().commit();
			hibernateSession.close();
		} catch (Exception e) {
			System.out.println("closeSession() failed: " + e.toString()); //$NON-NLS-1$
		}
	}

	public boolean hasOpenSession() {
		return hibernateSession != null;
	}
}
