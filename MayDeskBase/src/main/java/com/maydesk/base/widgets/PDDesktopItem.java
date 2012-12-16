/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;

public abstract class PDDesktopItem extends Component {

	public static final String PROPERTY_POSITION_X = "positionX";
	public static final String PROPERTY_POSITION_Y = "positionY";
	public static final String PROPERTY_TITLE = "title";

	public PDDesktopItem(String title, int posX, int posY) {
		set(PROPERTY_TITLE, title);
		set(PROPERTY_POSITION_X, new Extent(posX));
		set(PROPERTY_POSITION_Y, new Extent(posY));
	}

	@Override
	public boolean isValidParent(Component parent) {
		return true; //parent instanceof ContentPane;
	}

}
