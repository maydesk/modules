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
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.Label;
import nextapp.echo.app.ResourceImageReference;

import com.maydesk.base.config.IPlugTarget;
import com.maydesk.base.config.XmlBaseEntry;
import com.maydesk.base.config.XmlDesktopItem;

import echopoint.ContainerEx;
import echopoint.able.Positionable;

/**
 * @author chrismay
 */
public class MDDesktopImage extends ContainerEx implements IPlugTarget {

	@Override
	public void initWire(XmlBaseEntry baseEntry) {
		setPosition(Positionable.ABSOLUTE);
		XmlDesktopItem di = (XmlDesktopItem)baseEntry;
		setBottom(new Extent(di.getBottom()));
		setRight(new Extent(di.getRight()));
		ImageReference img = new ResourceImageReference(di.getValue());
		Label lblRecycleBin = new Label(img);
		add(lblRecycleBin);
	}
}
