/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author chrismay
 */
@Entity
@Table(name = "t_pdw_presence")
public class MPresence extends MBase {

	private String jabberId;
	private String status;
	private Date time;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public MPresence() {
		// Empty
	}

	public String getJabberId() {
		return jabberId;
	}

	public String getStatus() {
		return status;
	}

	public void setJabberId(String jabberId) {
		this.jabberId = jabberId;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}