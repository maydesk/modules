/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import java.util.Date;

import com.maydesk.base.util.IChangeSupportable;
import com.maydesk.base.util.PDBinding;

import echopoint.jquery.DateField;

/**
 * @author Alejandro Salas
 */
public class PDDateField extends DateField implements IChangeSupportable<Date> {

	private PDBinding changeSupport;

	public PDDateField() {
		setDateFormat("dd.MM.yyyy");
	}

	public PDDateField(String renderId) {
		this();
		setRenderId(renderId);
	}

	@Override
	public void setDate(Date date) {
		if (date == null) {
			return; // XXX this is a bug!
		}
		super.setDate(date);
	}

	@Override
	public Date getValue() {
		return getDate();
	}

	@Override
	public void setValue(Date date) {
		setDate(date);
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
		return "date";
	}
}
