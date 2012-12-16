/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import javax.swing.JLabel;

import com.maydesk.base.aspects.Translatable;


/**
 * A JLabel with built-in NLS support
 */
public class MyLabel extends JLabel {

	/**
	 * The preferred, NLS-way of initializing a Label 
	 */
	public MyLabel(Translatable term, Object... params) {
		super(nls(term, params));
    }
	
	/**
	 * This constructor should be avoided, use the NLS constructor instead 
	 */
	@Deprecated
	public MyLabel(String caption) {
		super(caption);
	}
}






