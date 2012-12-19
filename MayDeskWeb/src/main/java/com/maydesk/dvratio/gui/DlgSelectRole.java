package com.maydesk.dvratio.gui;

import com.maydesk.base.gui.PDOkCancelDialog;
import com.maydesk.base.model.MUserRole;
import com.maydesk.base.util.IRoleEditor;
import com.maydesk.dvratio.sop.SopRoles;

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
		userRole.setRoleClass(SopRoles.class.getCanonicalName());
		userRole.setRoleName(SopRoles.admin.name());
		return userRole;
	}

	@Override
	protected boolean onOkClicked() {
		return true;
	}
}
