/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import nextapp.echo.app.Color;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.RadioButton;
import nextapp.echo.app.button.ButtonGroup;
import nextapp.echo.app.event.ActionListener;

/**
 * @author chrismay
 */
public class PDButtonGroup<T> extends ButtonGroup {

	public T getSelectedValue() {
		PDRadioButton<T> rdo = getSelectedButton();
		return rdo == null ? null : rdo.getValue();
	}

	public PDRadioButton<T> getSelectedButton() {
		for (PDRadioButton<T> rdo : getPDButtons()) {
			if (rdo.isSelected()) {
				return rdo;
			}
		}
		return null;
	}

	public PDRadioButton<T> createButton() {
		return createButton(null);
	}

	public PDRadioButton<T> createButton(String caption) {
		PDRadioButton<T> btn = new PDRadioButton<T>(caption);
		btn.setGroup(this);
		return btn;
	}

	public PDRadioButton<T> createButton(T value, String caption) {
		PDRadioButton<T> btn = new PDRadioButton<T>(caption);
		btn.setGroup(this);
		btn.setValue(value);
		return btn;
	}

	public PDRadioButton<T> createButton(T value, ImageReference icon) {
		PDRadioButton<T> btn = new PDRadioButton<T>(icon);
		btn.setGroup(this);
		btn.setValue(value);
		return btn;
	}

	public void setSelectedValue(T value) {
		if (value != null) {
			for (PDRadioButton<T> rdo : getPDButtons()) {
				if (value.equals(rdo.getValue())) {
					rdo.setSelected(true);
					return;
				}
			}
		}
		getButtons()[0].setSelected(true);
	}

	public void addActionListener(ActionListener listener) {
		for (PDRadioButton<T> rdo : getPDButtons()) {
			rdo.addActionListener(listener);
		}
	}

	public PDRadioButton<T>[] getPDButtons() {
		RadioButton[] btnSource = getButtons();
		PDRadioButton<T>[] btnTarget = new PDRadioButton[btnSource.length];
		for (int i = 0; i < btnTarget.length; i++) {
			btnTarget[i] = (PDRadioButton<T>) btnSource[i];
		}
		return btnTarget;
	}

	@Override
	public void addButton(RadioButton radioButton) {
		if (!(radioButton instanceof PDRadioButton)) {
			throw new IllegalArgumentException("Only PDRadioButton allowed");
		}
		super.addButton(radioButton);
	}

	public void setEnabled(boolean enabled) {
		for (PDRadioButton<T> rdo : getPDButtons()) {
			rdo.setEnabled(enabled);
			rdo.setForeground(enabled ? Color.BLACK : Color.LIGHTGRAY);
		}
	}
}
