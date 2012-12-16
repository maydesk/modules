package com.maydesk.base.util;

import nextapp.echo.app.ImageReference;

import com.maydesk.base.model.MShortcut;

public interface ShortcutType {

	public void openShortcut(MShortcut shortcut);

	public String getTitle(MShortcut mShortcut);

	public ImageReference getIcon(MShortcut mShortcut);
}
