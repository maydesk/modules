/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import nextapp.echo.app.Extent;
import nextapp.echo.app.Label;

import com.maydesk.base.model.MWire;
import com.maydesk.base.sop.enums.EImage16;
import com.maydesk.base.util.IPlugTarget;

import echopoint.ContainerEx;

/**
 * @author chrismay
 */
public class PDRecycleBin extends ContainerEx implements IPlugTarget {

	public PDRecycleBin() {
		setTop(new Extent(20));
		setLeft(new Extent(20));
		setWidth(new Extent(25));
		setHeight(new Extent(25));

		Label lblRecycleBin = new Label(EImage16.recycle_bin.getImage());
		add(lblRecycleBin);
	}

	@Override
	public void initWire(MWire parentWire) {
	}
}
