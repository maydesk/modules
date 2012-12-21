/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.util;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import com.maydesk.base.model.MUser;

/**
 * Make sure te database connections do not time out
 * @author chrismay
 */
public class KeepAliveThread {

	public static void startThread() {
		Thread tt = new Thread() {
			@Override
			public void run() {
				System.out.println("Keep-alive thread started");
				int count = 0;
				while (true) {
					Session session = null;
					try {
						Thread.sleep(60000);
						session = CledaConnector.getInstance().createSession();
						Criteria criteria = session.createCriteria(MUser.class);
						criteria.setProjection(Projections.rowCount());
						criteria.uniqueResult();
						session.getTransaction().commit();
					} catch (Exception e) {
						if (count % 60 == 0) {
							e.printStackTrace();
						}
					} finally {
						if (session != null) {
							try {
								if (session.getTransaction() != null && session.getTransaction().isActive() && !session.getTransaction().wasCommitted()) {
									session.getTransaction().rollback();
								}
								session.close();
							} catch (Exception e) {
								System.out.println("WARN: closeSession " + e.getMessage());
							}
						}
					}
					if (count % 600 == 0) {
						System.out.println("tic...");
					}
					count++;
				}
			}
		};
		tt.start();
	}
}
