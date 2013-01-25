package com.maydesk.context.peer;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

public class MDCommonService {

	public static final Service[] SERVICES = {
		JavaScriptService.forResource("clazz", "js/draw2d/Class.js"),
		JavaScriptService.forResource("canvg", "js/draw2d/canvg.js"),
		JavaScriptService.forResource("json2", "js/draw2d/json2.js"),
		JavaScriptService.forResource("rgbcolor", "js/draw2d/rgbcolor.js"),
		JavaScriptService.forResource("shifty", "js/draw2d/shifty.js"),
		JavaScriptService.forResource("jq", "js/draw2d/jquery-1.8.1.js"),
//		JavaScriptService.forResource("js/draw2d/jquery-1.8.1.min.js"), //ILLEGAL
		JavaScriptService.forResource("jqtp", "js/draw2d/jquery-touch_punch.js"),
		JavaScriptService.forResource("jqui", "js/draw2d/jquery-ui-1.8.23.custom.min.js"),
		JavaScriptService.forResource("jqar", "js/draw2d/jquery.autoresize.js"),
		JavaScriptService.forResource("jqcm", "js/draw2d/jquery.contextmenu.js"),
		JavaScriptService.forResource("jql", "js/draw2d/jquery.layout.js"),
		JavaScriptService.forResource("raphael", "js/draw2d/raphael.js"),		
		JavaScriptService.forResource("draw2d", "js/draw2d/draw2d.js")		
	};
	
	static {
		for (Service service : SERVICES) {
			WebContainerServlet.getServiceRegistry().add(service);
		}
	}

	private MDCommonService() {
	}
}
