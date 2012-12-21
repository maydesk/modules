/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Grid;
import nextapp.echo.app.Insets;
import nextapp.echo.app.Label;
import nextapp.echo.app.layout.GridLayoutData;
import nextapp.echo.app.text.TextComponent;

import com.maydesk.base.aspects.Translatable;

/**
 * @author Alejandro Salas
 */
public class PDGrid extends Grid {

	public PDGrid(int cols) {
		super(cols);
		setInsets(new Insets(3));
	}

	public void add(Component c, Alignment alignment) {
		GridLayoutData gld = (GridLayoutData) c.getLayoutData();
		if (gld == null) {
			gld = new GridLayoutData();
			c.setLayoutData(gld);
		}
		gld.setAlignment(alignment);
		add(c);
	}

	public <T extends Component> T add2(T c) {
		add(c);
		return c;
	}

	public <T extends Component> T addFill(T c) {
		GridLayoutData gld = (GridLayoutData) c.getLayoutData();
		if (gld == null) {
			gld = new GridLayoutData();
			c.setLayoutData(gld);
		}
		if (c instanceof TextComponent) {
			((TextComponent) c).setWidth(new Extent(100, Extent.PERCENT));
		}
		gld.setColumnSpan(4); // GridLayoutData.SPAN_FILL);
		add(c);
		return c;
	}

	public PDLabel addLabel(String caption) {
		PDLabel lbl = new PDLabel(caption, PDLabel.STYLE.FIELD_LABEL);
		add(lbl, Alignment.ALIGN_TOP);
		return lbl;
	}

	public PDLabel addLabel(Translatable term, Object... params) {
		PDLabel lbl = new PDLabel(term, params);
		add(lbl, Alignment.ALIGN_TOP);
		return lbl;
	}

	public void addEmpty() {
		add(new Label());
	}
}
