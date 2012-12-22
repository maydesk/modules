/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui;

import nextapp.echo.app.TextField;

import com.maydesk.base.PDDesktop;

/**
 * A model box displaying a simple message
 * 
 * @author Alejandro Salas
 */
public class PDInputBox extends PDOkCancelDialog {

	public static PDInputBox inputBox(String title, String msg) {
		PDInputBox box = new PDInputBox(title, msg, 300, 150);
		PDDesktop.getInstance().addWindow(box);
		return box;
	}

	private String value;
	private TextField txt;

	public PDInputBox(String title, String msg, int w, int h) {
		super(title, w, h);
		setModal(true);

		txt = new TextField();
		addMainComponent(txt);
	}

	public void setValue(String value) {
		txt.setText(value);
	}

	public String getValue() {
		return value;
	}

	@Override
	protected boolean onOkClicked() {
		value = txt.getText();
		return true;
	}
}
