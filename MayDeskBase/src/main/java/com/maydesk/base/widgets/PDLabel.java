/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Font.Typeface;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.layout.GridLayoutData;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.util.PDLookAndFeel;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.util.SopletsResourceBundle;

import echopoint.ContainerEx;

/**
 * @author chrismay
 */
public class PDLabel extends ContainerEx {

	public enum STYLE {
		FIELD_LABEL, FIELD_VALUE, ANNOTATION, WHITE_BIG, HEADER_1, HEADER_3, SMALL, FIELD_BORDERED;
	}

	private Label label;

	public PDLabel(Translatable term, Object... params) {
		this(term, STYLE.FIELD_LABEL, params);
	}

	public PDLabel(Translatable term, STYLE style, Object... params) {
		this(SopletsResourceBundle.nls(term), style, false);
	}

	public PDLabel(STYLE style) {
		this(null, style, false);
	}

	@Deprecated
	public PDLabel(String text) {
		this(text, STYLE.FIELD_LABEL, false);
	}

	@Deprecated
	public PDLabel(String text, STYLE style) {
		this(text, style, false);
	}

	public PDLabel(String text, STYLE style, boolean alignRight) {
		label = new Label();
		label.setText(text);
		label.setForeground(Color.DARKGRAY);
		add(label);

		switch (style) {
		case FIELD_LABEL:
			setInsets(new Insets(5, 0));
			label.setFont(new Font(new Typeface("Arial"), Font.BOLD, new Extent(12)));
			break;
		case FIELD_BORDERED:
			setBorder(PDUtil.getGreyBorder());
			setInsets(new Insets(5, 3));
			label.setFont(new Font(new Typeface("Verdana"), Font.PLAIN, new Extent(12)));
			break;
		case FIELD_VALUE:
			setInsets(new Insets(5, 0));
			label.setFont(new Font(new Typeface("Verdana"), Font.PLAIN, new Extent(12)));
			break;
		case SMALL:
			setInsets(new Insets(2, 0));
			label.setFont(new Font(new Typeface("Verdana"), Font.PLAIN, new Extent(9)));
			break;
		case ANNOTATION:
			setInsets(new Insets(5, 0));
			label.setFont(new Font(new Typeface("Verdana"), Font.ITALIC, new Extent(11)));
			break;
		case HEADER_1:
			setInsets(new Insets(5, 0));
			label.setFont(new Font(new Typeface("Verdana"), Font.BOLD, new Extent(20)));
			label.setForeground(PDLookAndFeel.DISABLED_FOREGROUND);
			break;
		case HEADER_3:
			setInsets(new Insets(5, 0));
			label.setFont(new Font(new Typeface("Verdana"), Font.BOLD, new Extent(14)));
			label.setForeground(PDLookAndFeel.DISABLED_FOREGROUND);
			break;
		case WHITE_BIG:
			label.setFont(new Font(Font.MONOSPACE, Font.BOLD, new Extent(32)));
			label.setForeground(Color.WHITE);

		}

		if (alignRight) {
			GridLayoutData gld = new GridLayoutData();
			gld.setAlignment(new Alignment(Alignment.RIGHT, 0));
			setLayoutData(gld);
		}
	}

	public void setText(String text) {
		if (text == null)
			text = "";
		label.setText(text);
	}

	public String getText() {
		return label.getText();
	}

	public Label getLabel() {
		return label;
	}

	public void setIcon(ImageReference resourceImageReference) {
		label.setIcon(resourceImageReference);
	}

	@Override
	public void setToolTipText(String description) {
		label.setToolTipText(description);
	}
}
