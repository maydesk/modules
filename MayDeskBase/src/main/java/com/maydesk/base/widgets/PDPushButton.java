/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import com.maydesk.base.aspects.Translatable;


import echopoint.PushButton;

public class PDPushButton extends PushButton {
	
	public PDPushButton(Translatable term) {
		setText(nls(term));
	}
	
}
