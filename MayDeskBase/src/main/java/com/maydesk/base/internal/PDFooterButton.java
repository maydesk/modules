/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.internal;

import static nextapp.echo.app.Alignment.CENTER;
import static nextapp.echo.app.Alignment.DEFAULT;
import nextapp.echo.app.Alignment;
import nextapp.echo.app.Extent;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.widgets.PDPushButton;

/**
 * @author chrismay
 */
public class PDFooterButton extends PDPushButton {

	public PDFooterButton(Translatable caption) {
		super(caption);
		setAlignment(new Alignment(CENTER, DEFAULT));
		setHeight(new Extent(22));
	}
}
