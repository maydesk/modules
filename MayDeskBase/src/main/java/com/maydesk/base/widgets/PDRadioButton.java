/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import nextapp.echo.app.ImageReference;
import nextapp.echo.app.RadioButton;

/**
 * @author chrismay
 */
public class PDRadioButton<T> extends RadioButton {

	protected T value;

	// package visibility - must be created from PDButtonGroup.createButton()!
	PDRadioButton() {
	}

	PDRadioButton(String caption) {
		super(caption);
	}

	PDRadioButton(ImageReference icon) {
		super(icon);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
}
