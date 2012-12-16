package com.maydesk.base.gui;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import com.maydesk.base.sop.gui.StandardTerms;


public class PDSimpleDialog extends PDOkCancelDialog {

	public PDSimpleDialog(String title, int w, int h) {
		super(title, w, h);
		btnOk.setText(nls(StandardTerms.Close));
		btnCancel.setVisible(false);
		setModal(false);
	}

	@Override
    protected boolean onOkClicked() {
		return true;
    }
}
