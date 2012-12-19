/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import java.util.EventListener;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.WindowPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.event.EventListenerList;
import nextapp.echo.app.layout.SplitPaneLayoutData;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.sop.gui.StandardTerms;
import com.maydesk.base.util.PDLookAndFeel;
import com.maydesk.base.widgets.PDPushButton;

import echopoint.PushButton;

/**
 * An OK/Cancel dialog for general purpose
 * 
 * @author Alejandro Salas <br>
 *         Created on Feb 15, 2007
 */
public abstract class PDOkCancelDialog extends PDWindowPane {

	private EventListenerList eventListenerList = new EventListenerList();
	protected Component pnlMainContainer;
	protected PushButton btnOk;
	protected PushButton btnCancel;
	protected Row rowCommands;
	protected Row rowLeftFooter;

	public PDOkCancelDialog(Translatable title, int width, int height) {
		this(nls(title), width, height);
	}

	@Deprecated
	public PDOkCancelDialog(String title, int width, int height) {
		setTitle(title);
		setModal(true);
		setWidth(new Extent(width));
		setHeight(new Extent(height));
		initGUI();
		setDefaultCloseOperation(WindowPane.DISPOSE_ON_CLOSE);
	}

	public void addActionListener(ActionListener listener) {
		eventListenerList.addListener(ActionListener.class, listener);
	}

	public void addMainComponent(Component component) {
		pnlMainContainer.add(component);
	}

	protected void btnCancelClicked(ActionEvent evt) {
		setVisible(false);
	}

	protected void btnOkClicked(ActionEvent evt) {
		if (!onOkClicked())
			return;
		fireActionEvent(new ActionEvent(this, null));
		setVisible(false);
	}

	protected abstract boolean onOkClicked();

	public void fireActionEvent(ActionEvent evt) {
		EventListener[] eventListeners = eventListenerList.getListeners(ActionListener.class);
		for (int i = 0; i < eventListeners.length; i++) {
			ActionListener listener = (ActionListener) eventListeners[i];
			listener.actionPerformed(evt);
		}
	}

	private void initGUI() {

		SplitPane splitFooterAndBody = new SplitPane(SplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP);
		splitFooterAndBody.setSeparatorPosition(new Extent(28));
		add(splitFooterAndBody);

		SplitPane splitFooterLeftRight = new SplitPane(SplitPane.ORIENTATION_HORIZONTAL_RIGHT_LEFT);
		splitFooterLeftRight.setBackground(PDLookAndFeel.BACKGROUND_COLOR);
		splitFooterLeftRight.setSeparatorPosition(new Extent(200));
		splitFooterAndBody.add(splitFooterLeftRight);

		rowCommands = new Row();
		rowCommands.setCellSpacing(new Extent(6));
		rowCommands.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		rowCommands.setBackground(PDLookAndFeel.BACKGROUND_COLOR);
		rowCommands.setInsets(new Insets(0, 3, 12, 5));
		SplitPaneLayoutData spld = new SplitPaneLayoutData();
		spld.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		spld.setBackground(PDLookAndFeel.BACKGROUND_COLOR);
		rowCommands.setLayoutData(spld);
		splitFooterLeftRight.add(rowCommands);

		rowLeftFooter = new Row();
		rowLeftFooter.setBackground(PDLookAndFeel.BACKGROUND_COLOR);
		rowLeftFooter.setInsets(new Insets(12, 4, 0, 5));
		splitFooterLeftRight.add(rowLeftFooter);

		btnCancel = new PDPushButton(StandardTerms.Cancel);
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnCancelClicked(evt);
			}
		});
		rowCommands.add(btnCancel);

		btnOk = new PDPushButton(StandardTerms.OK);
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnOkClicked(evt);
			}
		});
		rowCommands.add(btnOk);

		pnlMainContainer = getMainContainer();
		if (pnlMainContainer.getLayoutData() == null) {
			spld = new SplitPaneLayoutData();
			spld.setInsets(new Insets(6));
			pnlMainContainer.setLayoutData(spld);
		}
		splitFooterAndBody.add(pnlMainContainer);
	}

	protected Component getMainContainer() {
		Column col = new Column();
		col.setCellSpacing(new Extent(6));
		return col;
	}

	public void removeActionListener(ActionListener listener) {
		eventListenerList.removeListener(ActionListener.class, listener);
	}
}