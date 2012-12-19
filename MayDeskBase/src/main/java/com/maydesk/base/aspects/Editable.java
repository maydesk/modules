package com.maydesk.base.aspects;

/**
 * @author chrismay
 */
public @interface Editable {

	/**
	 * Max length of the field (applies only to text and decimal types)
	 */
	public int length() default 0;

	/**
	 * Is the field mandatory?
	 */
	public boolean mandatory() default false;

	/**
	 * Is the field read-only?
	 */
	public boolean readOnly() default false;

}
