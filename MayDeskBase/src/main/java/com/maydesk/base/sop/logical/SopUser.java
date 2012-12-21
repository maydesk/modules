/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.sop.logical;

import lombok.soplets.Beanable;
import lombok.soplets.Sop;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.sop.enums.SopGender;

/**
 * @author chrismay
 */
@Sop(aspects = { Translatable.class, Beanable.class })
public enum SopUser {

	@Soplet(textDE = "Ort", textEN = "City")
	city,

	@Soplet(textDE = "Firma", textEN = "Company")
	company,

	@Soplet(textEN = "Country", textDE = "Land")
	country,

	@Soplet(textDE = "Email", textEN = "Email")
	email,

	@Soplet(textDE = "Fax", textEN = "Fax")
	fax,

	@Soplet(textDE = "Jabber ID", textEN = "Jabber ID")
	jabberId,

	@Soplet(textDE = "Vorname", textEN = "First name")
	firstName,

	@Soplet(textDE = "Nachname", textEN = "Last name")
	lastName,

	@Soplet(textDE = "Geschlecht", textEN = "Gender", javaType = SopGender.class)
	gender,

	@Soplet(textDE = "Login", textEN = "Login")
	login,

	@Soplet(textDE = "Telefon", textEN = "Phone")
	phone,

	@Soplet(textDE = "Strasse", textEN = "Street")
	street,

	@Soplet(textEN = "Zip code", textDE = "PLZ")
	zipCode,

	@Soplet(textEN = "Organization")
	organization;
}
