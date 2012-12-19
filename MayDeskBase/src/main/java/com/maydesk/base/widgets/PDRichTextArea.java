/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import nextapp.echo.extras.app.RichTextArea;

import com.maydesk.base.util.IChangeSupportable;
import com.maydesk.base.util.PDBinding;

/**
 * @author chrismay
 */
public class PDRichTextArea extends RichTextArea implements IChangeSupportable<String> {

	private PDBinding changeSupport;

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
