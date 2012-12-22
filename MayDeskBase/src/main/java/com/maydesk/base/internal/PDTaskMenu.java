/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.internal;

import nextapp.echo.app.Column;
import nextapp.echo.app.Extent;
import nextapp.echo.app.Insets;

import com.maydesk.base.model.MTask;

import echopoint.ContainerEx;

/**
 * @author chrismay
 */
public class PDTaskMenu extends ContainerEx {

	protected Column colMenu;

	public PDTaskMenu() {
		setWidth(new Extent(200));
		setOutsets(new Insets(0, 100, 0, 0));
		setInsets(new Insets(6, 6, 6, 6));
		colMenu = new Column();
		colMenu.setCellSpacing(new Extent(0));
		add(colMenu);
	}

	public PDTaskEntry addTask(final MTask task, boolean flash) {
		PDTaskEntry te = new PDTaskEntry(task);
		colMenu.add(te);
		return te;
	}

	@Override
	public void clear() {
		colMenu.removeAll();
	}
}
