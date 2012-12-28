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

/**
 * @author chrismay
 */
public interface ILookAndFeel {

	public Color getHighlight();

	public Color getBackgroundDark();

	public Color getBackgroundClear();

	public Color getBackgroundDark2();

	public IImage getFooterFillImage();

	public String getLogo();

	public IImage getProfideskIcon();

	public Color getForeground();

	public String getApplicationName();
}
