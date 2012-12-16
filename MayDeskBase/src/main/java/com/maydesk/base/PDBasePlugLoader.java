/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base;

import java.util.List;

import nextapp.echo.app.Component;

import com.maydesk.base.model.MWire;
import com.maydesk.base.util.IPlugTarget;
import com.maydesk.base.util.PDUtil;


public class PDBasePlugLoader {

	/**
	 * load the items to the desktop
	 */
	public static void loadItems(Component parentComponent, MWire parentWire) {
		parentComponent.removeAll();
		List<MWire> wires = PDUtil.findWires(parentWire);
		for (MWire wire : wires) {
			String className = wire.getPlug().getEditorClassName();
			if (className == null) {
				System.out.println("No editor class found for plug " + wire.getPlug().getName() + " (" + wire.getPlug().getCaption() + ")");
				continue;
			}
            try {
    			Class clazz = Class.forName(className);
    			Object instance = clazz.newInstance();    			
    			((IPlugTarget)instance).initWire(wire);
    			parentComponent.add((Component)instance);			
            } catch (Exception e) {
	            e.printStackTrace();
            }
		}		
		PDApplicationInstance.getActivePD().startPoller();
	}
	
//		
//		Label  lbl = new Label(new ResourceImageReference("img/CloudDeskLogo.png"));
//		ContainerEx c2 = new ContainerEx();
//		c2.setBottom(new Extent(30));
//		c2.setRight(new Extent(50));
//		c2.setPosition(Positionable.ABSOLUTE);
//		c2.add(lbl);
//		contentPane.add(c2);
//
//		taskMenu.clear();
//		contentPane.add(taskMenu);
//		
}
