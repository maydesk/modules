package com.maydesk.dvratio.sop;

import lombok.soplets.Sop;

import com.maydesk.base.aspects.Translatable;

/**
 * @author chrismay
 */
@Sop(aspects = Translatable.class)
public enum SopRoles {

	@Soplet(textEN = "User")
	user,

	@Soplet(textEN = "Admin")
	admin,

	;
}
