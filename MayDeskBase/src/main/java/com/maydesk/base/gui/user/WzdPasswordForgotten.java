/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui.user;

import static com.maydesk.base.util.KIT_CONST.CRLF;
import static com.maydesk.base.util.KIT_CONST.Comma;
import static com.maydesk.base.util.KIT_CONST.Space;
import static com.maydesk.base.util.SopletsResourceBundle.nls;

import java.util.List;
import java.util.Vector;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.dao.DaoUser;
import com.maydesk.base.gui.PDWizard;
import com.maydesk.base.gui.PDWizardPanel;
import com.maydesk.base.model.MUser;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.sop.gui.StandardTerms;
import com.maydesk.base.sop.logical.SopUser;
import com.maydesk.base.util.PDMailBean;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDGrid;
import com.maydesk.base.widgets.PDTextField;

/**
 * @author chrismay
 */
public class WzdPasswordForgotten extends PDWizard {

	private List<MUser> users = new Vector<MUser>();

	public WzdPasswordForgotten() {
		super();
		setHeight(new Extent(250));
		setWidth(new Extent(400));
		setTitle(nls(PDBeanTerms.Recover_Password));

		addPanel(new Panel2());
		addPanel(new Panel4());
		showPage(true);
	}

	class Panel2 extends PDWizardPanel {

		private PDTextField txtEmail;

		public Panel2() {
			super(null, StandardTerms.Next);
			setInfo(PDBeanTerms.Please_specify_your_email_or_login);

			PDGrid grid = new PDGrid(2);
			add(grid);

			grid.addLabel(SopUser.email);
			txtEmail = new PDTextField();
			grid.add(txtEmail);
		}

		@Override
		public Component getFocusComponent() {
			return txtEmail;
		}

		@Override
		public void applyToModel() {
			users = DaoUser.findUsersByEmail(txtEmail.getText());
			if (users.size() == 0)
				return;
			if (PDUtil.isEmpty(txtEmail.getText())) {
				return;
			}
			String emailAdresss = txtEmail.getText();

			PDMailBean mailBean = new PDMailBean();
			StringBuffer message = new StringBuffer();

			message.append(nls(PDBeanTerms.Hello) + Space + users.get(0).getJabberId() + Comma + CRLF + CRLF);
			message.append(nls(PDBeanTerms.your_login_data_are_as_follows) + CRLF + CRLF);
			for (MUser user : users) {
				message.append(nls(PDBeanTerms.Login$) + user.getJabberId() + CRLF);
				// message.append(nls(PDBeanTerms.Password$) + user.getPassword() + CRLF + CRLF);
			}

			try {
				// String project = PDDesktop.getInstance().getLookAndFeel().getApplicationName();
				mailBean.sendMail(emailAdresss, nls(PDBeanTerms.Password_recovery, "CloudDesk"), message.toString(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public Translatable getError() {
			if (PDUtil.isEmpty(txtEmail.getText())) {
				return PDBeanTerms.Please_specify_an_email;
			}
			if (users.size() == 0) {
				return PDBeanTerms.No_user_registered_with_this_email_address;
			}
			return null;
		}
	}

	class Panel4 extends PDWizardPanel {
		public Panel4() {
			super(null, StandardTerms.Done);
			setInfo(PDBeanTerms.Password_has_been_sent_to_your_email_address);
		}
	}
}