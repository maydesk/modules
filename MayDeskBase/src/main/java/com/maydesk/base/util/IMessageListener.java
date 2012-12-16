package com.maydesk.base.util;

import org.hibernate.Session;



public interface IMessageListener {

	public void doPoll(Session session);

}
