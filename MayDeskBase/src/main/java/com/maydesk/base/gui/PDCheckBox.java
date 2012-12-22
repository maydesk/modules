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
import nextapp.echo.app.CheckBox;
import nextapp.echo.app.Color;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Font;
import nextapp.echo.app.Font.Typeface;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.util.IChangeSupportableWithAction;
import com.maydesk.base.util.PDBinding;
import com.maydesk.base.util.PDBorderFactory;

/**
 * @author chrismay
 */
public class PDCheckBox extends CheckBox implements IChangeSupportableWithAction<Boolean> {

	private PDBinding changeSupport;

	public PDCheckBox() {
		initGui();
	}

	public PDCheckBox(Translatable caption, Object... params) {
		initGui();
		setText(nls(caption, params));
	}

	@Deprecated
	public PDCheckBox(String caption) {
		initGui();
		setText(caption);
	}

	protected void initGui() {
		setFont(new Font(new Typeface("Arial"), Font.BOLD, new Extent(12)));
		setForeground(Color.DARKGRAY);
		setDisabledForeground(Color.LIGHTGRAY);
		setBorder(PDBorderFactory.getBorder());
	}

	@Override
	public Boolean getValue() {
		return isSelected();
	}

	@Override
	public void setValue(Boolean value) {
		if (value != null) {
			setSelected(value);
		} else {
			setSelected(false);
		}
	}

	@Override
	public PDBinding getChangeSupport() {
		return changeSupport;
	}

	@Override
	public void setChangeSupport(PDBinding changeSupport) {
		this.changeSupport = changeSupport;
	}

	@Override
	public String getPropertyName() {
		return "selected";
	}

	// @Override
	@Override
	public void setEditable(boolean editable) {
		setEnabled(editable);
	}
}
