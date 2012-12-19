package com.maydesk.base.sop.plug;

/**
 * @author chrismay
 */
public @interface Configurable {

	public String description();

	public boolean mandatory() default false;

	public String defaultValue() default "";

}
