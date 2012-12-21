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
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.maydesk.base.util.PDFormat;

/**
 * @author Alejandro Salas <br>
 *         Created on Feb 7, 2007
 */
@Entity
@Table(name = "t_pdw_activity2")
public class MActivity extends MBase {

	private String activityString;
	private int contextId;
	private String contextText;
	private Date date;
	private String newValue;
	private String oldValue;
	private String textEnglish;
	private String textGerman;
	private String userLogin;
	private int userId;
	private MBrowserSession browserSession;

	public MActivity() {
	}

	public String getActivityString() {
		return activityString;
	}

	public int getContextId() {
		return contextId;
	}

	public String getContextText() {
		return contextText;
	}

	public Date getDate() {
		return date;
	}

	@Transient
	public String getDateStr() {
		return PDFormat.getDefaultDateTimeFormat().format(date.getTime());
	}

	public String getDescription(Locale locale) {
		return Locale.GERMAN == locale ? getTextGerman() : getTextEnglish();
	}

	public String getNewValue() {
		return newValue;
	}

	public String getOldValue() {
		return oldValue;
	}

	public String getTextEnglish() {
		return textEnglish;
	}

	public String getTextGerman() {
		return textGerman;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setActivityString(String actityString) {
		this.activityString = actityString;
	}

	public void setContextId(int contextId) {
		this.contextId = contextId;
	}

	public void setContextText(String context) {
		this.contextText = context;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public void setTextEnglish(String textEnglish) {
		this.textEnglish = textEnglish;
	}

	public void setTextGerman(String textGerman) {
		this.textGerman = textGerman;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public MBrowserSession getBrowserSession() {
		return browserSession;
	}

	public void setBrowserSession(MBrowserSession browserSession) {
		this.browserSession = browserSession;
	}

	@Column(name = "user_ref")
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}