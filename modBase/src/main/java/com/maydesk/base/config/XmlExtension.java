package com.maydesk.base.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;



public class XmlExtension {
	
	private List<XmlExtensionTab> tabs = new ArrayList<XmlExtensionTab>();
	private String className;

	@XmlAttribute
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	@XmlElementWrapper(name = "tabs")
	@XmlElement(name = "tab")
	public List<XmlExtensionTab> getTabs() {
		return tabs;
	}

	public void setTabs(List<XmlExtensionTab> tabs) {
		this.tabs = tabs;
	}
	
}
