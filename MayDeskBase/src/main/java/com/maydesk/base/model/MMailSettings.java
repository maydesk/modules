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
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Alejandro Salas <br>
 *         Created on Jul 3, 2006
 */
@Entity
@Table(name = "t_pdw_mail_settings")
public class MMailSettings extends MBase {

	private boolean auth = true;
	private String authPassword;
	private String authUser;
	private boolean debug = true;
	private boolean enabled = true;
	private String fromUser;
	private int port = 25;
	private String serverName;
	private int socketFactoryPort;
	private boolean secureSocket = false;
	private boolean starttls = true;
	private String testRecipient;

	public String getAuthPassword() {
		return authPassword;
	}

	public String getAuthUser() {
		return authUser;
	}

	public String getFromUser() {
		return fromUser;
	}

	public int getPort() {
		return port;
	}

	@Transient
	public String getPortStr() {
		return Integer.toString(port);
	}

	public String getServerName() {
		return serverName;
	}

	public int getSocketFactoryPort() {
		return socketFactoryPort;
	}

	@Transient
	public String getSocketFactoryPortStr() {
		return Integer.toString(socketFactoryPort);
	}

	public String getTestRecipient() {
		return testRecipient;
	}

	public boolean isAuth() {
		return auth;
	}

	public boolean isDebug() {
		return debug;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isSecureSocket() {
		return secureSocket;
	}

	public boolean isStarttls() {
		return starttls;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}

	public void setAuthUser(String authUser) {
		this.authUser = authUser;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setPortStr(String port) {
		try {
			this.port = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			this.port = 0;
		}
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public void setSocketFactoryPort(int socketFactoryPort) {
		this.socketFactoryPort = socketFactoryPort;
	}

	public void setSocketFactoryPortStr(String socketFactoryPort) {
		try {
			this.socketFactoryPort = Integer.parseInt(socketFactoryPort);
		} catch (NumberFormatException e) {
			this.socketFactoryPort = 0;
		}
	}

	public void setSecureSocket(boolean secureSocket) {
		this.secureSocket = secureSocket;
	}

	public void setStarttls(boolean starttls) {
		this.starttls = starttls;
	}

	public void setTestRecipient(String testRecipient) {
		this.testRecipient = testRecipient;
	}

}