/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import com.maydesk.base.util.PDUtil;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.extras.app.TabPane;
import nextapp.echo.extras.app.layout.TabPaneLayoutData;
import echopoint.ContainerEx;

public class PDTab extends ContainerEx {

	private TabPane tab;

	public PDTab() {
		initGUI();
	}
	
	protected void initGUI() {
		setHeight(new Extent(100, Extent.PERCENT));
		
		tab = new TabPane();
		tab.setBorder(PDUtil.getGreyBorder());
		tab.setInsets(new Insets(0, 0, 0, 0));
		tab.setTabInset(new Extent(20));
		tab.setBorderType(TabPane.BORDER_TYPE_ADJACENT_TO_TABS);
		add(tab);
	}

	public ContainerEx addTab(String tabTitle, Component tabContent) {
		ContainerEx pane = new ContainerEx();
		pane.setInsets(new Insets(6));		
		TabPaneLayoutData tpld = new TabPaneLayoutData();
		tpld.setTitle(tabTitle);
		pane.setLayoutData(tpld);
		pane.add(tabContent);
		tab.add(pane);
		return pane;
	}
	
	public TabPane getTabPane() {
		return tab;
	}
}
