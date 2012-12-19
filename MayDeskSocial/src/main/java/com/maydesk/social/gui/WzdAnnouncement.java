/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.social.gui;

import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.FillImage;
import nextapp.echo.app.Insets;
import nextapp.echo.app.ResourceImageReference;
import nextapp.echo.app.TextField;

import com.maydesk.base.PDUserSession;
import com.maydesk.base.gui.PDWizard;
import com.maydesk.base.gui.PDWizardPanel;
import com.maydesk.base.model.MWire;
import com.maydesk.base.sop.gui.PDBeanTerms;
import com.maydesk.base.sop.gui.StandardTerms;
import com.maydesk.base.util.IPlugTarget;
import com.maydesk.base.widgets.PDGrid;
import com.maydesk.social.dao.DaoSocial;
import com.maydesk.social.model.MAnnouncement;
import com.maydesk.social.sop.SopAnnouncement;

import echopoint.TextArea;
import echopoint.able.Positionable;

/**
 * @author chrismay
 */
public class WzdAnnouncement extends PDWizard implements IPlugTarget {

	private MAnnouncement announcement;

	public WzdAnnouncement() {
		super();
		setHeight(new Extent(320));
		setWidth(new Extent(420));
		setTitle("Create New Announcement");

		addPanel(new Panel1());
		addPanel(new Panel2());
		addPanel(new Panel3());
		showPage(true);
	}

	class Panel1 extends PDWizardPanel {

		private TextField txtTitle;
		private TextArea txtContent;

		public Panel1() {
			super(null, StandardTerms.Next);
			setInfo("With this wizard you can create a new announcement");

			PDGrid grd = new PDGrid(2);
			add(grd);

			grd.addLabel(SopAnnouncement.title);
			grd.add(txtTitle = new TextField());

			grd.addLabel(SopAnnouncement.text);
			grd.add(txtContent = new TextArea());
			txtContent.setHeight(new Extent(110));
			txtContent.setWidth(new Extent(290));
		}

		@Override
		public Component getFocusComponent() {
			return txtTitle;
		}

		@Override
		public void applyToModel() {
			announcement = new MAnnouncement();
			announcement.setTitle(txtTitle.getText());
			announcement.setText(txtContent.getText());
			announcement.setAuthor(PDUserSession.getInstance().getUser());
		}
	}

	class Panel2 extends PDWizardPanel {

		PnlAnnouncement pnl;

		public Panel2() {
			super(PDBeanTerms.Preview, StandardTerms.Back, PDBeanTerms.Publish_Now);
			// setInfo("Preview");

			pnl = new PnlAnnouncement();
			pnl.setPosition(Positionable.ABSOLUTE);
			pnl.setLeft(new Extent(3));
			pnl.setTop(new Extent(32));
			pnl.setWidth(new Extent(350));
			pnl.setHeight(new Extent(170));
			pnl.setBackgroundImage(new FillImage(new ResourceImageReference("img/CloudDeskBackground2.jpg")));
			pnl.setInsets(new Insets(6));
			add(pnl);
		}

		@Override
		public void readFromModel() {
			pnl.setContent(announcement);
		}

		@Override
		public void applyToModel2() {
			DaoSocial.getInstance().createAnnouncement(announcement);
		}
	}

	class Panel3 extends PDWizardPanel {
		public Panel3() {
			super(null, StandardTerms.Done);
			setInfo("Your announcement has been published!");
		}
	}

	@Override
	public void initWire(MWire parentWire) {
		// setTitle(parentWire.getCaption());
	}
}