/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author chrismay
 */
@Entity
@Table(name = "t_pdw_browser_session")
public class MBrowserSession extends MBase {

	private Calendar startupTime;
	private String userAgent;
	private String remoteHost;
	private int utcOffset;
	private int screenWidth;
	private int screenHight;

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public int getScreenHight() {
		return screenHight;
	}

	public void setScreenHight(int screenHight) {
		this.screenHight = screenHight;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public Calendar getStartupTime() {
		return startupTime;
	}

	public void setStartupTime(Calendar startupTime) {
		this.startupTime = startupTime;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public int getUtcOffset() {
		return utcOffset;
	}

	public void setUtcOffset(int utcOffset) {
		this.utcOffset = utcOffset;
	}
}