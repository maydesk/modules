/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui.user;

import com.maydesk.base.gui.PDOkCancelDialog;
import com.maydesk.base.model.MUserRole;
import com.maydesk.base.util.IRoleEditor;

/**
 * @author chrismay
 */
public class DlgSelectRole extends PDOkCancelDialog implements IRoleEditor {

	public DlgSelectRole() {
		super("Add role", 500, 400);
	}

	@Override
	public MUserRole getUserRole() {
		MUserRole userRole = new MUserRole();
//XXX		userRole.setRoleClass(SopRoles.class.getCanonicalName());
//XXX		userRole.setRoleName(SopRoles.admin.name());
		return userRole;
	}

	@Override
	protected boolean onOkClicked() {
		return true;
	}
}
