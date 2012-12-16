/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.gui;

import nextapp.echo.app.Component;

/**
 * A gereral purpose floating window 
 * 
 */
public class PDSvgImage extends Component {

    public static final String PROPERTY_SVG_DATA = "svg_data";

	public PDSvgImage() {
	}
	
	public byte[] getSvgData() {
		return (byte[])get(PROPERTY_SVG_DATA);
	}

	public void setSvgData(byte[] data) {
		set(PROPERTY_SVG_DATA, data);
	}
}
