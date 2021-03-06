/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.internal;

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

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDLabel;

import echopoint.ContainerEx;

/**
 * @author chrismay
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
			@Override
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
