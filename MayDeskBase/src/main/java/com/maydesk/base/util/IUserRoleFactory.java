/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.util;

import java.util.List;

import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MUser;
import com.maydesk.base.model.MUserRole;

/**
 * @author chrismay
 */
public interface IUserRoleFactory {

	public List<Enum> listRoles();

	public List<MBase> listContextsForRole(IRole role);

	public IRoleEditor getEditor(MUser user, Enum role);

	public String getContextDescription(MUserRole userRole);

	public String getRoleCaption(MUserRole userRole);

}