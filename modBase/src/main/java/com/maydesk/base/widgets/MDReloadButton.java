/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import com.maydesk.base.PDApplicationInstance;
import com.maydesk.base.config.IPlugTarget;
import com.maydesk.base.config.XmlBaseEntry;

/**
 * @author chrismay
 */
public class MDReloadButton extends PDButton implements IPlugTarget {

	public MDReloadButton() {
		super("Reload", PDButton.STYLE.TRANSPARENT);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				PDApplicationInstance appInst = (PDApplicationInstance) ApplicationInstance.getActive();
				//XXX appInst.reset();
			}
		});		
	}
	
	
	@Override
	public void initWire(XmlBaseEntry baseEntry) {
	}
}
