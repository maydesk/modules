package com.maydesk.base.aspects;

/**
 * @author chrismay
 */
public @interface Descriptable {

	public String descriptionDE() default "";

	public String descriptionEN() default "";

	public String descriptionFR() default "";

}
