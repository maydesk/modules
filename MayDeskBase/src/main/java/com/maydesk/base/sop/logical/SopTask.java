package com.maydesk.base.sop.logical;

import com.maydesk.base.aspects.Translatable;

import lombok.soplets.Sop;


@Sop(aspects=Translatable.class)
public enum SopTask {

	@Soplet( 
		textEN = "Name")
	name,

	@Soplet( 
		textEN = "execute till")
	executeTill,

	@Soplet( 
		textEN = "Description")
	description;	
}
