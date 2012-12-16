/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.util;

import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MDataLink;

import nextapp.echo.app.Component;


public interface ICrudWithBinding<T extends MBase> extends IBindable {
	
	public void readFromModel(MDataLink dataLinks);
	
	public Component getFocusComponent();

	//for setting the datalink
	public void setDataObject(MBase model);

	//public Component getComponent();
	
}
