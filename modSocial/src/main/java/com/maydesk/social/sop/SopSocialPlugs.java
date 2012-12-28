/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.social.sop;

import lombok.soplets.Sop;

import com.maydesk.base.sop.plug.Plugable;
import com.maydesk.base.sop.plug.SopSocketType;
import com.maydesk.social.gui.PnlAnnouncement;
import com.maydesk.social.gui.PnlUserStatus;

/**
 * @author chrismay
 */
@Sop(aspects = Plugable.class)
public enum SopSocialPlugs {

	@Soplet(plugTitle = "Display Announcement", plugType = SopSocketType.panel, editorClass = PnlAnnouncement.class)
	displayAnnouncement,
	
	@Soplet(plugTitle = "User status", plugType = SopSocketType.panel, editorClass = PnlUserStatus.class)
	userStatus,
}