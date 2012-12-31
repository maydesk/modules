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

import com.maydesk.base.config.IPlugTarget;
import com.maydesk.base.config.XmlBaseEntry;
import com.maydesk.base.config.XmlDesktopItem;
import com.maydesk.base.sop.enums.EImage16;

import echopoint.ContainerEx;

/**
 * @author chrismay
 */
public class PDRecycleBin extends ContainerEx implements IPlugTarget {

	@Override
	public void initWire(XmlBaseEntry baseEntry) {
		XmlDesktopItem di = (XmlDesktopItem)baseEntry;
		setTop(new Extent(di.getTop()));
		setLeft(new Extent(di.getLeft()));
		setWidth(new Extent(25));
		setHeight(new Extent(25));
		Label lblRecycleBin = new Label(EImage16.recycle_bin.getImage());
		add(lblRecycleBin);
	}
}
