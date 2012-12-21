/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import static com.maydesk.base.util.SopletsResourceBundle.nls;

import javax.swing.JLabel;

import com.maydesk.base.aspects.Translatable;

/**
 * A JLabel with built-in NLS support
 * 
 * @author chrismay
 */
public class MyLabel extends JLabel {

	/**
	 * The preferred, NLS-way of initializing a Label
	 */
	public MyLabel(Translatable term, Object... params) {
		super(nls(term, params));
	}

	/**
	 * This constructor should be avoided, use the NLS constructor instead
	 */
	@Deprecated
	public MyLabel(String caption) {
		super(caption);
	}
}
