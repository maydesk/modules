/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import nextapp.echo.app.Extent;
import nextapp.echo.app.ImageReference;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.model.MShortcut;

/**
 * A <code>Component</code> which renders its contents in a floating, movable window.
 * 
 * @author chrismay
 */
public class PDShortcut extends PDDesktopItem {

	public static final String PROPERTY_ACTION_EVENT = "action";
	public static final String PROPERTY_ACTION_MOUSE_UP = "mouseUp";
	public static final String PROPERTY_ICON = "icon";

	protected MShortcut shortcut;

	public PDShortcut(MShortcut shortcut) {
		super(shortcut.getTitle(), shortcut.getPositionX(), shortcut.getPositionY());
		this.shortcut = shortcut;
		try {
			ImageReference img = shortcut.getIcon();
			set(PROPERTY_ICON, img);
		} catch (Exception e) {
			System.out.println("Icon for shortcut " + shortcut + " was invalid: " + shortcut.getIcon());
		}
	}

	@Override
	public void processInput(String inputName, Object inputValue) {
		if (PROPERTY_ACTION_MOUSE_UP.equals(inputName)) {
			if (shortcut.getPositionX() < 50 && shortcut.getPositionY() < 55) {
				// sent to recycle bin
				setVisible(false);
				if (shortcut.getId() > 0) {
					PDHibernateFactory.getSession().delete(shortcut);
				}
			} else {
				// store the new x/y position
				if (shortcut.getOwner() != null) {
					PDHibernateFactory.getSession().saveOrUpdate(shortcut);
				}
			}
			// shortcut.onMouseUp(this);
		} else if (PROPERTY_POSITION_X.equals(inputName)) {
			shortcut.setPositionX(((Extent) inputValue).getValue());
		} else if (PROPERTY_POSITION_Y.equals(inputName)) {
			shortcut.setPositionY(((Extent) inputValue).getValue());
		} else if (PROPERTY_ACTION_EVENT.equals(inputName)) {
			shortcut.executeTask();
		}
	}

	public MShortcut getModel() {
		return shortcut;
	}
}
