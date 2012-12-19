/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import nextapp.echo.app.Extent;
import nextapp.echo.app.TextArea;

import com.maydesk.base.util.IChangeSupportableWithAction;
import com.maydesk.base.util.PDBinding;
import com.maydesk.base.util.PDBorderFactory;

/**
 * @author chrismay
 */
public class PDTextArea extends TextArea implements IChangeSupportableWithAction<String> {

	private PDBinding changeSupport;

	public PDTextArea() {
		setBorder(PDBorderFactory.getBorder());
		setWidth(new Extent(310));
	}

	@Override
	public String getValue() {
		return getText();
	}

	@Override
	public void setValue(String text) {
		setText(text);
	}

	@Override
	public PDBinding getChangeSupport() {
		return changeSupport;
	}

	@Override
	public void setChangeSupport(PDBinding changeSupport) {
		this.changeSupport = changeSupport;
	}

	@Override
	public String getPropertyName() {
		return "text";
	}

	@Override
	public void setEditable(boolean editable) {
		setEnabled(editable);
	}
}
