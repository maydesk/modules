/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.internal;

import nextapp.echo.app.Border;
import nextapp.echo.app.Border.Side;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.model.MTask;
import com.maydesk.base.util.PDUtil;

import echopoint.ContainerEx;

/**
 * @author chrismay
 */
public class PDTaskEntry extends ContainerEx {

	private MTask task;
	private Button button;

	public PDTaskEntry(MTask t) {
		this.task = t;

		Side emptySide = new Side(0, Color.BLACK, Border.STYLE_NONE);

		Side[] sides = new Side[4];
		sides[0] = emptySide;
		sides[1] = emptySide;
		sides[2] = new Side(2, Color.DARKGRAY, Border.STYLE_DOTTED);
		sides[3] = emptySide;
		Border border = new Border(sides);
		setBorder(border);

		// Row row = new Row();
		// add(row);
		//
		// Column colWorkflowOption = new Column();
		//
		// Label lblAssignedBy = new Label("Task assigned by: " + task.getCreatedBy());
		// colWorkflowOption.add(lblAssignedBy);
		//
		// Label lblAssignedSince = new Label("Task assigned since: " + PDFormat.formatLong(task.getCreatedDate()));
		// colWorkflowOption.add(lblAssignedSince);
		//
		// Label lblPriority = new Label("Priority: " + task.getPriority());
		// colWorkflowOption.add(lblPriority);
		//
		// Button btnAcceptNow = new Button("Accept now");
		// colWorkflowOption.add(btnAcceptNow);
		//
		// Button btnManiana = new Button("Remind me later");
		// colWorkflowOption.add(btnManiana);
		//
		// Button btnDecline = new Button("Decline");
		// colWorkflowOption.add(btnDecline);

		String caption = task.getName();
		// if (task.getExecuteTill() != null) {
		// int daysTooLate = -PDUtil.getDaysDifference(Calendar.getInstance(), task.getExecuteTill());
		// for (int i = 0; i < daysTooLate; i++) {
		// caption += "!";
		// }
		// }
		button = new Button(caption);
		button.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(13)));
		button.setForeground(new Color(212, 212, 212));
		button.setHeight(new Extent(16));
		button.setInsets(new Insets(5, 0, 5, 0));
		button.setBorder(PDUtil.emptyBorder());
		button.setRolloverEnabled(true);
		button.setRolloverForeground(Color.WHITE);
		button.setRolloverBackground(new Color(120, 180, 205));
		button.setRolloverBorder(PDUtil.emptyBorder());
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				task.executeTask();
			}
		});
		add(button);

		Label lblDescription = new Label();
		String s = task.getDescription();
		lblDescription.setText(s);
		lblDescription.setFont(new Font(Font.VERDANA, Font.PLAIN, new Extent(9)));
		lblDescription.setForeground(Color.DARKGRAY);
		// lblDescription.setInsets(new Insets(20, 0, 0, 2));
		ContainerEx c = new ContainerEx();
		c.setInsets(new Insets(20, 0, 0, 2));
		c.add(lblDescription);
		add(c);
	}

	public MTask getTask() {
		return task;
	}

	public Button getButton() {
		return button;
	}

}
