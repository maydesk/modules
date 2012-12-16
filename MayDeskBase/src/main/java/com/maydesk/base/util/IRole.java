/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.util;

public interface IRole {
	
	public String name();

	public boolean hasPrivilege(Enum privilegeName);
}
