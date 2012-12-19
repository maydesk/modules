package com.maydesk.dvratio.sop;

/**
 * @author chrismay
 */
public @interface Wireable {

	public SopDVRPlugs parent();

	public SopDVRPlugs plug();
	// public String parameter() default"";
}
