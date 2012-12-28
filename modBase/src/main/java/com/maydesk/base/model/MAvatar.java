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
import javax.persistence.Transient;

import com.maydesk.base.dao.DaoUser;

/**
 * @author chrismay
 */
@Entity
@Table(name = "t_pdw_avatar")
public class MAvatar extends MBase {

	private MUser owner;
	private MUser person;
	private int positionX;
	private int positionY;

	public void executeTask() {
		executeTaskInternal();
	}

	protected void executeTaskInternal() {
	}

	@ManyToOne(targetEntity = MUser.class, optional = false)
	public MUser getOwner() {
		return owner;
	}

	@ManyToOne(targetEntity = MUser.class, optional = false)
	public MUser getPerson() {
		return person;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	@Transient
	public String getTitle() {
		return person.toString();
	}

	public void setOwner(MUser owner) {
		this.owner = owner;
	}

	public void setPerson(MUser person) {
		this.person = person;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	@Override
	public String toString() {
		return getTitle();
	}

	public void acknowledgeStatus() {
		DaoUser dao = new DaoUser();
		dao.acknowledgeStatus(person);
	}
}