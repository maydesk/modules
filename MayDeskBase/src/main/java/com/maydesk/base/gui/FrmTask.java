/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import com.maydesk.base.model.MBase;
import com.maydesk.base.model.MTask;
import com.maydesk.base.sop.logical.SopTask;
import com.maydesk.base.widgets.PDDateField;
import com.maydesk.base.widgets.PDGrid;
import com.maydesk.base.widgets.PDTextField;

import nextapp.echo.app.TextArea;

public class FrmTask extends PDOkCancelDialog {

	private MTask task;
	private PDTextField txtName;
	private PDDateField calExecutionTill;
	private TextArea txtDescription;
	
	public FrmTask(MTask task) {
	    super(task.toString(), 400, 300);
	    initGUI();
	    readFromModel(task);
    }	
	
	private void initGUI() {
		PDGrid grid = new PDGrid(2);
		addMainComponent(grid);
		
		grid.addLabel(SopTask.name);
		txtName = new PDTextField();
		grid.add(txtName);
		
		grid.addLabel(SopTask.executeTill);		
		calExecutionTill = new PDDateField();
		grid.add(calExecutionTill);
		
		grid.addLabel(SopTask.description);		
		txtDescription = new TextArea();
		grid.add(txtDescription);		
	}

	public void readFromModel(MBase model) {
	    this.task = (MTask)model;
		txtName.setText(task.toString());
		//calExecutionTill.setDate(task.getExecuteTill());
		txtDescription.setText(task.getDescription());
    }

	@Override
    protected boolean onOkClicked() {
	    return true;
    }
}
