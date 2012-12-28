/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui.user;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;

import org.jivesoftware.smackx.packet.VCard;

import com.maydesk.base.PDUserSession;
import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MDataLink;
import com.maydesk.base.model.MUser;
import com.maydesk.base.sop.enums.ECountry;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.sop.gui.StandardTerms;
import com.maydesk.base.sop.logical.SopUser;
import com.maydesk.base.util.ICrud;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDCombo;
import com.maydesk.base.widgets.PDGrid;
import com.maydesk.base.widgets.PDLabel;
import com.maydesk.base.widgets.PDTextField;

/**
 * @author chrismay
 */
public class PnlUserForWizard extends PDGrid implements ICrud<MUser> {

	private PDTextField txtJabberId;
	private PDTextField txtFirstName;
	private PDTextField txtLastName;
	private PDTextField txtOrganization;
	private PDCombo<ECountry> cboCountry;
	private PDTextField txtZipCode;
	private PDTextField txtCity;
	private PDTextField txtEmail;
	private VCard vcard;
	private boolean editable;

	public PnlUserForWizard() {
		this(true);
	}

	public PnlUserForWizard(boolean editable) {
		super(2);
		this.editable = editable;
		initGUI();
		setEditing(editable);
	}

	protected void initGUI() {
		PDLabel lbl = addLabel(SopUser.jabberId);
		lbl.setText(lbl.getText());
		txtJabberId = new PDTextField();
		txtJabberId.setEnabled(false);
		addFill(txtJabberId);

		lbl = addLabel(SopUser.firstName);
		lbl.setText(lbl.getText());
		txtFirstName = new PDTextField();
		addFill(txtFirstName);

		lbl = addLabel(SopUser.lastName);
		lbl.setText(lbl.getText());
		txtLastName = new PDTextField();
		addFill(txtLastName);

		addLabel(SopUser.organization);
		addFill(txtOrganization = new PDTextField());

		addLabel(SopUser.zipCode);
		txtZipCode = new PDTextField();
		txtZipCode.setMaximumLength(5);
		txtZipCode.setWidth(new Extent(100));
		add(txtZipCode);

		addLabel(SopUser.city);
		txtCity = new PDTextField();
		addFill(txtCity);

		addLabel(SopUser.country);
		cboCountry = new PDCombo<ECountry>(ECountry.values(), StandardTerms.EMPTY, false);
		addFill(cboCountry);

		lbl = addLabel(SopUser.email);
		txtEmail = new PDTextField();
		addFill(txtEmail);

		if (editable) {
			lbl.setText(lbl.getText() + "*"); // email is mandatory
			lbl = new PDLabel(PDBeanTerms.Star_is_mandatory, PDLabel.STYLE.ANNOTATION);
			addFill(lbl);
		}
	}

	public VCard createVCard() {
		if (vcard == null) {
			vcard = new VCard();
		}
		vcard.setFirstName(txtFirstName.getText());
		vcard.setLastName(txtLastName.getText());
		vcard.setOrganization(txtOrganization.getText());
		vcard.setAddressFieldHome("PCODE", txtZipCode.getText());
		vcard.setAddressFieldHome("LOCALITY", txtCity.getText());
		if (cboCountry.getSelectedItem() != null) {
			vcard.setAddressFieldHome("CTRY", cboCountry.getSelectedItem().name());
		}
		vcard.setEmailHome(txtEmail.getText());
		return vcard;
	}

	@Override
	public Component getFocusComponent() {
		return txtFirstName;
	}

	public Translatable getError() {
		if (PDUtil.isEmpty(txtEmail.getText())) {
			return PDBeanTerms.Please_specify_an_email;
		}
		if (!PDUtil.isValidEmailAddress(txtEmail.getText())) {
			return PDBeanTerms.Invalid_email_address;
		}
		return null;
	}

	@Override
	public void readFromModel(MBase model) {
		MUser user = null;
		if (model instanceof MDataLink) {
			MDataLink dl = (MDataLink) model;
			user = MBase.loadById(MUser.class, dl.getTargetId());
		} else {
			user = (MUser) model;
		}
		vcard = PDUserSession.getInstance().getVCard(user.getJabberId());
		txtJabberId.setText(user.getJabberId());
		txtFirstName.setText(vcard.getFirstName());
		txtLastName.setText(vcard.getLastName());
		txtOrganization.setText(vcard.getOrganization());
		txtZipCode.setText(vcard.getAddressFieldHome("PCODE"));
		txtCity.setText(vcard.getAddressFieldHome("LOCALITY"));
		String country = vcard.getAddressFieldHome("CTRY");
		cboCountry.setSelectedIndex(0);
		if (!PDUtil.isEmpty(country)) {
			try {
				ECountry c = ECountry.valueOf(country);
				cboCountry.setSelectedItem(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		txtEmail.setText(vcard.getEmailHome());
	}

	public void setEditing(boolean isEditing) {
		txtFirstName.setEnabled(isEditing);
		txtLastName.setEnabled(isEditing);
		txtOrganization.setEnabled(isEditing);
		cboCountry.setEnabled(isEditing);
		txtZipCode.setEnabled(isEditing);
		txtCity.setEnabled(isEditing);
		txtEmail.setEnabled(isEditing);
	}

	@Override
	public Class getModelClass() {
		return MUser.class;
	}
}