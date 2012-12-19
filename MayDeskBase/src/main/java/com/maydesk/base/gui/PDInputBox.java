/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import nextapp.echo.app.TextField;

import com.maydesk.base.PDDesktop;

/**
 * A model box displaying a simple message
 * 
 * @author Alejandro Salas
 */
public class PDInputBox extends PDOkCancelDialog {

	public static PDInputBox inputBox(String title, String msg) {
		PDInputBox box = new PDInputBox(title, msg, 300, 150);
		PDDesktop.getInstance().addWindow(box);
		return box;
	}

	private String value;
	private TextField txt;

	public PDInputBox(String title, String msg, int w, int h) {
		super(title, w, h);
		setModal(true);

		txt = new TextField();
		addMainComponent(txt);
	}

	public void setValue(String value) {
		txt.setText(value);
	}

	public String getValue() {
		return value;
	}

	@Override
	protected boolean onOkClicked() {
		value = txt.getText();
		return true;
	}
}
