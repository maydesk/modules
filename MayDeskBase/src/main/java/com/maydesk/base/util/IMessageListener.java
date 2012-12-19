package com.maydesk.base.util;

import org.hibernate.Session;

/**
 * @author chrismay
 */
public interface IMessageListener {

	public void doPoll(Session session);

}
