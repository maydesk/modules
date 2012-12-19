package com.maydesk.social.gui;

import nextapp.echo.app.Insets;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.PDUserSession;
import com.maydesk.base.model.MWire;
import com.maydesk.base.util.IPlugTarget;
import com.maydesk.base.widgets.PDButton;

/**
 * @author chrismay
 */
public class BtnLogout extends PDButton implements IPlugTarget {

	public BtnLogout() {
		super("Logout", PDButton.STYLE.TRANSPARENT);
		setInsets(new Insets(10, 3, 3, 0));
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PDUserSession.getInstance().doLogout();
			}
		});
	}

	@Override
	public void initWire(MWire parentWire) {
	}
}