package com.maydesk.base.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class XmlMenu extends XmlBaseEntry { 

	private List<XmlMenu> menuEntries = new ArrayList<XmlMenu>();
	private List<XmlMenuItem> menuItems = new ArrayList<XmlMenuItem>();
	private String textEN;
	
	public List<XmlMenu> getMenuEntries() {
		return menuEntries;
	}

	@XmlElement(name = "item")
	public List<XmlMenuItem> getMenuItems() {
		return menuItems;
	}
	
	@XmlAttribute
	public String getTextEN() {
		return textEN;
	}
	
	@XmlElement(name = "menu")
	public void setMenuEntries(List<XmlMenu> menuEntries) {
		this.menuEntries = menuEntries;
	}
	
	public void setMenuItems(List<XmlMenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	
	public void setTextEN(String textEN) {
		this.textEN = textEN;
	}	
}
