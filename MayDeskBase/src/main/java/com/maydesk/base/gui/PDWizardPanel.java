/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.model.MBase;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDLabel;

import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;


import echopoint.ContainerEx;

/**
 * A panel which is placed inside the PDWizard 
 * 
 */
public abstract class PDWizardPanel implements IWizardPanel {

	protected String backLabel;
	protected String nextLabel;
	protected PDLabel lblTitle;
	protected Label lblInfo;
	protected Component component;
	private ContainerEx conInfo; 
	
	public PDWizardPanel(Translatable backLabel, Translatable nextLabel) {
		this(null, backLabel, nextLabel);
	}
	
	public PDWizardPanel(Translatable title, Translatable backLabel, Translatable nextLabel) {
		try {
	        component = getComponentClass().newInstance();
        } catch (Exception e) {
	        e.printStackTrace();
        }
		
		this.backLabel = nls(backLabel);
		this.nextLabel = nls(nextLabel);
		if (component instanceof Column) {
			((Column)component).setCellSpacing(new Extent(6));
		}
		lblTitle = new PDLabel(title, PDLabel.STYLE.HEADER_1);
		component.add(lblTitle);
		
		conInfo = new ContainerEx();
		conInfo.setBackground(new Color(244, 244, 244));
		conInfo.setBorder(PDUtil.getGreyBorder());
		conInfo.setInsets(new Insets(3));
		conInfo.setWidth(new Extent(95, Extent.PERCENT));
		conInfo.setVisible(false);
		component.add(conInfo);
		
		lblInfo = new Label(); // PDLabel(PDLabel.BORDERED);
		lblInfo.setLineWrap(true);
		conInfo.add(lblInfo);
	}

	protected Class<? extends Component> getComponentClass() {
		return Column.class;
	}

	public Component getComponent() {
		return component;
	}

	protected void setTitle(Translatable title) {
		lblTitle.setText(nls(title));
	}
	
	@Deprecated
	protected void setTitle(String text) {
		lblTitle.setText(text);
	}

	protected void setInfo(Translatable info, Object... params) {
		lblInfo.setText(nls(info, params));
		conInfo.setVisible(true);
	}

	@Deprecated
	protected void setInfo(String text) {
		lblInfo.setText(text);
		conInfo.setVisible(true);
	}
	
	public String getNextCaption() {
		return nextLabel;
	}

	public String getBackCaption() {
		return backLabel;
	}

	public boolean doBackAction() {
		return true;
	}

	public void readFromModel() {
	}

	public void applyToModel() {
	}

	public void applyToModel2() {
	}

	public Translatable getError() {
		return null;
	}
	
	public boolean isApplicable() {
		return true;
	}
	
	/**
	 * Overwrite if necessary
	 */
	public int getNextButtonWidth() {
		return 80;
	}
	
	/**
	 * Overwrite if necessary
	 */
	public int getBackButtonWidth() {
		return 80;
	}
	
	public void setEditing(boolean isEditing) {
	}
	
	public Component getFocusComponent() {
		return null;
	}

	public void readFromModel(MBase model) {
		//not used here
	}

	public boolean doNextAction(){
		return true;
	}
	
	public Class getModelClass() {
	    // TODO Auto-generated method stub
	    return null;
    }
	
	public void add(Component c) {
		component.add(c);
	}
}
