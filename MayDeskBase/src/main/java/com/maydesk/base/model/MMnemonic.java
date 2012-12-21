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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author chrismay
 */
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
