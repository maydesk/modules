package com.maydesk.context.widget;

import nextapp.echo.app.Component;
import nextapp.echo.app.ImageReference;


public class MDToolEntry extends Component{

	public static final String PROPERTY_ICON = "icon";
	public static final String PROPERTY_TOOL = "tool";
	
	public MDToolEntry() {
	}

	public void setIcon(ImageReference icon) {
		set(PROPERTY_ICON, icon);		
	}

	public void setTool(String tool) {
		set(PROPERTY_TOOL, tool);		
	}
}
