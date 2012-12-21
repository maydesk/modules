/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui;

import static com.maydesk.base.util.SopletsResourceBundle.nls;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.sop.gui.StandardTerms;

/**
 * A model box displaying a simple message
 * 
 * @author Alejandro Salas
 */
public class PDMessageBox extends PDOkCancelDialog {

	public static PDMessageBox confirmDeletion(String objectDescription) {
		return confirmMsgBox(PDBeanTerms.Really_delete_object_x, objectDescription);
	}

	public static PDMessageBox confirmDeletion() {
		return confirmMsgBox(PDBeanTerms.Really_delete_object);
	}

	public static PDMessageBox confirmMsgBox(Translatable msg, Object... param) {
		PDMessageBox box = new PDMessageBox(StandardTerms.Warning, msg, 300, 165, param);
		PDDesktop.getInstance().addWindow(box);
		return box;
	}

	public static PDMessageBox msgBox(Translatable title, Translatable text, int w, int h, Object... params) {
		return msgBox(nls(title), nls(text, params), w, h);
	}

	@Deprecated
	public static PDMessageBox msgBox(String title, String text, int w, int h) {
		try {
			PDMessageBox box = new PDMessageBox(title, text, w, h);
			box.btnCancel.setVisible(false);
			PDDesktop.getInstance().addWindow(box);
			return box;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String msg;

	public PDMessageBox() {
		super("Info", 400, 200);
		setModal(true);
	}

	@Deprecated
	public PDMessageBox(String title, String text, int w, int h) {
		super(title, w, h);
		this.msg = text;
		setResizable(false);
		setModal(true);
		initGUI();
	}

	public PDMessageBox(Translatable title, Translatable msg, int w, int h, Object... param) {
		super(title, w, h);
		this.msg = nls(msg, param);
		setResizable(false);
		setModal(true);
		initGUI();
	}

	private void initGUI() {
		Label lblText = new Label(msg);
		lblText.setLineWrap(true);
		addMainComponent(lblText);
	}

	@Override
	protected boolean onOkClicked() {
		return true;
	}

	public void info(String text) {
		msg = text;
		Label lblText = new Label(msg);
		lblText.setIcon(new ResourceImageReference("img/Icon_Info_64.gif"));
		lblText.setLineWrap(true);
		addMainComponent(lblText);
		PDDesktop.getInstance().addWindow(this);
	}
}
