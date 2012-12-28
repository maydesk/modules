package com.maydesk.base.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(namespace = "com.maydesk.base.config.XMLConfig")
public class XMLDesktopConfig {
	
	private List<XmlDesktopItem> desktopEntries = new ArrayList<>();
	private XmlMenu menu;
	
	public XmlMenu getMenu() {
		return menu;
	}

	public void setMenu(XmlMenu menu) {
		this.menu = menu;
	}

	@XmlElementWrapper(name = "desktopitems")
	@XmlElement(name = "item")
	public List<XmlDesktopItem> getDesktopEntries() {
		return desktopEntries;
	}

	public void setDesktopEntries(List<XmlDesktopItem> desktopEntries) {
		this.desktopEntries = desktopEntries;
	}


}
