/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.widgets;

import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.model.MBase;
import com.maydesk.base.util.IChangeSupportableWithAction;
import com.maydesk.base.util.PDBinding;

import echopoint.AutoLookupSelectField;

/**
 * @author chrismay
 */
public class PDAutoLookupField<T extends MBase> extends AutoLookupSelectField implements IChangeSupportableWithAction<T> {

	private PDBinding changeSupport;
	private Class modelClazz;

	public PDAutoLookupField(Class<T> modelClazz) {
		this.modelClazz = modelClazz;
	}

	@Override
	public void setValue(T value) {
		setKey(value.getIdAsString());
		setText(value.toString());
	}

	@Override
	public T getValue() {
		String idAsString = super.getKey();
		if (idAsString == null) {
			return null;
		}
		return (T) PDHibernateFactory.getSession().load(modelClazz, Integer.parseInt(idAsString));
	}

	@Override
	public String getPropertyName() {
		return "key";
	}

	@Override
	public void setChangeSupport(PDBinding changeSupport) {
		this.changeSupport = changeSupport;
	}

	@Override
	public PDBinding getChangeSupport() {
		return changeSupport;
	}
}
