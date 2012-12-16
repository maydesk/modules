package com.maydesk.base.aspects;


public @interface Descriptable {

	public String descriptionDE() default "";
	public String descriptionEN() default "";
	public String descriptionFR() default "";

}
