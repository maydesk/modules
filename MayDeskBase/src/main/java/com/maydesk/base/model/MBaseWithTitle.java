/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.model;

import javax.persistence.MappedSuperclass;

/**
 * @author Alejandro Salas
 * Created on 08.10.2007
 */
@MappedSuperclass
public abstract class MBaseWithTitle extends MBase {

	protected String cachedTitle;
	protected String cachedDescription;

	public String getCachedDescription() {
		return cachedDescription;
	}

	public void setCachedDescription(String cachedDescription) {
		this.cachedDescription = cachedDescription;
	}

	public String getCachedTitle() {
		return cachedTitle;
	}

	public void setCachedTitle(String cachedTitle) {
		this.cachedTitle = cachedTitle;
	}

	public abstract String createCachedTitle();

	public String createCachedDescription() {
		return "";
	}

	@Override
	public String toString() {
		return cachedTitle;
	}

	public void updateCachedValues() {
		cachedTitle = createCachedTitle();
		cachedDescription = createCachedDescription();
	}
}