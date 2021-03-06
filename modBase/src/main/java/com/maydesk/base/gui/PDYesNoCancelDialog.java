/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui;

import nextapp.echo.app.Component;
import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.sop.gui.StandardTerms;
import com.maydesk.base.widgets.PDPushButton;

/**
 * @author Chris May
 */
public abstract class PDYesNoCancelDialog extends PDOkCancelDialog {

	private Label lbl;

	public PDYesNoCancelDialog(String title, String text) {
		super(title, 300, 150);
		lbl.setText(text);

		PDPushButton btnNo = new PDPushButton(StandardTerms.No);
		btnNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnNoClicked(evt);
			}
		});
		rowCommands.add(btnNo);

		btnOk.setText("Yes");
	}

	protected void btnNoClicked(ActionEvent evt) {
		if (!onNoClicked())
			return;
		fireActionEvent(new ActionEvent(this, null));
		setVisible(false);
	}

	protected abstract boolean onNoClicked();

	@Override
	protected void btnCancelClicked(ActionEvent evt) {
		if (!onCancelClicked())
			return;
		setVisible(false);
	}

	protected abstract boolean onCancelClicked();

	@Override
	protected Component getMainContainer() {
		lbl = new Label();
		return lbl;
	}

}