package com.maydesk.base.aspects;

/**
 * @author chrismay
 */
public @interface Translatable {

	public String textDE() default "";

	public String textEN() default "";

	public String textFR() default "";

}
