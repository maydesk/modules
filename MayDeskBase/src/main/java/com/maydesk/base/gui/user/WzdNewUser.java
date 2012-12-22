/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui.user;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import nextapp.echo.app.CheckBox;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.jivesoftware.smackx.packet.VCard;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.gui.PDCheckBox;
import com.maydesk.base.gui.PDWizard;
import com.maydesk.base.gui.PDWizardPanel;
import com.maydesk.base.model.MMediaFile;
import com.maydesk.base.model.MUser;
import com.maydesk.base.sop.enums.SopGender;
import com.maydesk.base.sop.enums.SopMood;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.sop.gui.StandardTerms;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDCombo;
import com.maydesk.base.widgets.PDGrid;
import com.maydesk.base.widgets.PDLabel;
import com.maydesk.base.widgets.PDPasswordField;
import com.maydesk.base.widgets.PDTextField;

import echopoint.HtmlLabel;
import echopoint.Strut;

/**
 * A wizard where a user can register to the system and/or complete his
 * registration
 * 
 * @author chrismay
 */
public class WzdNewUser extends PDWizard {

	private MUser user;
	private String textWelcome;
	private String textFinished;
	private String licenseText;
	private PDCheckBox chkAvatar;
	private VCard vcard;
	private PDPasswordField txtPassword1;

	public WzdNewUser() {
		this(new MUser());
	}

	public WzdNewUser(MUser user) {
		this(user, "Follow the next steps in order to register to Jabber and CloudDesk in one single operation", "Congratulations, you have succesfully registered to CloudDesk!");
	}

	public WzdNewUser(MUser user, String textWelcome, String textFinished) {
		super(null);
		this.user = user;
		this.textWelcome = textWelcome;
		this.textFinished = textFinished;
		setHeight(new Extent(380));
		setWidth(new Extent(400));
		setTitle("New User Registration");
		licenseText = getLicenseText();

		addPanel(new Panel1());
		if (licenseText != null) {
			addPanel(new Panel1b());
		}
		addPanel(new Panel2());
		addPanel(new Panel3());
		addPanel(new Panel4());
		showPage(true);
	}

	class Panel1 extends PDWizardPanel {
		public Panel1() {
			super(StandardTerms.Welcome, null, StandardTerms.Next);
			setInfo(textWelcome);
		}
	}

	class Panel1b extends PDWizardPanel {

		private CheckBox chkAgree;

		public Panel1b() {
			super(StandardTerms.License, StandardTerms.Back, StandardTerms.Next);

			add(new PDLabel("(You need to scroll down and click on the 'I agree to the terms' checkbox)", PDLabel.STYLE.ANNOTATION));

			HtmlLabel txtLicense = new HtmlLabel();
			txtLicense.setText(licenseText);
			txtLicense.setHeight(new Extent(150));
			add(txtLicense);

			chkAgree = new CheckBox("I agree to the terms");
			add(chkAgree);
		}

		@Override
		public Translatable getError() {
			if (!chkAgree.isSelected()) {
				return PDBeanTerms.You_must_agree_to_the_terms;
			}
			return null;
		}
	}

	class Panel2 extends PDWizardPanel {
		private PnlUserForWizard pnlUser;

		public Panel2() {
			super(StandardTerms.Back, StandardTerms.Next);

			String s = "Personal data";
			setInfo(s);

			pnlUser = new PnlUserForWizard();
			add(pnlUser);
		}

		@Override
		public Component getFocusComponent() {
			return pnlUser.getFocusComponent();
		}

		@Override
		public void applyToModel() {
			vcard = pnlUser.createVCard();
		}

		@Override
		public Translatable getError() {
			return pnlUser.getError();
		}
	}

	class Panel3 extends PDWizardPanel {

		private PDGrid grid;
		private PDTextField txtLogin;
		private PDPasswordField txtPassword2;
		private PDCombo<String> cboServer;
		private PDLabel lblJabberId;

		public Panel3() {
			super(StandardTerms.Back, PDBeanTerms.Register_Now);

			setInfo(PDBeanTerms.Please_select_a_login_and_a_password);

			grid = new PDGrid(2);
			add(grid);

			grid.addLabel("User Name");

			txtLogin = new PDTextField();
			txtLogin.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					loginChanged();
				}
			});
			grid.add(txtLogin);

			grid.addLabel("Jabber Server");
			String[] servers = new String[] { "jabber.de", "xmpp-hosting.de", "xabber.de" };
			cboServer = new PDCombo<String>(servers);
			cboServer.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					loginChanged();
				}
			});
			grid.add(cboServer);

			grid.addLabel(PDBeanTerms.Your_Jabber_ID);
			lblJabberId = new PDLabel("@jabber.org", PDLabel.STYLE.FIELD_BORDERED);
			grid.add(lblJabberId);

			grid.addLabel(StandardTerms.Password);
			txtPassword1 = new PDPasswordField();
			txtPassword1.setMaximumLength(15);
			grid.add(txtPassword1);

			grid.addLabel(PDBeanTerms.Password_repeat);
			txtPassword2 = new PDPasswordField();
			txtPassword2.setMaximumLength(15);
			grid.add(txtPassword2);
		}

		@Override
		public int getNextButtonWidth() {
			return 90;
		}

		@Override
		public int getBackButtonWidth() {
			return 70;
		}

		private void loginChanged() {
			lblJabberId.setText(txtLogin.getText().toLowerCase() + "@" + cboServer.getSelectedItem());
		}

		@Override
		public Component getFocusComponent() {
			return txtLogin;
		}

		@Override
		public void readFromModel() {
			txtLogin.setText(user.getEmail());
		}

		@Override
		public void applyToModel2() {
			user.setJabberId(txtLogin.getText().toLowerCase() + "@" + cboServer.getSelectedItem());
			user.setCachedTitle(user.getJabberId());
			PDHibernateFactory.getSession().save(user);
		}

		@Override
		public Translatable getError() {
			if (PDUtil.isEmpty(txtPassword1.getText())) {
				return PDBeanTerms.Please_select_password;
			}
			if (!txtPassword1.getText().equals(txtPassword2.getText())) {
				return PDBeanTerms.Passwords_do_not_match;
			}

			Translatable error = PDUserSession.getInstance().registerUser(txtLogin.getText(), cboServer.getSelectedItem(), txtPassword1.getText(), vcard);
			return error;
		}
	}

	class Panel4 extends PDWizardPanel {

		public Panel4() {
			super(null, StandardTerms.Done);
			String s = textFinished;
			setInfo(s);

			add(new Strut(0, 16));

			chkAvatar = new PDCheckBox("Upload Avatar image now");
			chkAvatar.setSelected(true);
			add(chkAvatar);
		}

		@Override
		public Component getFocusComponent() {
			return chkAvatar;
		}

		@Override
		public void applyToModel() {
			createDefaultAvatar();
			PDUserSession.getInstance().doLogin(user.getJabberId(), txtPassword1.getText());
			if (chkAvatar.isSelected()) {
				PDDesktop.getInstance().addWindow(new FrmAvatar());
			}
		}
	}

	private void createDefaultAvatar() {
		try {
			MMediaFile file = new MMediaFile();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			if (user.getGender() == SopGender.female) {
				new ResourceImageReference("img/silhouette-female.gif").render(baos);
			} else {
				new ResourceImageReference("img/silhouette-male.gif").render(baos);
			}
			file.setContentType("image/gif");
			file.setFileBytes(baos.toByteArray());
			file.setFileName(SopMood.normal.name());
			file.setParentId(user.getId());
			PDHibernateFactory.getSession().save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getLicenseText() {
		return null;
	}
}