/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Extent;
import nextapp.echo.app.StreamImageReference;

import org.apache.commons.io.IOUtils;

public class ByteArrayImageReference extends StreamImageReference {

	private byte[] data;
	private String contentType;
	private String id = ApplicationInstance.generateSystemId();
	private int size;
		
	public ByteArrayImageReference(byte[] data, String contentType, int size) {
		this.data = data;
		this.contentType = contentType;
		this.size = size;
	}
		
	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public void render(OutputStream out) throws IOException {
		try {
			IOUtils.copy(new ByteArrayInputStream(data), out);	
			out.flush(); 
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getRenderId() {
		return id;
	}
	
	public Extent getHeight() {
		return new Extent(size);
	}

	public Extent getWidth() {
		return new Extent(size);
	}	
}
