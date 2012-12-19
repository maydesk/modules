/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import nextapp.echo.app.Border;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.TextField;

import com.maydesk.base.util.IChangeSupportableWithAction;
import com.maydesk.base.util.PDBinding;
import com.maydesk.base.util.PDBorderFactory;
import com.maydesk.base.util.PDLookAndFeel;

/**
 * @author chrismay
 */
public class PDTextField extends TextField implements IChangeSupportableWithAction {

	public static final String PROPERTY_KEY_ACTION = "keyAction";
	private PDBinding changeSupport;

	public PDTextField() {
		initGUI();
		setMaximumLength(50);
	}

	public PDTextField(String renderId) {
		initGUI();
		setMaximumLength(50);
	}

	public PDTextField(int maxLength) {
		initGUI();
		setMaximumLength(maxLength);
	}

	protected void initGUI() {
		setKeyAction(true);
		setInsets(new Insets(3, 1, 3, 0));
		setHeight(new Extent(16));
		setBorder(PDBorderFactory.getBorder());

		setDisabledBorder(new Border(new Extent(1), Color.LIGHTGRAY, Border.STYLE_DOTTED));
		setDisabledForeground(PDLookAndFeel.DISABLED_FOREGROUND);
		setDisabledBackground(PDLookAndFeel.DISABLED_BACKGROUND);

		setWidth(new Extent(150));
	}

	public void setKeyAction(boolean actionEnabled) {
		set(PROPERTY_KEY_ACTION, actionEnabled);
	}

	public boolean isKeyAction() {
		return (Boolean) get(PROPERTY_KEY_ACTION);
	}

	@Override
	public Object getValue() {
		return getText();
	}

	@Override
	public void setValue(Object value) {
		if (value == null) {
			setText("");
		} else {
			setText(value + "");
		}
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
