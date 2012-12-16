/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_pdw_chat_history")
public class MChatHistory extends MBase {

	private Date lastActivity;
	private String otherUser;
	private MUser user;
	
	public String getOtherUser() {
    	return otherUser;
    }

	public void setOtherUser(String otherUser) {
    	this.otherUser = otherUser;
    }
	
	public Date getLastActivity() {
    	return lastActivity;
    }
	
	public MUser getUser() {
    	return user;
    }
	
	public void setLastActivity(Date lastActivity) {
    	this.lastActivity = lastActivity;
    }
	
	public void setUser(MUser user) {
    	this.user = user;
    }	
}