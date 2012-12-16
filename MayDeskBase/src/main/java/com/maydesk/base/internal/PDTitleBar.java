/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.internal;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import com.maydesk.base.gui.PDMessageBox;
import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.util.HeaderValue;
import com.maydesk.base.util.IImage;
import com.maydesk.base.util.PDLookAndFeel;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDButton;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import echopoint.Strut;

/**
 * 
 * 
 * @date 25.01.2009
 */
public class PDTitleBar extends Grid {

	protected PDButton btnDelete;
	protected Label btnTitle;
	protected Row commandRow;
	protected boolean deleteable;
	protected HeaderValue headerValues;
	protected ActionListener titleChangeListener;
	protected IDeleteMaster parentForm;

	public PDTitleBar(IDeleteMaster parentForm, HeaderValue headerValues, int cols) {
		super(cols);
		this.parentForm = parentForm;
		this.headerValues = headerValues;
		this.deleteable = true;
		initGUI();
	}

	public PDTitleBar(IDeleteMaster parentForm, HeaderValue headerValues, boolean deleteable) {
		super(3);
		this.parentForm = parentForm;
		this.headerValues = headerValues;
		this.deleteable = deleteable;
		initGUI();
	}

	public PDButton addCommand(String text, IImage image, String toolTipText, String renderId) {
		PDButton btn = new PDButton(text);
		btn.setBorder(PDUtil.emptyBorder());
		btn.setInsets(new Insets(3, 0, 0, 0));
		btn.setHeight(new Extent(16));
		btn.setRolloverEnabled(false);
		btn.setForeground(Color.DARKGRAY);
		btn.setBackground(getBackground());
		if (image != null) {
			btn.setIcon(image.getImage());
		} else {
			btn.setInsets(new Insets(0, 3, 0, 0));
		}
		if (toolTipText != null) {
			btn.setToolTipText(toolTipText);
		}
		if (text != null) {
			btn.setText(text);
			btn.setFont(new Font(Font.VERDANA, Font.PLAIN, new Extent(9)));
			btn.setIconTextMargin(new Extent(2));
		}
		commandRow.add(btn);
		return btn;
	}

	protected void addCommands() {
		//override if applicable
	}

	protected void btnDeleteClicked() {
		PDMessageBox.confirmDeletion().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnDeleteClickedBis();
			}
		});
	}

	protected void btnDeleteClickedBis() {
		parentForm.deleteItem();
	}
	
	protected void initEditingCommands() {
		// extend if needed
	}

	protected void initGUI() {		
		setBorder(PDUtil.emptyBorder());
		
		setWidth(new Extent(100, Extent.PERCENT));
		setInsets(new Insets(0, 1, 0, 1));
		setColumnWidth(0, new Extent(1));

		btnTitle = new Label(EImage16.arrowLeft.getImage());
		//btnTitle.setRenderId(getClass().getSimpleName() + "" + headerValues.id);
		btnTitle.setIconTextMargin(new Extent(0));
		btnTitle.setFont(new Font(Font.VERDANA, 0, new Extent(11)));
		btnTitle.setForeground(Color.BLACK);
		btnTitle.setBackground(PDLookAndFeel.EXP_SECTION_BACKROUND);
		btnTitle.setLineWrap(false);
		
		add(btnTitle);
		if (headerValues != null) {
			setTitle(headerValues.title);
		}

		commandRow = new Row();
		commandRow.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		commandRow.setCellSpacing(new Extent(4));
		add(commandRow);

		initEditingCommands();

		addCommands();

		if (deleteable) {
			commandRow.add(new Strut(6, 0));
			btnDelete = addCommand(null, EImage16.deletee, nls(PDBeanTerms.Delete_item), "delete");
			btnDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnDeleteClicked();
				}
			});
		}
		setBackground(PDLookAndFeel.EXP_SECTION_BACKROUND);		
	}

	public void setTitle(String title) {
		if (btnTitle != null && title != null) {
			if (title.length() > 55) {
				title = title.substring(0, 52) + "...";
			}
			btnTitle.setText(title);
		}
		if (headerValues != null) {
			headerValues.title = title;
		}
	}

	public void setTitleChangeListener(ActionListener titleChangeListener) {
		this.titleChangeListener = titleChangeListener;
	}
	
	public void setBackground(Color c) {
		super.setBackground(c);
		if (commandRow != null) {
			for (Component cp : commandRow.getComponents()) {
				cp.setBackground(c);
			}
		}
		if (btnTitle != null) {
			btnTitle.setBackground(c);
		}
	}

	public Row getCommandRow() {
	    return commandRow;
    }
}