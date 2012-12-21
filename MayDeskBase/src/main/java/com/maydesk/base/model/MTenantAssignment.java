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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author chrismay
 */
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