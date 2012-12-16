/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.internal;

import static nextapp.echo.app.Alignment.CENTER;
import static nextapp.echo.app.Alignment.DEFAULT;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.widgets.PDPushButton;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Extent;



/**
 * 
 * 
 */
public class PDFooterButton extends PDPushButton {

	public PDFooterButton(Translatable caption) {
		super(caption);
		setAlignment(new Alignment(CENTER, DEFAULT));
		setHeight(new Extent(22));
	}
}
