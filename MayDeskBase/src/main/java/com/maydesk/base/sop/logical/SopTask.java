package com.maydesk.base.sop.logical;

import lombok.soplets.Sop;

import com.maydesk.base.aspects.Translatable;

/**
 * @author chrismay
 */
@Sop(aspects = Translatable.class)
public enum SopTask {

	@Soplet(textEN = "Name")
	name,

	@Soplet(textEN = "execute till")
	executeTill,

	@Soplet(textEN = "Description")
	description;
}
