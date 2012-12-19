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
@Table(name = "t_pdw_presence_acknowledge")
public class MPresenceAcknowledge extends MBase {

	private String jabberId;
	private MUser acknowledgedBy;
	private Date acknowledgeTime;

	public String getJabberId() {
		return jabberId;
	}

	public void setJabberId(String jabberId) {
		this.jabberId = jabberId;
	}

	public MUser getAcknowledgedBy() {
		return acknowledgedBy;
	}

	public void setAcknowledgedBy(MUser acknowledgedBy) {
		this.acknowledgedBy = acknowledgedBy;
	}

	public Date getAcknowledgeTime() {
		return acknowledgeTime;
	}

	public void setAcknowledgeTime(Date acknowledgeTime) {
		this.acknowledgeTime = acknowledgeTime;
	}

}