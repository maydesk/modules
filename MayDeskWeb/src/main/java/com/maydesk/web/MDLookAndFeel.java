/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.web;

import nextapp.echo.app.Color;

import com.maydesk.base.util.PDLookAndFeel;

/**
 * @author chrismay
 */
public class MDLookAndFeel extends PDLookAndFeel {

	@Override
	public String getLogo() {
		return "img/CloudDeskLogo.png";
	}

	@Override
	public String getBackgroundImage() {
		return "img/CloudDeskBackground.jpg";
	}

	@Override
	public Color getBackgroundClear() {
		return new Color(215, 220, 217);
	}

	@Override
	public String getVersionInfo() {
		return "Demo";
	}

	@Override
	public String getApplicationName() {
		return "CloudDesk";
	}
}