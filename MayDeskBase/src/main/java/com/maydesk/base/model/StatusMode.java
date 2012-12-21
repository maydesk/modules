/* This file is part of the MayDesk project.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.*/

package com.maydesk.base.model;

import static com.maydesk.base.util.SopletsResourceBundle.nls;
import lombok.soplets.Sop;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.ResourceImageReference;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.packet.Presence.Type;

import com.maydesk.base.aspects.Translatable;

/**
 * @author Alejandro Salas
 */
@Sop(aspects = Translatable.class)
public enum StatusMode {

	@Soplet(textEN = "Available")
	AVAILABLE(Mode.available, "img/available.png"),

	@Soplet(textEN = "Away")
	AWAY(Mode.away, "img/away.png"),

	@Soplet(textEN = "Do not Disturb")
	DND(Mode.dnd, "img/dnd.png"),

	@Soplet(textEN = "Away (Extended time)")
	XA(Mode.xa, "img/xa.png"),

	@Soplet(textEN = "Offline")
	OFFLINE(null, "img/offline.png");

	private Mode mode;
	private ImageReference img;

	private StatusMode(Mode mode, String imgStr) {
		this.mode = mode;
		img = new ResourceImageReference(imgStr);
	}

	public Mode getMode() {
		return mode;
	}

	public ImageReference getImg() {
		return img;
	}

	@Override
	public String toString() {
		return nls(this);
	}

	public static StatusMode findByPresence(Presence presence) {
		if (presence.getType() != Type.available) {
			return StatusMode.OFFLINE;
		}

		// When logging in Type is available, but Mode is null
		// Don't know why the packet sent is not taking effect
		Mode mode = presence.getMode();
		if (mode == null) {
			return StatusMode.AVAILABLE;
		}

		for (StatusMode statusMode : StatusMode.values()) {
			if (statusMode.mode == mode) {
				return statusMode;
			}
		}

		return StatusMode.AVAILABLE;
	}
}