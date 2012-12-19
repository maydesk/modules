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