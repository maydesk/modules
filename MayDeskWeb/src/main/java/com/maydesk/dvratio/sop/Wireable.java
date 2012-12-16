package com.maydesk.dvratio.sop;


public @interface Wireable {

	public SopDVRPlugs parent();
	public SopDVRPlugs plug();
	//public String parameter() default"";
}
