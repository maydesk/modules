/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import com.maydesk.base.util.IChangeSupportableWithAction;
import com.maydesk.base.util.PDBinding;
import com.maydesk.base.util.PDBorderFactory;

import nextapp.echo.app.Extent;
import nextapp.echo.app.TextArea;

public class PDTextArea extends TextArea implements IChangeSupportableWithAction<String> {

	private PDBinding changeSupport;

	public PDTextArea() {
		setBorder(PDBorderFactory.getBorder());
		setWidth(new Extent(310));
	}
	
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
		
	@Override
    public void setEditable(boolean editable) {
	    setEnabled(editable);	    
    }
}
