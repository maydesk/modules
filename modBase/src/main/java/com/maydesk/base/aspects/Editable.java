/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.aspects;

/**
 * @author chrismay
 */
public @interface Editable {

	/**
	 * Max length of the field (applies only to text and decimal types)
	 */
	public int length() default 0;

	/**
	 * Is the field mandatory?
	 */
	public boolean mandatory() default false;

	/**
	 * Is the field read-only?
	 */
	public boolean readOnly() default false;

}
