package com.maydesk.base.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(namespace = "com.maydesk.base.config.XMLConfig")
public class XMLDesktopConfig {
	
	private String title;
	private List<XmlDesktopItem> desktopEntries = new ArrayList<XmlDesktopItem>();
	private List<XmlDesktopItem> footerItemsLeft = new ArrayList<XmlDesktopItem>();
	private List<XmlDesktopItem> footerItemsRight = new ArrayList<XmlDesktopItem>();
	private List<XmlExtension> extensions = new ArrayList<XmlExtension>();
	private XmlMenu menu;

	@XmlElementWrapper(name = "extensions")
	@XmlElement(name = "extension")
	public List<XmlExtension> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<XmlExtension> extensions) {
		this.extensions = extensions;
	}

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

	@XmlElementWrapper(name = "footerItemsRight")
	@XmlElement(name = "item")
	public List<XmlDesktopItem> getFooterItemsRight() {
		return footerItemsRight;
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

	public void setFooterItemsRight(List<XmlDesktopItem> footerItemsRight) {
		this.footerItemsRight = footerItemsRight;
	}

	public void setMenu(XmlMenu menu) {
		this.menu = menu;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
