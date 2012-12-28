/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.gui;

import nextapp.echo.app.Component;

/**
 * A gereral purpose floating window
 * 
 * @author chrismay
 */
public class PDSvgImage extends Component {

	public static final String PROPERTY_SVG_DATA = "svg_data";

	public PDSvgImage() {
	}

	public byte[] getSvgData() {
		return (byte[]) get(PROPERTY_SVG_DATA);
	}

	public void setSvgData(byte[] data) {
		set(PROPERTY_SVG_DATA, data);
	}
}
