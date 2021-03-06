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

import com.maydesk.base.sop.gui.StandardTerms;

/**
 * @author Alejandro Salas
 */
public class PDSimpleDialog extends PDOkCancelDialog {

	public PDSimpleDialog(String title, int w, int h) {
		super(title, w, h);
		btnOk.setText(nls(StandardTerms.Close));
		btnCancel.setVisible(false);
		setModal(false);
	}

	@Override
	protected boolean onOkClicked() {
		return true;
	}
}
