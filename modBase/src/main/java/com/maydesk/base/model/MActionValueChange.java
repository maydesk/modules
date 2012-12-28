/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.model;

import javax.persistence.Entity;
import javax.persistence.Transient;

import com.maydesk.base.util.PDBinding;

/**
 * @author chrismay
 */
@Entity
public class MActionValueChange extends MAction {

	private PDBinding changeSupport;
	private String targetClass;
	private int targetId;
	private String targetField;
	private String valueClass;

	public void setChangeSupport(PDBinding changeSupport) {
		this.changeSupport = changeSupport;
	}

	@Override
	public void redoAction() {
		// changeSupport.redoChange(this);
	}

	@Override
	public void undoAction() {
		// changeSupport.undoChange(this);
	}

	public String getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}

	public String getTargetField() {
		return targetField;
	}

	public void setTargetField(String targetField) {
		this.targetField = targetField;
	}

	public int getTargetId() {
		return targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public String getValueClass() {
		return valueClass;
	}

	public void setValueClass(String valueClass) {
		this.valueClass = valueClass;
	}

	@Transient
	public PDBinding getChangeSupport() {
		return changeSupport;
	}

	@Transient
	public Object getNewValue() {
		if (valueClass.equals(int.class.getCanonicalName())) {
			return getNewIntValue();
		} else if (valueClass.equals(double.class.getCanonicalName())) {
			return getNewDoubleValue();
		} else {
			return getNewStringValue();
		}
	}

	@Transient
	public Object getOldValue() {
		if (valueClass.equals(int.class.getCanonicalName())) {
			return getOldIntValue();
		} else if (valueClass.equals(double.class.getCanonicalName())) {
			return getOldDoubleValue();
		} else {
			return getOldStringValue();
		}
	}

	public void setOldValue(Object value) {
		if (valueClass.equals(int.class.getCanonicalName())) {
			setOldIntValue((Integer) value);
		} else if (valueClass.equals(double.class.getCanonicalName())) {
			setOldDoubleValue((Double) value);
		} else {
			setOldStringValue(value + "");
		}
	}

	public void setNewValue(Object value) {
		if (valueClass.equals(int.class.getCanonicalName())) {
			setNewIntValue((Integer) value);
		} else if (valueClass.equals(double.class.getCanonicalName())) {
			setNewDoubleValue((Double) value);
		} else {
			setNewStringValue(value + "");
		}
	}

	@Override
	public String toString() {
		return "Wert geändert von " + getOldValue() + " nach " + getNewValue();
	}
}