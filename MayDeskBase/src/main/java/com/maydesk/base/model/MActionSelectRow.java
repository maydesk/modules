/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

import javax.persistence.Entity;

import com.maydesk.base.gui.PDMasterDataView;

/**
 * @author chrismay
 */
@Entity
public class MActionSelectRow extends MAction {

	private PDMasterDataView masterDataView;

	@Override
	public void redoAction() {
		masterDataView.setSelectedRow(getNewIntValue());
	}

	@Override
	public void undoAction() {
		masterDataView.setSelectedRow(getOldIntValue());
	}

	public void setMasterDataView(PDMasterDataView masterDataView) {
		this.masterDataView = masterDataView;
	}

	@Override
	public String toString() {
		return "Datensatz angewählt";
	}
}