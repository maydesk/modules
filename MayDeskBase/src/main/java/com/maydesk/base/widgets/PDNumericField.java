/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import java.text.NumberFormat;

import nextapp.echo.app.Alignment;
import nextapp.echo.app.Extent;

import com.maydesk.base.util.PDFormat;

/**
 * @author Alejandro Salas
 */
public class PDNumericField extends PDTextField {

	protected NumberFormat nf;

	public PDNumericField() {
		this(2);
		setWidth(new Extent(60));
		setAlignment(Alignment.ALIGN_RIGHT);
	}

	public PDNumericField(int digits) {
		nf = PDFormat.getNumberFormat(digits);
	}

	public PDNumericField(NumberFormat nf) {
		this.nf = nf;
	}

	public double getNumber() {
		try {
			return nf.parse(getText()).doubleValue();
		} catch (Exception e) {
			return 0.0;
		}
	}

	public void setNumber(double value) {
		setText(nf.format(value));
	}

	@Override
	public Object getValue() {
		return getNumber();
	}

	@Override
	public void setValue(Object value) {
		setNumber((Double) value);
	}
}