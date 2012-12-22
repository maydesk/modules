/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import nextapp.echo.app.Button;
import nextapp.echo.app.Color;
import nextapp.echo.app.Column;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.ResourceImageReference;

import com.maydesk.base.util.PDUtil;

/**
 * @author Alejandro Salas <br>
 *         Created on Sep 27, 2012
 */
public class PDContextMenu extends Component {

	private Column colMenu = new Column();
	private Component applyToComp;

	public PDContextMenu(Component applyToComp) {
		this.applyToComp = applyToComp;

		colMenu.setCellSpacing(new Extent(2));
		add(colMenu);
		add(applyToComp);
	}

	public Button addItem(String text, String img, boolean useTextAsTooltip) {
		Button btn = new Button();

		if (!PDUtil.isEmpty(img)) {
			ImageReference img2 = new ResourceImageReference(img);
			btn.setIcon(img2);
		}
		btn.setRolloverEnabled(true);
		btn.setRolloverBackground(new Color(233, 233, 233));

		if (useTextAsTooltip) {
			btn.setToolTipText(text);

			// If no image we need to display the text
			if (PDUtil.isEmpty(img)) {
				btn.setText(text);
			}
		} else {
			btn.setText(text);
		}
		colMenu.add(btn);
		return btn;
	}

	public Component addItem(Component comp) {
		colMenu.add(comp);
		return comp;
	}

	public Component getApplyToComp() {
		return applyToComp;
	}
}