/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
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