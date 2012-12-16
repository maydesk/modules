/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import java.util.List;
import java.util.Vector;

import com.maydesk.base.model.MBase;
import com.maydesk.base.util.ICrud;

import nextapp.echo.app.Component;

	
public class PDTabCrud<T extends MBase> extends PDTab implements ICrud<T> {

	protected List<Component> tabs;
	
	public PDTabCrud() {
		tabs = new Vector<Component>();
	}

	public void readFromModel(MBase model) {
		for (Component tab : tabs) {
			if (tab instanceof ICrud) {
				((ICrud)tab).readFromModel(model);
			}
		}
	}
	
    public void addTab2(String caption, Component tab) {
    	tabs.add(tab);    	
    	addTab(caption, (Component)tab);
    }

    public void removeTab(int index) {
    	getTabPane().remove(index);
    	tabs.remove(index);
    }

	public Component getFocusComponent() {
		if (tabs.size() > 0) {
			return ((ICrud)tabs.get(0)).getFocusComponent();
		}
	    return null;
    }

	public Class getModelClass() {
	    // TODO Auto-generated method stub
	    return null;
    }
}