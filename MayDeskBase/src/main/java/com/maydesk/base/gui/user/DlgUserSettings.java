/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui.user;

import nextapp.echo.app.event.ActionEvent;

import org.jivesoftware.smackx.packet.VCard;

import com.maydesk.base.PDUserSession;
import com.maydesk.base.gui.PDOkCancelDialog;
import com.maydesk.base.model.MUser;

/**
 * @author Alejandro Salas <br>
 *         Created on Mar 21, 2007
 */
public class DlgUserSettings extends PDOkCancelDialog {

	private PnlUserForWizard pnlUser;
	private MUser user;
	private boolean editable;

	public DlgUserSettings(MUser user, boolean editable) {
		super("Profile data", 380, 300);
		this.user = user;
		this.editable = editable;

		pnlUser = new PnlUserForWizard(editable);
		pnlUser.readFromModel(user);
		addMainComponent(pnlUser);
		btnOk.setVisible(editable);
	}

	@Override
	protected boolean onOkClicked() {
		VCard vcard = pnlUser.createVCard();
		PDUserSession.getInstance().updateVCard(vcard);
		return true;
	}

	@Override
	protected void btnCancelClicked(ActionEvent evt) {
		if (editable) {
			// XXX : Why is the user being reloaded if nothing has changed???
			PDUserSession.getInstance().setUser((MUser) user.reload());
		}
		setVisible(false);
	}

}