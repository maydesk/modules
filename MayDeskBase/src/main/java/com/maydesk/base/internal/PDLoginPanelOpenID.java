/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.internal;

import com.maydesk.base.PDUserSession;
import com.maydesk.base.model.MUser;
import com.maydesk.base.util.ILookAndFeel;
import com.maydesk.base.widgets.PDLabel;

import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import echopoint.Strut;

/**
 * 
 * 
 */
public class PDLoginPanelOpenID extends Row {

	private final static String LOGIN_COOKIE_NAME = "PROFIDESK_LOGIN";
	
	private PDLabel lblStatus;
	
	public PDLoginPanelOpenID() {
		initGUI();
		PDUserSession.getInstance().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateStatus();
            }			
		});
	}

	public void applyLookAndFeel(ILookAndFeel lookAndFeel) {
	}

	private void initGUI() {
		setCellSpacing(new Extent(6));
		setInsets(new Insets(3));
		
//		Font smallFont = new Font(Font.ARIAL, Font.PLAIN, new Extent(11));
		add(new Strut(3, 0));
		
		lblStatus = new PDLabel(PDLabel.STYLE.FIELD_LABEL);
		lblStatus.setForeground(Color.WHITE);
		add(lblStatus);
		
		updateStatus();
	}

	public void updateStatus() {
		PDUserSession us = PDUserSession.getInstance();
		MUser user = us.getUser();
		if (user != null) {
			lblStatus.setText("Status: You are logged in as: '" + user.getJabberId() + "'");
		} else {
			lblStatus.setText("Status: You are not logged in");
		}
	}
}