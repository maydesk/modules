/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author mail@chrismay.de
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "t_pdw_task")
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public class MTask<T> extends MBaseWithTitle {

	protected MUser createdBy;
	private Date createdDate;
	private MUser doneBy;
	protected Date doneDate;
	protected int modelId;
	protected MTenant tenant;

	public MTask() {
		// Empty
	}

	public void executeTask() {
		executeTaskInternal();
	}

	protected void executeTaskInternal() {
	}

	@ManyToOne(targetEntity = MUser.class, optional = false)
	public MUser getCreatedBy() {
		return createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	@ManyToOne(targetEntity = MUser.class, optional = true)
	public MUser getDoneBy() {
		return doneBy;
	}

	public Date getDoneDate() {
		return doneDate;
	}

	public int getModelId() {
		return modelId;
	}

	public void setCreatedBy(MUser createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setDoneBy(MUser doneBy) {
		this.doneBy = doneBy;
	}

	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	@ManyToOne(targetEntity = MTenant.class)
	public MTenant getTenant() {
		return tenant;
	}

	public void setTenant(MTenant tenant) {
		this.tenant = tenant;
	}

	@Transient
	public String getName() {
		return null;
	}

	@Transient
	public String getDescription() {
		return null;
	}

	@Override
	public String createCachedTitle() {
		// TODO Auto-generated method stub
		return null;
	}

}