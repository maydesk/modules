package com.maydesk.base.config;

import javax.xml.bind.annotation.XmlAttribute;



public class XmlExtensionTab {
	
	private String className;
	private String title;

	@XmlAttribute
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlAttribute
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
