package com.maydesk.base.config;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class XmlDesktopItem extends XmlBaseEntry {

	private int left;
	private int top;
	private String className;
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getLeft() {
		return left;
	}

	public int getTop() {
		return top;
	}
	
	public void setLeft(int left) {
		this.left = left;
	}
	
	public void setTop(int top) {
		this.top = top;
	}
}
