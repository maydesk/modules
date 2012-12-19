package com.maydesk.base.sop.enums;

import lombok.soplets.Sop;

import com.maydesk.base.aspects.Translatable;

/**
 * @author chrismay
 */
@Sop(aspects = Translatable.class)
public enum SopMood {

	@Soplet(textEN = "Happy")
	happy,

	@Soplet(textEN = "Normal")
	normal,

	@Soplet(textEN = "Sad")
	sad;

}
