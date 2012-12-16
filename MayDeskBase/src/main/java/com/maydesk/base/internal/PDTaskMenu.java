/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.internal;

import com.maydesk.base.model.MTask;

import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import echopoint.ContainerEx;

/**
 */
public class PDTaskMenu extends ContainerEx {

	protected Column colMenu;

	public PDTaskMenu() {
		setWidth(new Extent(200));
		setOutsets(new Insets(0, 100, 0, 0));
		setInsets(new Insets(6, 6, 6, 6));
		colMenu = new Column();
		colMenu.setCellSpacing(new Extent(0));
		add(colMenu);
	}

	public PDTaskEntry addTask(final MTask task, boolean flash) {
		PDTaskEntry te = new PDTaskEntry(task);
		colMenu.add(te);
		return te;
	}

	public void clear() {
		colMenu.removeAll();
	}
}
