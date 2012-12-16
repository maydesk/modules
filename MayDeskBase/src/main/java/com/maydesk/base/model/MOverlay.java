/* 
 * This file is copyright of PROFIDESK (www.profidesk.net)
 * Copyright (C) 2009
 * All rights reserved
 */
package com.maydesk.base.model;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class MOverlay extends MBaseWithTitle {

	protected int positionX = 100;
	protected int positionY = 100;
	protected int height = 100;
	protected int width = 100;
	protected String name;
	
	public String getName() {
    	return name;
    }
	public void setName(String name) {
    	this.name = name;
    }
	public int getHeight() {
    	return height;
    }
	public void setHeight(int height) {
    	this.height = height;
    }
	public int getPositionX() {
    	return positionX;
    }
	public void setPositionX(int positionX) {
    	this.positionX = positionX;
    }
	public int getPositionY() {
    	return positionY;
    }
	public void setPositionY(int positionY) {
    	this.positionY = positionY;
    }
	public int getWidth() {
    	return width;
    }
	public void setWidth(int width) {
    	this.width = width;
    }
	
}
