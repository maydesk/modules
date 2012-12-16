/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import nextapp.echo.app.Component;
import nextapp.echo.extras.app.RichTextArea;

/**
 * A modal editor for rich text
 * 
 */
public class PDDialogRichText extends PDOkCancelDialog {

	private boolean editable;
	private RichTextArea richTextArea;
	private String newText;

	public PDDialogRichText(String title, boolean editable) {
		super(title, 617, 390);
		this.editable = editable;
		initGUI();
	}

	protected Component getMainContainer() {
		richTextArea = new RichTextArea();
		return richTextArea;
	}
	
	public String getText() {
		return newText;
	}

	private void initGUI() {
		if (!editable) {
			btnOk.setVisible(false);
			btnCancel.setText("OK"); //$NON-NLS-1$
		}
		richTextArea.setEnabled(editable);
	}

	public void setText(String text) {
		richTextArea.setText(text);
	}

	@Override
    protected boolean onOkClicked() {
		if (editable) {
			newText = richTextArea.getText();
		}
	    return true;
    }
}