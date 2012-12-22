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
import javax.persistence.Table;

/**
 * @author chrismay
 */
@Entity
@Table(name = "t_pdw_presence_acknowledge")
public class MPresenceAcknowledge extends MBase {

	private String jabberId;
	private MUser acknowledgedBy;
	private Date acknowledgeTime;

	public String getJabberId() {
		return jabberId;
	}

	public void setJabberId(String jabberId) {
		this.jabberId = jabberId;
	}

	public MUser getAcknowledgedBy() {
		return acknowledgedBy;
	}

	public void setAcknowledgedBy(MUser acknowledgedBy) {
		this.acknowledgedBy = acknowledgedBy;
	}

	public Date getAcknowledgeTime() {
		return acknowledgeTime;
	}

	public void setAcknowledgeTime(Date acknowledgeTime) {
		this.acknowledgeTime = acknowledgeTime;
	}

}