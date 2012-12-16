/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.util;

import com.maydesk.base.model.MBase;

import nextapp.echo.app.Component;

public interface ICrud<T extends MBase> {

	public void readFromModel(MBase model);
	
	public Component getFocusComponent();

	public Class getModelClass();
}
