/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.util;

import echopoint.model.AutoLookupSelectModel.EntrySelect;

/**
 * @author chrismay
 */
public class SimpleEntrySelect implements EntrySelect {

	String idAsString;
	String value;
	String searchVal;

	public SimpleEntrySelect(String idAsString, String value, String searchVal) {
		this.idAsString = idAsString;
		this.value = value;
		this.searchVal = searchVal;
	}

	@Override
	public String getKey() {
		return idAsString;
	}

	@Override
	public String getSearchVal() {
		return searchVal;
	}

	@Override
	public String getValue() {
		return value;
	}

}