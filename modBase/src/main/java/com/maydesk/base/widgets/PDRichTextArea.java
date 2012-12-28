/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import nextapp.echo.extras.app.RichTextArea;

import com.maydesk.base.util.IChangeSupportable;
import com.maydesk.base.util.PDBinding;

/**
 * @author chrismay
 */
public class PDRichTextArea extends RichTextArea implements IChangeSupportable<String> {

	private PDBinding changeSupport;

	@Override
	public String getValue() {
		return getText();
	}

	@Override
	public void setValue(String text) {
		setText(text);
	}

	@Override
	public PDBinding getChangeSupport() {
		return changeSupport;
	}

	@Override
	public void setChangeSupport(PDBinding changeSupport) {
		this.changeSupport = changeSupport;
	}

	@Override
	public String getPropertyName() {
		return "text";
	}

	@Override
	public void setEditable(boolean editable) {
		setEnabled(editable);
	}
}
