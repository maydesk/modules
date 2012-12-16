package com.maydesk.base.util;

import nextapp.echo.app.event.ActionListener;


public interface IChangeSupportableWithAction<T> extends IChangeSupportable<T> {

	public void addActionListener(ActionListener actionListener);
}
