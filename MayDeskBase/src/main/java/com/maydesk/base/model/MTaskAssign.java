/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author chrismay
 */
@Entity
@Table(name = "t_pdw_task_assign"
/* uniqueConstraints = @UniqueConstraint(columnNames={"user_ref", "task"}) */)
public class MTaskAssign extends MBase {

	private MUser userRef;
	private MTask task;
	protected Date executeTill;
	private String comment;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getExecuteTill() {
		return executeTill;
	}

	public void setExecuteTill(Date executeTill) {
		this.executeTill = executeTill;
	}

	@ManyToOne(targetEntity = MUser.class, optional = true)
	public MUser getUserRef() {
		return userRef;
	}

	public void setUserRef(MUser userRef) {
		this.userRef = userRef;
	}

	@ManyToOne(targetEntity = MTask.class, optional = false)
	public MTask getTask() {
		return task;
	}

	public void setTask(MTask task) {
		this.task = task;
	}
}