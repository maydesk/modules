/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base;

import nextapp.echo.app.event.ActionListener;
import nextapp.echo.extras.app.menu.DefaultOptionModel;
import nextapp.echo.extras.app.menu.ItemModel;
import nextapp.echo.extras.app.menu.MenuModel;

/**
 * An abstract implementation for providing a perspective Extends this class to
 * provide a personalized view of your desktop
 */
public abstract class PDMenuProvider implements ActionListener {

	public PDMenuProvider() {		
	}

	public ItemModel getMenuItem(String name, String actionId) {
		DefaultOptionModel mit = new DefaultOptionModel(name, actionId, null);
		return mit;
	}

	protected abstract void onInit();

	public abstract MenuModel updateMenu();

	protected void loggingOut() {
		//Empty
	}
}
