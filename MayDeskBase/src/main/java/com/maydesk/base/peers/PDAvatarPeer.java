/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.peers;

import nextapp.echo.app.Color;
import nextapp.echo.app.Component;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

import com.maydesk.base.util.PDUtil;
import com.maydesk.base.widgets.PDAvatar;
import com.maydesk.base.widgets.PDDesktopItem;

/**
 * @author chrismay
 */
public class PDAvatarPeer extends PDDesktopItemPeer {

	private static final String PD_AVATAR = "PDAvatar";

	static {
		JavaScriptService SERVICE = JavaScriptService.forResources(PD_AVATAR, new String[] { PDUtil.BASE_PATH + "js/PDBubbleText.js", PDUtil.BASE_PATH + "js/PDAvatar.js" });
		WebContainerServlet.getServiceRegistry().add(SERVICE);
	}

	public PDAvatarPeer() {
		addOutputProperty(PDAvatar.PROPERTY_BUBBLE_MESSAGE);
		addOutputProperty(PDAvatar.PROPERTY_BUBBLE_STATUS);
		addOutputProperty(PDAvatar.PROPERTY_ICON);
		addOutputProperty(PDAvatar.PROPERTY_STATUS_IMG);
		addEvent(new AbstractComponentSynchronizePeer.EventPeer("action", PDAvatar.ACTION_EVENT) {
			@Override
			public boolean hasListeners(Context context, Component component) {
				return true;
			}
		});
		addEvent(new AbstractComponentSynchronizePeer.EventPeer("mouseUp", PDAvatar.ACTION_MOUSE_UP) {
			@Override
			public boolean hasListeners(Context context, Component component) {
				return true;
			}
		});
		addEvent(new AbstractComponentSynchronizePeer.EventPeer("acknowledgeStatus", PDAvatar.ACTION_ACKNOWLEDGE_STATUS) {
			@Override
			public boolean hasListeners(Context context, Component component) {
				return true;
			}
		});
		addEvent(new AbstractComponentSynchronizePeer.EventPeer("acknowledgeMessage", PDAvatar.ACTION_ACKNOWLEDGE_MESSAGE) {
			@Override
			public boolean hasListeners(Context context, Component component) {
				return true;
			}
		});
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getClientComponentType(boolean)
	 */
	@Override
	public String getClientComponentType(boolean shortType) {
		return PD_AVATAR;
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#getComponentClass()
	 */
	@Override
	public Class getComponentClass() {
		return PDAvatar.class;
	}

	/**
	 * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(nextapp.echo.app.util.Context,
	 *      Component)
	 */
	@Override
	public void init(Context context, Component component) {
		super.init(context, component);
		ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
		serverMessage.addLibrary(PD_AVATAR);

	}

	/**
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#storeInputProperty(nextapp.echo.app.util.Context,
	 *      nextapp.echo.app.Component, java.lang.String, int, java.lang.Object)
	 */
	@Override
	public void storeInputProperty(Context context, Component component, String propertyName, int index, Object newValue) {
		ClientUpdateManager clientUpdateManager = (ClientUpdateManager) context.get(ClientUpdateManager.class);
		if (PDDesktopItem.PROPERTY_POSITION_X.equals(propertyName)) {
			clientUpdateManager.setComponentProperty(component, PDDesktopItem.PROPERTY_POSITION_X, newValue);
		} else if (PDDesktopItem.PROPERTY_POSITION_Y.equals(propertyName)) {
			clientUpdateManager.setComponentProperty(component, PDDesktopItem.PROPERTY_POSITION_Y, newValue);
		}
	}

	/**
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getInputPropertyClass(java.lang.String)
	 */
	@Override
	public Class getInputPropertyClass(String propertyName) {
		if (PDAvatar.PROPERTY_BUBBLE_MESSAGE.equals(propertyName)) {
			return String.class;
		} else if (PDAvatar.PROPERTY_BUBBLE_STATUS.equals(propertyName)) {
			return String.class;
		} else if (PDAvatar.PROPERTY_COLOR.equals(propertyName)) {
			return Color.class;
		}
		return super.getInputPropertyClass(propertyName);
	}

	/**
	 * @see nextapp.echo.webcontainer.AbstractComponentSynchronizePeer#getOutputProperty(nextapp.echo.app.util.Context,
	 *      nextapp.echo.app.Component, java.lang.String, int)
	 */
	@Override
	public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
		PDAvatar avatarComp = (PDAvatar) component;
		if (propertyName.equals(PDAvatar.PROPERTY_BUBBLE_MESSAGE)) {
			return avatarComp.get(PDAvatar.PROPERTY_BUBBLE_MESSAGE);
		} else if (propertyName.equals(PDAvatar.PROPERTY_BUBBLE_STATUS)) {
			return avatarComp.get(PDAvatar.PROPERTY_BUBBLE_STATUS);
		} else if (propertyName.equals(PDAvatar.PROPERTY_STATUS_IMG)) {
			return avatarComp.get(PDAvatar.PROPERTY_STATUS_IMG);
		}

		return super.getOutputProperty(context, component, propertyName, propertyIndex);
	}

}
