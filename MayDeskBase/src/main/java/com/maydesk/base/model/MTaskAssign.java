/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

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