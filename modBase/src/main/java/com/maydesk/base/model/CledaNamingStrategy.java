/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.model;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ImprovedNamingStrategy;

/**
 * @author Demian Guitierrez
 */
public class CledaNamingStrategy extends ImprovedNamingStrategy {

	protected Configuration configuration;

	public CledaNamingStrategy(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public String tableName(String tableName) {
		String url = (String) configuration.getProperties().get("hibernate.connection.url");

		// Oracle should use a "username"."tablename" schema or we will have
		// problems with different unrelated databases with common table names
		if (url.contains("oracle")) {
			return configuration.getProperty("hibernate.connection.username") + "." + super.tableName(tableName);
		}

		return super.tableName(tableName);
	}
}