/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "t_pwd_tenant_assignment")
public class MTenantAssignment extends MBase {
	
	private MUser userRef;
	private MTenant tenant;
	private boolean owner;
	private boolean isAssignmentActive;


	public boolean isAssignmentActive() {
		return isAssignmentActive;
	}

	public void setAssignmentActive(boolean isAssignmentActive) {
		this.isAssignmentActive = isAssignmentActive;
	}
	
	@ManyToOne(targetEntity = MUser.class) 
	public MUser getUserRef() {
		return userRef;
	}

	public void setUserRef(MUser userRef) {
		this.userRef = userRef;
	}

	@ManyToOne(targetEntity = MTenant.class, optional = false)
	public MTenant getTenant() {
		return tenant;
	}

	public void setTenant(MTenant tenant) {
		this.tenant = tenant;
	}

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}
}