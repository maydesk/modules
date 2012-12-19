/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author chrismay
 */
@Entity
@Table(name = "t_pdw_context")
public class MContext extends MBase {

	private int height;
	private String icon;
	private String title;
	private int positionX;
	private int positionY;
	private int width;

	public MContext() {
		// Empty
	}

	public void executeTask() {
		executeTaskInternal();
	}

	protected void executeTaskInternal() {
	}

	public int getHeight() {
		return height;
	}

	public String getIcon() {
		return icon;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public String getTitle() {
		return title;
	};

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}