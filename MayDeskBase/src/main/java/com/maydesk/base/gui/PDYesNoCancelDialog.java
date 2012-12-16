/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import com.maydesk.base.sop.gui.StandardTerms;
import com.maydesk.base.widgets.PDPushButton;

import nextapp.echo.app.Component;
import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;


/**
 * @author Chris May 
 */
public abstract class PDYesNoCancelDialog extends PDOkCancelDialog {

	private Label lbl;
	
	public PDYesNoCancelDialog(String title, String text) {
		super(title, 300, 150);
		lbl.setText(text);
		
		PDPushButton btnNo = new PDPushButton(StandardTerms.No);
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnNoClicked(evt);
			}
		});
		rowCommands.add(btnNo);
		
		btnOk.setText("Yes");
	}
	
	protected void btnNoClicked(ActionEvent evt) {
		if (!onNoClicked()) return;
		fireActionEvent(new ActionEvent(this, null));
		setVisible(false);
	}
	
	protected abstract boolean onNoClicked();

	@Override
	protected void btnCancelClicked(ActionEvent evt) {
		if (!onCancelClicked()) return;
		setVisible(false);
	}
	
	protected abstract boolean onCancelClicked();

	protected Component getMainContainer() {
		lbl = new Label();
		return lbl;
	}
	
}