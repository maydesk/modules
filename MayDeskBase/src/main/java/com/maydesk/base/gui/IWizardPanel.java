/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.util.ICrud;

import nextapp.echo.app.Component;



public interface IWizardPanel extends ICrud {

	public String getNextCaption();

	public String getBackCaption();

	public boolean doBackAction();

	public void applyToModel2();
	
	public Translatable getError();
	
	public boolean isApplicable();
	
	public int getNextButtonWidth();

	public int getBackButtonWidth();

	public void readFromModel();

	public boolean doNextAction();
	
	public void applyToModel();

	public Component getComponent();
}
