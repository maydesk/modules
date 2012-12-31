/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.util;

import nextapp.echo.app.Color;

import com.maydesk.base.sop.enums.EImage16;

/**
 * @author chrismay
 */
public abstract class PDLookAndFeel implements ILookAndFeel {

	public static final Color HIGHLIGHT_COLOR = new Color(255, 243, 194);
	public static final Color BACKGROUND_COLOR = new Color(114, 136, 173);
	public static final Color LIGHT_BACKGROUND_COLOR = new Color(244, 250, 250);
	public static final Color ROLLOVER_COLOR = new Color(94, 116, 153);
	public static final Color DISABLED_BACKGROUND = new Color(245, 245, 245);
	public static final Color DISABLED_FOREGROUND = new Color(60, 60, 60);
	public static final Color EXP_SECTION_BACKROUND = new Color(180, 210, 225);

	@Override
	public Color getBackgroundClear() {
		return LIGHT_BACKGROUND_COLOR;
	}

	@Override
	public Color getBackgroundDark() {
		return BACKGROUND_COLOR;
	}

	@Override
	public Color getBackgroundDark2() {
		return ROLLOVER_COLOR;
	}

	@Override
	public Color getHighlight() {
		return HIGHLIGHT_COLOR;
	}

	@Override
	public IImage getFooterFillImage() {
		return EImage16.footerBackground;
	}

	public IImage getFooterLogo() {
		return EImage16.profideskFooter2;
	}

	@Override
	public IImage getProfideskIcon() {
		return EImage16.profideskFooter2;
	}

	@Override
	public Color getForeground() {
		return Color.WHITE;
	}

	public String getBackgroundImage() {
		return "img/MayDeskBackground.jpg";
	}
}
