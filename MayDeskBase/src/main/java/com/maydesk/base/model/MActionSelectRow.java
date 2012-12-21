/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

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