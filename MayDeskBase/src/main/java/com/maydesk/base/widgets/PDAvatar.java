/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.widgets;

import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.Extent;
import nextapp.echo.app.ImageReference;
import nextapp.echo.app.event.ActionEvent;
import nextapp.echo.app.event.ActionListener;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.packet.VCard;

import com.maydesk.base.PDDesktop;
import com.maydesk.base.PDHibernateFactory;
import com.maydesk.base.PDUserSession;
import com.maydesk.base.dao.DaoUser;
import com.maydesk.base.gui.user.DlgUserSettings;
import com.maydesk.base.model.MAvatar;
import com.maydesk.base.model.MUser;
import com.maydesk.base.model.StatusMode;
import com.maydesk.base.util.PDUtil;

public class PDAvatar extends PDDesktopItem {

	public static final String ACTION_EVENT = "action";	
	public static final String ACTION_MOUSE_UP = "mouseUp";
	public static final String ACTION_ACKNOWLEDGE_STATUS = "acknowledgeStatus";
	public static final String ACTION_ACKNOWLEDGE_MESSAGE = "acknowledgeMessage";
	public static final String PROPERTY_ICON = "icon";
	public static final String PROPERTY_BUBBLE_STATUS = "bubbleStatus";
	public static final String PROPERTY_BUBBLE_MESSAGE = "bubbleMessage";
	public static final String PROPERTY_BUBBLE_LEFT = "bubbleLeft";
	public static final String PROPERTY_BUBBLE_TOP = "bubbleTop";
	public static final String PROPERTY_COLOR = "color";
	public static final String PROPERTY_STATUS_IMG = "statusImg";
	public static final String PROPERTY_TOOL_TIP = "tooltip";
	
	protected MAvatar avatar;

	/**
	 * this constructor is used only for pseudo-instances
	 * for visualizing a fake person
	 */
	public PDAvatar(ImageReference img, int posX, int posY, Color color) {
		super("Hello", posX, posY);
		set(PROPERTY_ICON, img);
		set(PROPERTY_COLOR, color);
	}

	public PDAvatar(final MAvatar person) {
		super(person.getTitle(), person.getPositionX(), person.getPositionY());
		this.avatar = person;
		MUser user = person.getPerson();
		ImageReference img = PDUserSession.getInstance().getImage(user.getJabberId());
		set(PROPERTY_ICON, img);
		set(PROPERTY_TOOL_TIP, getTooltip(user));
		set(PROPERTY_BUBBLE_LEFT, new Extent(0));
		set(PROPERTY_BUBBLE_TOP, new Extent(0));
		set(PROPERTY_COLOR, new Color(user.getColorShade()));
		checkPresenceStatus();
	}
	
	private String getTooltip(MUser user) {
		String tooltip = null;
		
		VCard vcard = PDUserSession.getInstance().getVCard(user.getJabberId());
		if (PDUtil.isEmpty(vcard.getFirstName())) {
			tooltip = user.getJabberId();
		} else {
			tooltip = vcard.getFirstName();
			if (!PDUtil.isEmpty(user.getJabberId())) {
				tooltip += "/" + user.getJabberId();
			}
		}
		
		return tooltip;
	}

	@Override
	public void processInput(String inputName, Object inputValue) {
		if (avatar == null) return;
		if (ACTION_MOUSE_UP.equals(inputName)) {
    		if (avatar.getPositionX() < 50 && avatar.getPositionY() < 55) { 

    			// sent to recycle bin
    			// Testing just in case it's not a menued comp.
    			Component comp = getParent() instanceof PDContextMenu ? getParent() : this;
    			PDDesktop.getInstance().getContentPane().remove(comp);
    			
    			if (avatar.getId() > 0) {
    				PDHibernateFactory.getSession().delete(avatar);
    			}
    		} else {
    			//store the new x/y position
    			if (avatar.getOwner() != null) {
    				PDHibernateFactory.getSession().saveOrUpdate(avatar);
    			}
    		}        	
    		//shortcut.onMouseUp(this);
		} else if (PROPERTY_POSITION_X.equals(inputName)) {
			avatar.setPositionX(((Extent) inputValue).getValue());
        } else if (PROPERTY_POSITION_Y.equals(inputName)) {
        	avatar.setPositionY(((Extent) inputValue).getValue());
        } else if (ACTION_EVENT.equals(inputName)) {
        	avatar.executeTask();
        } else if (ACTION_ACKNOWLEDGE_STATUS.equals(inputName)) {
        	avatar.acknowledgeStatus();
        } else if (ACTION_ACKNOWLEDGE_MESSAGE.equals(inputName)) {
        	startConversation();
        }
    }
	
	private void startConversation() {
		String initialMessage = (String)get(PROPERTY_BUBBLE_MESSAGE);
		PDUserSession.getInstance().createNewChatWindow(avatar.getPerson().getJabberId(), initialMessage);		
	}
	
	public MAvatar getModel() {
	    return avatar;
    }

	public Component getMenuedComponent() {
		PDContextMenu menu = new PDContextMenu(this);
		menu.addItem("Send message", "img/menu/chat16.png", true).addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				startConversation();
			}
		}); 
		menu.addItem("View Profile", "img/menu/person16b.gif", true).addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				viewProfileClicked();
			}
		}); 
		
		return menu;
    }   
	
	private void viewProfileClicked() {
		PDDesktop.getInstance().addWindow(new DlgUserSettings(avatar.getPerson(), false));
	}

	public void setBubbleMessage(String bubbleMessage) {
		set(PROPERTY_BUBBLE_MESSAGE, bubbleMessage);		
    }
	
	public void setBubbleStatus(String bubbleStatus) {
		set(PROPERTY_BUBBLE_STATUS, bubbleStatus);		
    }

	/*
	 * show the latest presence, but only if not already acknowledged by the user
	 */
	public void checkPresenceStatus() {
		DaoUser dao = new DaoUser();
		String status = dao.findNewestPresenceStatus(avatar.getPerson().getJabberId());
		set(PROPERTY_BUBBLE_STATUS, status);
		
		Presence presence = PDUserSession.getInstance().getCurrentPresence(avatar.getPerson().getJabberId());
		StatusMode statusMode = StatusMode.findByPresence(presence);
		set(PROPERTY_STATUS_IMG, statusMode.getImg());
    }
}