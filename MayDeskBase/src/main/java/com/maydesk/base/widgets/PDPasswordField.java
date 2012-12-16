/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import nextapp.echo.app.Border;
import nextapp.echo.app.Color;
import nextapp.echo.app.PasswordField;
import nextapp.echo.app.Border.Side;

public class PDPasswordField extends PasswordField {
	
	public PDPasswordField() {
		initGUI();
	}

	protected void initGUI() {
//		setInsets(new Insets(3, 1, 3, 0));
//		setHeight(new Extent(16));
		Side[] sides = new Side[4];
		sides[0] = new Side(0, Color.LIGHTGRAY, Border.STYLE_SOLID);
		sides[1] = new Side(0, Color.LIGHTGRAY, Border.STYLE_SOLID);
		sides[3] = new Side(2, Color.LIGHTGRAY, Border.STYLE_SOLID);
		sides[2] = new Side(1, Color.LIGHTGRAY, Border.STYLE_DOTTED);
		setBorder(new Border(sides));
	}
}
