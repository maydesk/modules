/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

import javax.persistence.Entity;

/**
 * @author chrismay
 */
@Entity
public class MActionGeneric extends MAction {

	@Override
	public void redoAction() {
	}

	@Override
	public void undoAction() {
	}
}