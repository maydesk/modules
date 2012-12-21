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

import nextapp.echo.app.WindowPane;

/**
 * @author chrismay
 */
@Entity
public class MActionOpenWindow extends MAction {

	private WindowPane window;

	@Override
	public void redoAction() {
		window.setVisible(true);
		// PDDesktop.getDesktop().addWindow(window);
	}

	@Override
	public void undoAction() {
		window.userClose();
	}

	public void setWindow(WindowPane window) {
		this.window = window;
	}

	@Override
	public String toString() {
		return "Fenster geöffnet";
	}
}