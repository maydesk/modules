/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

import javax.persistence.Entity;

import nextapp.echo.app.WindowPane;


@Entity
public class MActionOpenWindow extends MAction {

	private WindowPane window;
	
	@Override
    public void redoAction() {
		window.setVisible(true);
		//PDDesktop.getDesktop().addWindow(window);
    }

	@Override
    public void undoAction() {
		window.userClose();
    }

	public void setWindow(WindowPane window) {
    	this.window = window;
    }
	
	@Override
	public String toString() {
		return "Fenster geöffnet";
	}
}