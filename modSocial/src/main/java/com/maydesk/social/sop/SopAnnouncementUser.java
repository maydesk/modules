/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.social.sop;

import javax.persistence.ManyToOne;

import lombok.soplets.Beanable;
import lombok.soplets.Sop;

import com.maydesk.base.aspects.Translatable;
import com.maydesk.base.model.MUser;
import com.maydesk.social.model.MAnnouncement;

/**
 * @author chrismay
 */
@Sop(aspects = { Translatable.class, Beanable.class })
public enum SopAnnouncementUser {
	@ManyToOne(targetEntity = MUser.class)
	@Soplet(javaType = MUser.class)
	targetUser,

	@ManyToOne(targetEntity = MAnnouncement.class)
	@Soplet(javaType = MAnnouncement.class)
	announcement;
}
