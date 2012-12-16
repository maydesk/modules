/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "t_pdw_browser_session")
public class MBrowserSession extends MBase {

	private Calendar startupTime;
	private String userAgent;
	private String remoteHost;
	private int utcOffset;
	private int screenWidth;
	private int screenHight;

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public int getScreenHight() {
		return screenHight;
	}

	public void setScreenHight(int screenHight) {
		this.screenHight = screenHight;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public Calendar getStartupTime() {
		return startupTime;
	}

	public void setStartupTime(Calendar startupTime) {
		this.startupTime = startupTime;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public int getUtcOffset() {
		return utcOffset;
	}

	public void setUtcOffset(int utcOffset) {
		this.utcOffset = utcOffset;
	}
}