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
		return "C"  + userRole.getRoleName();
	}

	@Override
	public String getRoleCaption(MUserRole userRole) {
		return userRole.getRoleName();
	}
}
