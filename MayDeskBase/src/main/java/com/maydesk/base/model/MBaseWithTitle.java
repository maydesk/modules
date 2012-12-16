/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;
 
import javax.persistence.MappedSuperclass;

/**
 * Created on 08.10.2007
 */
@MappedSuperclass
public abstract class MBaseWithTitle extends MBase {

	protected String cachedTitle;
	protected String cachedDescription;

	public String getCachedDescription() {
    	return cachedDescription;
    }

	public void setCachedDescription(String cachedDescription) {
    	this.cachedDescription = cachedDescription;
    }

	public String getCachedTitle() {
		return cachedTitle;
	}

	public void setCachedTitle(String cachedTitle) {
		this.cachedTitle = cachedTitle;
	}

	public abstract String createCachedTitle();

	public String createCachedDescription() {
		return "";
	}

	public String toString() {
		return cachedTitle;
	}
	
	public void updateCachedValues() {
		cachedTitle = createCachedTitle();
		cachedDescription = createCachedDescription();
	}
}