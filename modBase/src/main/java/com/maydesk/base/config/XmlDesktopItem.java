package com.maydesk.base.config;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class XmlDesktopItem extends XmlBaseEntry {

	private int bottom;
	private String className;
	private int left;
	private int right;
	private int top;
	private String value;
	
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getBottom() {
		return bottom;
	}

	public String getClassName() {
		return className;
	}

	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public int getTop() {
		return top;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setLeft(int left) {
		this.left = left;
	}
	
	public void setRight(int right) {
		this.right = right;
	}
	
	public void setTop(int top) {
		this.top = top;
	}

}
