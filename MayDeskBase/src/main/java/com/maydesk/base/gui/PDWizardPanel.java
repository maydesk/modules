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
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.model.MBase;
import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDLabel;

import echopoint.ContainerEx;

/**
 * A panel which is placed inside the PDWizard
 * 
 * @author chrismay
 */
public abstract class PDWizardPanel implements IWizardPanel {

	protected String backLabel;
	protected String nextLabel;
	protected PDLabel lblTitle;
	protected Label lblInfo;
	protected Component component;
	private ContainerEx conInfo;

	public PDWizardPanel(Translatable backLabel, Translatable nextLabel) {
		this(null, backLabel, nextLabel);
	}

	public PDWizardPanel(Translatable title, Translatable backLabel, Translatable nextLabel) {
		try {
			component = getComponentClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.backLabel = nls(backLabel);
		this.nextLabel = nls(nextLabel);
		if (component instanceof Column) {
			((Column) component).setCellSpacing(new Extent(6));
		}
		lblTitle = new PDLabel(title, PDLabel.STYLE.HEADER_1);
		component.add(lblTitle);

		conInfo = new ContainerEx();
		conInfo.setBackground(new Color(244, 244, 244));
		conInfo.setBorder(PDUtil.getGreyBorder());
		conInfo.setInsets(new Insets(3));
		conInfo.setWidth(new Extent(95, Extent.PERCENT));
		conInfo.setVisible(false);
		component.add(conInfo);

		lblInfo = new Label(); // PDLabel(PDLabel.BORDERED);
		lblInfo.setLineWrap(true);
		conInfo.add(lblInfo);
	}

	protected Class<? extends Component> getComponentClass() {
		return Column.class;
	}

	@Override
	public Component getComponent() {
		return component;
	}

	protected void setTitle(Translatable title) {
		lblTitle.setText(nls(title));
	}

	@Deprecated
	protected void setTitle(String text) {
		lblTitle.setText(text);
	}

	protected void setInfo(Translatable info, Object... params) {
		lblInfo.setText(nls(info, params));
		conInfo.setVisible(true);
	}

	@Deprecated
	protected void setInfo(String text) {
		lblInfo.setText(text);
		conInfo.setVisible(true);
	}

	@Override
	public String getNextCaption() {
		return nextLabel;
	}

	@Override
	public String getBackCaption() {
		return backLabel;
	}

	@Override
	public boolean doBackAction() {
		return true;
	}

	@Override
	public void readFromModel() {
	}

	@Override
	public void applyToModel() {
	}

	@Override
	public void applyToModel2() {
	}

	@Override
	public Translatable getError() {
		return null;
	}

	@Override
	public boolean isApplicable() {
		return true;
	}

	/**
	 * Overwrite if necessary
	 */
	@Override
	public int getNextButtonWidth() {
		return 80;
	}

	/**
	 * Overwrite if necessary
	 */
	@Override
	public int getBackButtonWidth() {
		return 80;
	}

	public void setEditing(boolean isEditing) {
	}

	@Override
	public Component getFocusComponent() {
		return null;
	}

	@Override
	public void readFromModel(MBase model) {
		// not used here
	}

	@Override
	public boolean doNextAction() {
		return true;
	}

	@Override
	public Class getModelClass() {
		// TODO Auto-generated method stub
		return null;
	}

	public void add(Component c) {
		component.add(c);
	}
}
