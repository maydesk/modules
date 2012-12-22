/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.sop;

import lombok.soplets.Beanable;
import lombok.soplets.Sop;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.model.MPlug;
import com.maydesk.base.model.MWire;

/**
 * @author chrismay
 */
@Sop(aspects = { Beanable.class, Translatable.class })
public enum SopWire {

	@Soplet(textEN = "Caption", javaType = String.class)
	caption,

	@Soplet(textEN = "Parameter")
	parameter,

	@Soplet(textEN = "Parent wire", javaType = MWire.class)
	parentWire,

	@Soplet(textEN = "Plug", javaType = MPlug.class)
	plug;
}
