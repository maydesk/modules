/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import java.util.List;
import java.util.Random;

import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.util.IImage;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import echopoint.ContainerEx;
import echopoint.able.Scrollable;

/**
 * A panel which shows some structured information
 * May be used in the background for assisting the user, or in 
 * the context of a slide-show
 * 
 */
public class PDInfoPane extends ContainerEx {

	protected final static String LEFT = "left";
	protected final static String RIGHT = "right";
	protected final static int INSET_RANDOM = 0;
	protected final static int INSET_RANDOM_PLUS_X = 1;
	protected final static int INSET_MINIMUM = 2;

	protected Label lblTitle;
	protected Label lblDescription;
	protected Label lblText;
	protected Label lblAttributes;
	protected Button btnArrow;
	
	public PDInfoPane() {
		this(INSET_RANDOM, false);
	}

	public PDInfoPane(int position) {
		this(position, false);
	}

	public PDInfoPane(int position, boolean scrollableText) {
		initGUI(position, scrollableText);
	}

	private void initGUI(int position, boolean scrollableText) {
		int x = 40;
		int y = 50;
		if (position == INSET_RANDOM) {
			x = 80 + new Random().nextInt(80);
			y += new Random().nextInt(60);
		} else if (position == INSET_RANDOM_PLUS_X) {
			x = 170 + new Random().nextInt(80);
			y += new Random().nextInt(60);
		}

		setInsets(new Insets(x, y, 0, 0));
		lblTitle = new Label();
		//lblTitle.setInsets(new Insets(0, 0, 0, 3));
		lblTitle.setForeground(Color.DARKGRAY);
		lblTitle.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(22)));
		add(lblTitle);

		lblDescription = new Label();
		//lblDescription.setOutsets(new Insets(0, 6, 0, 0));
		//lblDescription.setInsets(new Insets(3, 3, 0, 3));
		lblDescription.setLineWrap(true);
		//lblDescription.setWidth(new Extent(450));
		lblDescription.setForeground(Color.WHITE);
		lblDescription.setBackground(Color.DARKGRAY);
		lblDescription.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(18)));
		add(lblDescription);

		lblText = new Label();
		lblText.setTextAlignment(new Alignment(Alignment.LEADING, Alignment.TOP));
		//lblText.setOutsets(new Insets(0, -5, 0, 0));
		//lblText.setInsets(new Insets(0, 0, 0, 0));
		lblText.setLineWrap(true);
		//lblText.setWidth(new Extent(450));
		lblText.setForeground(Color.DARKGRAY);
		lblText.setFont(new Font(Font.VERDANA, Font.PLAIN, new Extent(16)));

		if (scrollableText) {
			//lblText.setOutsets(new Insets(0, 3, 0, 0));
			ContainerEx scrollContainer = new ContainerEx();
			scrollContainer.setScrollBarPolicy(Scrollable.AUTO);
			scrollContainer.setWidth(new Extent(600));
			scrollContainer.setHeight(new Extent(250));
			add(scrollContainer);
			scrollContainer.add(lblText);
		} else {
			add(lblText);
		}

		lblAttributes = new Label();
		//lblAttributes.setInsets(new Insets(0, 0, 0, 0));
		lblAttributes.setLineWrap(true);
		//lblAttributes.setWidth(new Extent(320));
		lblAttributes.setForeground(Color.DARKGRAY);
		lblAttributes.setFont(new Font(Font.VERDANA, Font.ITALIC, new Extent(14)));
		add(lblAttributes);
	}

	public void setText(String text) {
		//lblText.setText(PDUtil.getXHTML(text));
	}


	public void setAttributes(List<String> attributes) {
		String s = "<span xmlns:xhtml=\"http://www.w3.org/1999/xhtml\"> <xhtml:p><xhtml:ul>";
		for (String att : attributes) {
			s += "<xhtml:li>" + att + "</xhtml:li>";
		}
		s += "</xhtml:ul></xhtml:p> </span>";
		//XhtmlFragment xhtml = new XhtmlFragment(s);
		//lblAttributes.setText(xhtml);
	}

	protected String getImageTag(IImage img, String alignment) {
		ImageReference rir = img.getImage();
		Label lbl = new Label(rir);
		//lbl.setWidth(new Extent(0));
		//lbl.setHeight(new Extent(0));
		add(lbl);

		//XXX use ImageManager instead
		String s = "<xhtml:img  align=\"" + alignment
				+ "\" vspace=\"5\" hspace=\"10\" src=\"/Showroom/?serviceId=Echo.StreamImage&amp;imageuid="
				+ rir.getRenderId() + "\" />";
		return s;
	}

	protected void addArrowButton(String caption) {
		btnArrow = new Button(caption);
		btnArrow.setIcon(EImage16.orangearrow.getImage());
		btnArrow.setTextPosition(new Alignment(Alignment.CENTER, Alignment.BOTTOM));
		btnArrow.setInsets(new Insets(210, 0, 0, 0));
		btnArrow.setForeground(Color.ORANGE);
		btnArrow.setBackground(null);
		btnArrow.setWidth(new Extent(200));
		btnArrow.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(14)));
		btnArrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnArrowClicked();
			}
		});
		add(btnArrow);
	}

	protected void btnArrowClicked() {
		// overwrite if needed
	}

}
