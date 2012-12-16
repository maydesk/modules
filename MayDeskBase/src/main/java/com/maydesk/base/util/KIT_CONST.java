package com.maydesk.base.util;


public enum KIT_CONST {

	Comma(","),
	
	Star("*"),

	Space(" "),

	Apostrophe("'"),

	SA(" '"),

	AS("' "),

	CRLF("\n"), 
	
	UNDERSCORE("_"), 
	
	EMPTY(""),
	
	Colon(":");
	
	
	private String text;
	
	private KIT_CONST(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}

	/**
	 * short form of toString()
	 */
	public String s() {
	    return text;
    }
}
