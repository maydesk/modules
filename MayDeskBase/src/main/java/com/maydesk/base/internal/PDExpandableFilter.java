/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.internal;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDLabel;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Border;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.RowLayoutData;


import echopoint.ContainerEx;

/**
 */
public class PDExpandableFilter extends ContainerEx {

	private Grid grid;
	private Button btnExpand;
	private Button btnSearch;
	private Button btnReset;
	private ActionListener expandListener;
	private boolean isInitialized = false;
	
	public PDExpandableFilter(ActionListener expandListener) {
		this.expandListener = expandListener;
		initGUI();
	}

	private void initGUI() {
		final Row row = new Row();
		add(row);

		grid = new Grid(2);
		grid.setInsets(new Insets(3, 3, 2, 3));
		RowLayoutData rld = new RowLayoutData();
		rld.setBackgroundImage(new FillImage(EImage16.semiTrans4.getImage()));
		rld.setAlignment(new Alignment(Alignment.TOP, Alignment.TOP));
		rld.setInsets(new Insets(5));
		grid.setLayoutData(rld);
		row.add(grid);

		Column colCommands = new Column();
		colCommands.setBackground(Color.ORANGE);
		rld = new RowLayoutData();
		rld.setAlignment(new Alignment(Alignment.TOP, Alignment.TOP));
		colCommands.setLayoutData(rld);
		row.add(colCommands);

		btnExpand = new Button("F\ni\nl\nt\ne\nr");
		btnExpand.setLineWrap(true);
		btnExpand.setBackground(Color.ORANGE);
		btnExpand.setForeground(Color.DARKGRAY);
		btnExpand.setInsets(new Insets(6, 12, 0, 0));
		btnExpand.setBorder(new Border(new Extent(1), Color.DARKGRAY, Border.STYLE_DOTTED));
		btnExpand.setHeight(new Extent(110));
		btnExpand.setWidth(new Extent(14));
		btnExpand.setRolloverEnabled(false);
		btnExpand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnReset.setVisible(!btnReset.isVisible());
				btnSearch.setVisible(!btnSearch.isVisible());
				expandListener.actionPerformed(e);
			}
		});
		colCommands.add(btnExpand);

		btnReset = new Button(EImage16.undo.getImage());
		btnReset.setBorder(PDUtil.emptyBorder());
		btnReset.setBackground(Color.ORANGE);
		btnReset.setRolloverEnabled(false);
		btnReset.setVisible(false);
		colCommands.add(btnReset);

		btnSearch = new Button(EImage16.generado.getImage());
		btnSearch.setBorder(PDUtil.emptyBorder());
		btnSearch.setBackground(Color.ORANGE);
		btnSearch.setRolloverEnabled(false);
		btnSearch.setInsets(new Insets(0, 0, 0, 5));
		btnSearch.setVisible(false);
		colCommands.add(btnSearch);
	}

	public void addFilter(Translatable caption, Component filterComponent) {
		grid.add(new PDLabel(caption));
		grid.add(filterComponent);
	}

	public void addFilter(String caption, Component filterComponent) {
		grid.add(new PDLabel(caption, PDLabel.STYLE.FIELD_LABEL));
		grid.add(filterComponent);
	}

	@Override
	public void setWidth(Extent width) {
		grid.setWidth(new Extent(width.getValue() - 10));
	}

	public void setResetListener(ActionListener resetListener) {
		btnReset.addActionListener(resetListener);		
    }
	
	public void setOkListener(ActionListener okListener) {
		btnSearch.addActionListener(okListener);
		isInitialized = true;
    }

	public boolean isInitialized() {
		return isInitialized;
    }

	public Grid getGrid() {
    	return grid;
    }
}
