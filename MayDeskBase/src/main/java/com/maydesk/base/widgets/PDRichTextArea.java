/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import com.maydesk.base.util.IChangeSupportable;
import com.maydesk.base.util.PDBinding;

import nextapp.echo.extras.app.RichTextArea;

public class PDRichTextArea extends RichTextArea implements IChangeSupportable<String> {

	private PDBinding changeSupport;

	public String getValue() {
	    return getText();
    }

	public void setValue(String text) {
		setText(text);
    }
	
	public PDBinding getChangeSupport() {
    	return changeSupport;
    }

	public void setChangeSupport(PDBinding changeSupport) {
    	this.changeSupport = changeSupport;
    }

	public String getPropertyName() {
	    return "text";
    }
	
	//@Override
    public void setEditable(boolean editable) {
	    setEnabled(editable);	    
    }
}
