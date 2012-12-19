/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.util;

import nextapp.echo.app.Command;

/**
 * @author Alejandro Salas
 */
public class BrowserRedirectParentCommand implements Command {

	private String uri;

	public BrowserRedirectParentCommand(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}
}
