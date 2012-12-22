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
import nextapp.echo.extras.app.RichTextArea;

/**
 * A modal editor for rich text
 * 
 * @author chrismay
 */
public class PDDialogRichText extends PDOkCancelDialog {

	private boolean editable;
	private RichTextArea richTextArea;
	private String newText;

	public PDDialogRichText(String title, boolean editable) {
		super(title, 617, 390);
		this.editable = editable;
		initGUI();
	}

	@Override
	protected Component getMainContainer() {
		richTextArea = new RichTextArea();
		return richTextArea;
	}

	public String getText() {
		return newText;
	}

	private void initGUI() {
		if (!editable) {
			btnOk.setVisible(false);
			btnCancel.setText("OK"); //$NON-NLS-1$
		}
		richTextArea.setEnabled(editable);
	}

	public void setText(String text) {
		richTextArea.setText(text);
	}

	@Override
	protected boolean onOkClicked() {
		if (editable) {
			newText = richTextArea.getText();
		}
		return true;
	}
}