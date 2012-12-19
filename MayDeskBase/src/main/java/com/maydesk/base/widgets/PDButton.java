/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.FillImage;

import com.maydesk.base.util.IImage;
import com.maydesk.base.util.PDUtil;

/**
 * @author chrismay
 */
public class PDButton extends Button {

	public enum STYLE {
		NORMAL, TRANSPARENT
	}

	public PDButton(String text) {
		this(text, STYLE.NORMAL);
	}

	public PDButton(String text, STYLE style) {
		setText(text);

		switch (style) {
		case NORMAL:
			setBorder(new Border(1, Color.DARKGRAY, Border.STYLE_OUTSET));
			setBackground(new Color(235, 235, 235));
			break;
		case TRANSPARENT:
			setForeground(Color.LIGHTGRAY);
			setBackgroundImage(new FillImage(PDUtil.getImg("img/semitrans6a.png")));
		}

		setTextAlignment(Alignment.ALIGN_CENTER);
		setAlignment(Alignment.ALIGN_CENTER);
		setRolloverEnabled(true);
	}

	public PDButton(IImage image) {
		setIcon(image.getImage());
	}

	public PDButton(String text, IImage image) {
		setIcon(image.getImage());
		setText(text);
	}
}
