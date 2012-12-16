package com.maydesk.base.sop.enums;

import lombok.soplets.Sop;

import com.maydesk.base.aspects.Translatable;

@Sop(aspects=Translatable.class)
public enum SopGender {

	@Soplet(
		textEN = "Male")
	male,
	
	@Soplet(
		textEN = "Female")
	female;
}
