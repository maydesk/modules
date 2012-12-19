/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
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
