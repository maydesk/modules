/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.util;

public class Attachment {

	private byte[] data;
	private String label;
	
	public Attachment() {
	}
	
	public byte[] getData() {
		return data;
	}

	public String getLabel() {
		return label;
	}
	
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
}
