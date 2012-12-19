/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.layout.GridLayoutData;
import nextapp.echo.app.text.TextComponent;

import com.maydesk.base.aspects.Translatable;

/**
 * @author Alejandro Salas
 */
public class PDGrid extends Grid {

	public PDGrid(int cols) {
		super(cols);
		setInsets(new Insets(3));
	}

	public void add(Component c, Alignment alignment) {
		GridLayoutData gld = (GridLayoutData) c.getLayoutData();
		if (gld == null) {
			gld = new GridLayoutData();
			c.setLayoutData(gld);
		}
		gld.setAlignment(alignment);
		add(c);
	}

	public <T extends Component> T add2(T c) {
		add(c);
		return c;
	}

	public <T extends Component> T addFill(T c) {
		GridLayoutData gld = (GridLayoutData) c.getLayoutData();
		if (gld == null) {
			gld = new GridLayoutData();
			c.setLayoutData(gld);
		}
		if (c instanceof TextComponent) {
			((TextComponent) c).setWidth(new Extent(100, Extent.PERCENT));
		}
		gld.setColumnSpan(4); // GridLayoutData.SPAN_FILL);
		add(c);
		return c;
	}

	public PDLabel addLabel(String caption) {
		PDLabel lbl = new PDLabel(caption, PDLabel.STYLE.FIELD_LABEL);
		add(lbl, Alignment.ALIGN_TOP);
		return lbl;
	}

	public PDLabel addLabel(Translatable term, Object... params) {
		PDLabel lbl = new PDLabel(term, params);
		add(lbl, Alignment.ALIGN_TOP);
		return lbl;
	}

	public void addEmpty() {
		add(new Label());
	}
}
