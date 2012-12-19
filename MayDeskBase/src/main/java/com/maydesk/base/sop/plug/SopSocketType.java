package com.maydesk.base.sop.plug;

import lombok.soplets.Sop;

import com.maydesk.base.aspects.Translatable;

/**
 * @author chrismay
 */
@Sop(aspects = { Translatable.class })
public enum SopSocketType {

	@Soplet(textEN = "Base")
	base,

	@Soplet(textEN = "Menu Entry")
	menu,

	@Soplet(textEN = "Panel")
	panel,

	@Soplet(textEN = "Action")
	action,

	@Soplet(textEN = "Floating Form")
	form,

	@Soplet(textEN = "Tab")
	tab,

	@Soplet(textEN = "Desktop (internal)")
	desktop,

	@Soplet(textEN = "Login Panel (internal)")
	loginPanel;

	@Override
	public String toString() {
		return textEN();
	}
}
