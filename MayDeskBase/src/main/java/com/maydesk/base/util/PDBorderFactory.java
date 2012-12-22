/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.util;

import nextapp.echo.app.Border;
import nextapp.echo.app.Border.Side;
import nextapp.echo.app.Color;

/**
 * @author chrismay
 */
public class PDBorderFactory {

	public static Border getBorderError() {
		Side[] sides = new Side[4];
		sides[0] = new Side(0, Color.RED, Border.STYLE_SOLID);
		sides[1] = new Side(0, Color.RED, Border.STYLE_SOLID);
		sides[2] = new Side(2, Color.RED, Border.STYLE_SOLID);
		sides[3] = new Side(5, Color.RED, Border.STYLE_SOLID);
		return new Border(sides);
	}

	public static Border getBorder() {
		Side[] sides = new Side[4];
		sides[0] = new Side(0, Color.LIGHTGRAY, Border.STYLE_SOLID);
		sides[1] = new Side(0, Color.LIGHTGRAY, Border.STYLE_SOLID);
		sides[2] = new Side(2, Color.LIGHTGRAY, Border.STYLE_DOTTED);
		sides[3] = new Side(5, new Color(220, 220, 220), Border.STYLE_SOLID);
		return new Border(sides);
	}

	public static Border getBorderActive() {
		Side[] sides = new Side[4];
		sides[0] = new Side(0, Color.LIGHTGRAY, Border.STYLE_SOLID);
		sides[1] = new Side(0, Color.LIGHTGRAY, Border.STYLE_SOLID);
		sides[2] = new Side(2, Color.LIGHTGRAY, Border.STYLE_DOTTED);
		sides[3] = new Side(5, new Color(240, 140, 205), Border.STYLE_SOLID);
		return new Border(sides);
	}
}
