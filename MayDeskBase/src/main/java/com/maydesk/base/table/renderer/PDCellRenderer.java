/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.table.renderer;

import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.layout.GridLayoutData;
import nextapp.echo.app.table.TableCellRenderer;

/**
 * @author Alejandro Salas
 */
public abstract class PDCellRenderer implements TableCellRenderer {

	protected void setBackground(Component c, int row) {
		if ((row % 2) == 0) {
			GridLayoutData gld = new GridLayoutData();
			gld.setBackground(new Color(232, 232, 232));
			c.setLayoutData(gld);
		}
	}
}