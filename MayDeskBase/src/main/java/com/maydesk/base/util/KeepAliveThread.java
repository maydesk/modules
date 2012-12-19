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
