/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.web;

import java.util.ArrayList;
import java.util.List;

import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MUser;
import com.maydesk.base.model.MUserRole;
import com.maydesk.base.util.IRole;
import com.maydesk.base.util.IRoleEditor;
import com.maydesk.base.util.IUserRoleFactory;
import com.maydesk.dvratio.sop.SopRoles;

/**
 * @author chrismay
 */
public class MDUserRoleFactory implements IUserRoleFactory {

	@Override
	public List<Enum> listRoles() {
		List<Enum> roles = new ArrayList<Enum>();
		for (SopRoles role : SopRoles.values()) {
			roles.add(role);
		}
		return roles;
	}

	@Override
	public List<MBase> listContextsForRole(IRole role) {
		return null;
	}

	@Override
	public IRoleEditor getEditor(MUser user, Enum role) {
		return null;
	}

	@Override
	public String getContextDescription(MUserRole userRole) {
		return "C" + userRole.getRoleName();
	}

	@Override
	public String getRoleCaption(MUserRole userRole) {
		return userRole.getRoleName();
	}
}
