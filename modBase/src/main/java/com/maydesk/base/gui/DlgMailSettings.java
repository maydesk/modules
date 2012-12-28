/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui;

import javax.mail.AuthenticationFailedException;

import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.util.MDMailUtil;
import com.maydesk.base.widgets.PDButton;
import com.maydesk.base.widgets.PDGrid;
import com.maydesk.base.widgets.PDLabel;
import com.maydesk.base.widgets.PDTextField;

/**
 * A dialog to set the server mail settings
 * 
 * @author Daniel Created on Jun 20, 2007
 */
public class DlgMailSettings extends PDOkCancelDialog {

	private PDTextField txtFrom;
	private PDTextField txtTestRecipient;

	public DlgMailSettings() {
		super(PDBeanTerms.Mail_Setup, 350, 410);
		initGUI();
	}

	@Override
	protected boolean onOkClicked() {
		return true;
	}

	private void btnTestClicked() {
		try {
			MDMailUtil.sendMail(txtTestRecipient.getText(), "Test", "Testmail", null);
		} catch (AuthenticationFailedException afe) {
			PDMessageBox.msgBox("Mail Error", "Authentication failed!", 280, 150);
			afe.printStackTrace();
			return;
		} catch (Exception e) {
			PDMessageBox.msgBox("Mail Error", e.getMessage(), 400, 220);
			e.printStackTrace();
			return;
		}
		PDMessageBox.msgBox("Mail sent", "A test mail has been succesfully sent to " + txtTestRecipient.getText(), 200, 200);
	}

	protected void initGUI() {
		PDGrid grid = new PDGrid(2);
		addMainComponent(grid);

		grid.add(new PDLabel(PDBeanTerms.From));
		txtFrom = new PDTextField(getClass().getSimpleName() + "txtFrom");
		grid.add(txtFrom);

		grid.add(new PDLabel(PDBeanTerms.Test_recipient));
		txtTestRecipient = new PDTextField(getClass().getSimpleName() + "txtTestRecipient");
		grid.add(txtTestRecipient);

		grid.add(new Label(""));
		PDButton btnTest = new PDButton("Test Mail");
		btnTest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnTestClicked();
			}
		});
		grid.add(btnTest);

		grid.add(new Label(""));
		PDButton btnException = new PDButton("Test Exception");
		btnException.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// test exception handler, a dialog should appear, and an email sent to the admin
				throw new IllegalArgumentException("Test exception");
			}
		});
		grid.add(btnException);
	}
}
