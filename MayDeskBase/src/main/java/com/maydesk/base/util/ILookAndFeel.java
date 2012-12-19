/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.util;

import nextapp.echo.app.Color;

/**
 * @author chrismay
 */
public interface ILookAndFeel {

	public Color getHighlight();

	public Color getBackgroundDark();

	public Color getBackgroundClear();

	public Color getBackgroundDark2();

	public IImage getFooterFillImage();

	public String getVersionInfo();

	public String getLogo();

	public IImage getProfideskIcon();

	public Color getForeground();

	public String getApplicationName();
}
