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
@Table(name = "t_pdw_presence")
public class MPresence extends MBase {

	private String jabberId;
	private String status;
	private Date time;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public MPresence() {
		// Empty
	}

	public String getJabberId() {
		return jabberId;
	}

	public String getStatus() {
		return status;
	}

	public void setJabberId(String jabberId) {
		this.jabberId = jabberId;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}