/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_pdw_mnemonic")
public class MMnemonic extends MBase {

	private int position;
	private int serialId;
	private MUser userRef;
	
	public int getPosition() {
    	return position;
    }

	public int getSerialId() {
    	return serialId;
    }

	@ManyToOne(targetEntity = MUser.class, optional = false)
	public MUser getUserRef() {
		return userRef;
	}

	public void setPosition(int position) {
    	this.position = position;
    }
	
	public void setSerialId(int serialId) {
    	this.serialId = serialId;
    }
	
	public void setUserRef(MUser userRef) {
		this.userRef = userRef;
	}	
}
