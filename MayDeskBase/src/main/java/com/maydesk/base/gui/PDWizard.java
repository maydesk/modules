/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import java.util.List;
import java.util.Vector;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Color;
import nextapp.echo.app.ContentPane;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.Row;
import nextapp.echo.app.SplitPane;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;
import nextapp.echo.app.layout.SplitPaneLayoutData;
import nextapp.echo.extras.app.TransitionPane;

import com.maydesk.base.PDApplicationInstance;
import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.internal.PDFooterButton;
import com.maydesk.base.model.MTask;
import com.maydesk.base.sop.gui.StandardTerms;
import com.maydesk.base.util.PDLookAndFeel;

/**
 * A wizard-like window, where the user can accomplish tasks step-by-step in a
 * guide way
 * 
 * @author chrismay
 */
public class PDWizard extends PDWindowPane {

	protected PDFooterButton btnBack;
	protected PDFooterButton btnNext;
	protected List<IWizardPanel> panels = new Vector<IWizardPanel>();
	protected int pageIndex = 0;
	protected ContentPane container;
	protected MTask originTask;
	protected TransitionPane footerTransitionPane;
	protected SplitPane footerSplit;

	public PDWizard() {
		this(null);
	}

	public PDWizard(MTask task) {
		this.originTask = task;
		initGUI();
	}

	protected void addPanel(IWizardPanel pnl) {
		panels.add(pnl);
	}

	protected void initGUI() {
		setWidth(new Extent(650));
		setHeight(new Extent(400));
		setResizable(false);

		SplitPane split = new SplitPane(SplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP);
		split.setSeparatorPosition(new Extent(24));
		add(split);

		footerSplit = new SplitPane(SplitPane.ORIENTATION_HORIZONTAL_RIGHT_LEFT);
		footerSplit.setSeparatorPosition(new Extent(170));
		footerSplit.setBackground(PDLookAndFeel.BACKGROUND_COLOR);
		split.add(footerSplit);

		Row footerRow = new Row();
		footerRow.setAlignment(new Alignment(Alignment.RIGHT, Alignment.DEFAULT));
		footerRow.setCellSpacing(new Extent(6));
		footerRow.setBackground(PDLookAndFeel.BACKGROUND_COLOR);
		footerRow.setInsets(new Insets(0, 1, 12, 1));
		SplitPaneLayoutData spld = new SplitPaneLayoutData();
		spld.setOverflow(SplitPaneLayoutData.OVERFLOW_HIDDEN);
		footerRow.setLayoutData(spld);
		footerSplit.add(footerRow);

		footerTransitionPane = new TransitionPane();
		footerTransitionPane.setType(TransitionPane.TYPE_CAMERA_PAN_LEFT);
		footerSplit.add(footerTransitionPane);

		btnBack = new PDFooterButton(StandardTerms.Back);
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnBackClicked();
			}
		});
		footerRow.add(btnBack);

		btnNext = new PDFooterButton(StandardTerms.Next);
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnNextClicked();
			}
		});
		footerRow.add(btnNext);

		container = new ContentPane();
		container.setInsets(new Insets(6, 9, 3, 6));
		split.add(container);
	}

	protected void btnNextClicked() {

		IWizardPanel page = panels.get(pageIndex);

		if (!page.doNextAction()) {
			return;
		}

		page.applyToModel();
		Translatable error = page.getError();
		if (error != null) {
			showError(error);
			return;
		}
		page.applyToModel2();

		while (++pageIndex < panels.size()) {
			if (panels.get(pageIndex).isApplicable()) {
				showPage();
				return;
			}
		}
		setVisible(false);
		fireWindowClosing();
	}

	protected void btnBackClicked() {
		if (!panels.get(pageIndex).doBackAction())
			return;

		while (--pageIndex >= 0) {
			if (panels.get(pageIndex).isApplicable()) {
				break;
			}
		}
		showPage(false);
	}

	protected void showPage() {
		showPage(true);
	}

	protected void showPage(boolean readFromModel) {
		container.removeAll();

		footerTransitionPane.removeAll();
		footerTransitionPane.add(new Label());

		IWizardPanel page = panels.get(pageIndex);
		while (!page.isApplicable()) {
			page = panels.get(++pageIndex);
		}

		if (readFromModel) {
			page.readFromModel();
		}
		container.add(page.getComponent());

		if (page.getNextCaption() == null) {
			btnNext.setVisible(false);
		} else {
			btnNext.setVisible(true);
			btnNext.setText(page.getNextCaption());
			btnNext.setWidth(new Extent(page.getNextButtonWidth()));
		}
		if (page.getBackCaption() == null) {
			btnBack.setVisible(false);
		} else {
			btnBack.setVisible(true);
			btnBack.setText(page.getBackCaption());
			btnBack.setWidth(new Extent(page.getBackButtonWidth()));
		}

		if (page.getFocusComponent() != null) {
			PDApplicationInstance.getActivePD().setFocusedComponent(page.getFocusComponent());
		}
	}

	public void showError(Translatable error) {
		footerTransitionPane.removeAll();
		Label lblError = new Label("   " + nls(error)); //$NON-NLS-1$
		lblError.setForeground(Color.WHITE);
		lblError.setFormatWhitespace(true);
		footerTransitionPane.add(lblError);
	}

	public void setTitle(Translatable title) {
		super.setTitle(nls(title));
	}
}
