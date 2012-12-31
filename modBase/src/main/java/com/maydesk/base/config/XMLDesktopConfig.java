package com.maydesk.base.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(namespace = "com.maydesk.base.config.XMLConfig")
public class XMLDesktopConfig {
	
	private List<XmlDesktopItem> desktopEntries = new ArrayList<XmlDesktopItem>();
	private List<XmlDesktopItem> footerItemsLeft = new ArrayList<XmlDesktopItem>();
	private XmlMenu menu;

	@XmlElementWrapper(name = "desktopItems")
	@XmlElement(name = "item")
	public List<XmlDesktopItem> getDesktopEntries() {
		return desktopEntries;
	}

	@XmlElementWrapper(name = "footerItemsLeft")
	@XmlElement(name = "item")
	public List<XmlDesktopItem> getFooterItemsLeft() {
		return footerItemsLeft;
	}
	
	public XmlMenu getMenu() {
		return menu;
	}

	public void setDesktopEntries(List<XmlDesktopItem> desktopEntries) {
		this.desktopEntries = desktopEntries;
	}

	public void setFooterItemsLeft(List<XmlDesktopItem> footerItemsLeft) {
		this.footerItemsLeft = footerItemsLeft;
	}

	public void setMenu(XmlMenu menu) {
		this.menu = menu;
	}


}
