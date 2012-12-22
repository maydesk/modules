/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.extras.app.TabPane;
import nextapp.echo.extras.app.layout.TabPaneLayoutData;

import com.maydesk.base.util.PDUtil;

import echopoint.ContainerEx;

/**
 * @author chrismay
 */
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
