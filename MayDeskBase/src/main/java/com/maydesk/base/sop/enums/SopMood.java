package com.maydesk.base.sop.enums;

import com.maydesk.base.aspects.Translatable;

import lombok.soplets.Sop;

@Sop(aspects=Translatable.class)
public enum SopMood {

	@Soplet(
		textEN = "Happy")
	happy,
	
	@Soplet(
		textEN = "Normal")
	normal,

	@Soplet(
		textEN = "Sad")
	sad;

}
