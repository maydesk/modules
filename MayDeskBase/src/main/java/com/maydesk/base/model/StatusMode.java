package com.maydesk.base.model;

import static com.maydesk.base.util.SopletsResourceBundle.nls;
import lombok.soplets.Sop;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.ResourceImageReference;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.packet.Presence.Type;

import com.maydesk.base.aspects.Translatable;

@Sop(aspects=Translatable.class)
public enum StatusMode {
	
	@Soplet(
		textEN = "Available")
	AVAILABLE(Mode.available, "img/available.png"), 
	
	@Soplet(
		textEN = "Away")
	AWAY(Mode.away, "img/away.png"), 
	
	@Soplet(
		textEN = "Do not Disturb")
	DND(Mode.dnd, "img/dnd.png"), 
	
	@Soplet(
		textEN = "Away (Extended time)")
	XA(Mode.xa, "img/xa.png"),
	
	@Soplet(
		textEN = "Offline")
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