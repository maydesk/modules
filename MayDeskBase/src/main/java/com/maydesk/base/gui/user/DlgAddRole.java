/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui.user;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import java.util.List;

import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.gui.PDOkCancelDialog;
import com.maydesk.base.model.MUser;
import com.maydesk.base.model.MUserRole;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.util.IRoleEditor;
import com.maydesk.base.util.IUserRoleFactory;
import com.maydesk.base.widgets.PDCombo;
import com.maydesk.base.widgets.PDLabel;

/**
 * @author chrismay
 */
public class DlgAddRole extends PDOkCancelDialog {

	private static final long serialVersionUID = 1L;

	private MUserRole userRole;
	private PDCombo<Enum> cboRole;
	private IRoleEditor roleEditor;
	private Column roleEditorColumn;
	private IUserRoleFactory userRoleFactory;
	private MUser user;

	@SuppressWarnings("unchecked")
	public DlgAddRole(MUser user, String factoryClassName) {
		super(nls(PDBeanTerms.Add_role), 400, 300);
		this.user = user;
		try {
			Class factoryClass = Class.forName(factoryClassName);
			userRoleFactory = (IUserRoleFactory) factoryClass.newInstance();
			initGUI2();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MUserRole getUserRole() {
		return userRole;
	}

	private void initGUI2() {
		List<Enum> roles = userRoleFactory.listRoles();
		pnlMainContainer.add(new PDLabel(PDBeanTerms.Role));
		cboRole = new PDCombo<Enum>();
		for (Enum role : roles) {
			cboRole.getModel().add(role.toString(), role);
		}
		cboRole.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showRoleEditor();
			}
		});
		pnlMainContainer.add(cboRole);

		roleEditorColumn = new Column();
		pnlMainContainer.add(roleEditorColumn);
	}

	private void showRoleEditor() {
		roleEditorColumn.removeAll();
		roleEditor = userRoleFactory.getEditor(user, cboRole.getSelectedItem());
		if (roleEditor != null) {
			roleEditorColumn.add((Component) roleEditor);
		}
	}

	@Override
	protected boolean onOkClicked() {
		if (roleEditor == null) {
			userRole = new MUserRole();
		} else {
			userRole = roleEditor.getUserRole();
		}
		userRole.setRole(cboRole.getSelectedItem());
		userRole.setUserRef(user);
		return true;
	}
}
