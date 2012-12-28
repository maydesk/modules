package com.maydesk.base.config;

import javax.xml.bind.annotation.XmlAttribute;


public class XmlMenuItem { 

	private String commandClass;
	private String commandId;
	private String textEN;
	
	public String getCommandClass() {
		return commandClass;
	}
	
	public String getCommandId() {
		return commandId;
	}
	
	@XmlAttribute
	public String getTextEN() {
		return textEN;
	}
	
	public void setCommandClass(String commandClass) {
		this.commandClass = commandClass;
	}
	
	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}
	
	public void setTextEN(String textEN) {
		this.textEN = textEN;
	}	
}
